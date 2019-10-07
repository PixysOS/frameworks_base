/*
 * Copyright (C) 2018 The OmniROM Project
 *               2020-2021 The LineageOS Project
 *               2024 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.qs.tiles;

import static com.android.internal.logging.MetricsLogger.VIEW_UNKNOWN;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.view.View;

import androidx.annotation.Nullable;

import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.dagger.qualifiers.Background;
import com.android.systemui.dagger.qualifiers.Main;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTile.State;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.UserSettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.settings.SecureSettings;

import javax.inject.Inject;

public class AODTile extends QSTileImpl<State> implements
        BatteryController.BatteryStateChangeCallback {

    public static final String TILE_SPEC = "aod";

    private final Icon mIcon = ResourceIcon.get(R.drawable.ic_qs_aod);
    private final BatteryController mBatteryController;

    private final UserSettingObserver mSetting;

    @Inject
    public AODTile(
            QSHost host,
            QsEventLogger uiEventLogger,
            @Background Looper backgroundLooper,
            @Main Handler mainHandler,
            FalsingManager falsingManager,
            MetricsLogger metricsLogger,
            StatusBarStateController statusBarStateController,
            ActivityStarter activityStarter,
            QSLogger qsLogger,
            SecureSettings secureSettings,
            BatteryController batteryController,
            UserTracker userTracker
    ) {
        super(host, uiEventLogger, backgroundLooper, mainHandler, falsingManager, metricsLogger,
                statusBarStateController, activityStarter, qsLogger);

        mSetting = new UserSettingObserver(secureSettings, mHandler, Settings.Secure.DOZE_ALWAYS_ON,
                userTracker.getUserId()) {
            @Override
            protected void handleValueChanged(int value, boolean observedChange) {
                handleRefreshState(value);
            }
        };

        mBatteryController = batteryController;
        batteryController.observe(getLifecycle(), this);
    }

    private int getDozeState() {
        int dozeState = Settings.Secure.getIntForUser(mContext.getContentResolver(),
                Settings.Secure.DOZE_ALWAYS_ON, 0, UserHandle.USER_CURRENT);
        if (dozeState == 0) {
            dozeState = Settings.Secure.getIntForUser(mContext.getContentResolver(),
                    Settings.Secure.DOZE_ON_CHARGE, 0, UserHandle.USER_CURRENT) == 1 ? 2 : 0;
        }
        return dozeState;
    }

    @Override
    public void onPowerSaveChanged(boolean isPowerSave) {
        refreshState();
    }

    @Override
    protected void handleDestroy() {
        super.handleDestroy();
        mSetting.setListening(false);
    }

    @Override
    public boolean isAvailable() {
        return mContext.getResources().getBoolean(
                com.android.internal.R.bool.config_dozeAlwaysOnDisplayAvailable);
    }

    @Override
    public State newTileState() {
        State state = new State();
        state.handlesLongClick = false;
        return state;
    }

    @Override
    public void handleSetListening(boolean listening) {
        super.handleSetListening(listening);
        mSetting.setListening(listening);
    }

    @Override
    protected void handleUserSwitch(int newUserId) {
        mSetting.setUserId(newUserId);
        handleRefreshState(mSetting.getValue());
    }

    @Override
    protected void handleClick(@Nullable View view) {
        int dozeState = getDozeState();
        dozeState = dozeState < 2 ? dozeState + 1 : 0;
        Settings.Secure.putIntForUser(mContext.getContentResolver(), Settings.Secure.DOZE_ALWAYS_ON,
                dozeState == 2 ? 0 : dozeState, UserHandle.USER_CURRENT);
        Settings.Secure.putIntForUser(mContext.getContentResolver(), Settings.Secure.DOZE_ON_CHARGE,
                dozeState == 2 ? 1 : 0, UserHandle.USER_CURRENT);
        refreshState();
    }

    @Override
    protected void handleLongClick(@Nullable View view) {
        Settings.Secure.putIntForUser(mContext.getContentResolver(), Settings.Secure.DOZE_ALWAYS_ON,
                getDozeState() != 0 ? 0 : 1, UserHandle.USER_CURRENT);
        Settings.Secure.putIntForUser(mContext.getContentResolver(), Settings.Secure.DOZE_ON_CHARGE,
                0, UserHandle.USER_CURRENT);
        refreshState();
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_aod_label);
    }

    @Override
    protected void handleUpdateState(State state, Object arg) {
        state.icon = mIcon;
        state.label = mContext.getString(R.string.quick_settings_aod_label);

        int dozeState = getDozeState();
        switch (dozeState) {
            case 0:
                state.state = Tile.STATE_INACTIVE;
                state.secondaryLabel = mContext.getString(R.string.switch_bar_off);
                break;
            case 1:
                state.state = Tile.STATE_ACTIVE;
                state.secondaryLabel = mContext.getString(R.string.switch_bar_on);
                break;
            case 2:
                state.state = Tile.STATE_ACTIVE;
                state.secondaryLabel = mContext.getString(R.string.quick_settings_aod_secondary_label_on_at_charge);
                break;
        }
    }

    @Override
    public int getMetricsCategory() {
        return VIEW_UNKNOWN;
    }
}
