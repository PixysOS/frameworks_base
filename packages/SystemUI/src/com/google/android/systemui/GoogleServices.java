/*
 * Copyright (C) 2022 StatiXOS
 * SPDX-License-Identifer: Apache-2.0
 */

package com.google.android.systemui;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;

import com.android.systemui.Dumpable;
import com.android.systemui.VendorServices;
import com.android.systemui.dagger.SysUISingleton;
import com.android.systemui.dagger.qualifiers.Background;
import com.android.systemui.dagger.qualifiers.Main;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.util.wakelock.WakeLockLogger;

import com.google.android.systemui.ambient.AmbientIndicationContainer;
import com.google.android.systemui.ambient.AmbientIndicationService;
import com.android.systemui.res.R;

import dagger.Lazy;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.inject.Inject;

@SysUISingleton
public class GoogleServices extends VendorServices {

    private final ArrayList<Object> mServices = new ArrayList<>();
    private final ActivityStarter mActivityStarter;
    private final AlarmManager mAlarmManager;
    private final Lazy<Handler> mBgHandler;
    private final Lazy<Handler> mMainHandler;
    private final NotificationShadeWindowView mNotificationShadeWindowView;
    private final PowerInteractor mPowerInteractor;
    private final ShadeViewController mShadeViewController;
    private final WakeLockLogger mWakelockLogger;

    private final Context mContext;

    @Inject
    public GoogleServices(
            Context context,
            ActivityStarter activityStarter,
            AlarmManager alarmManager,
            NotificationShadeWindowView notificationShadeWindowView,
            PowerInteractor powerInteractor,
            ShadeViewController shadeViewController,
            WakeLockLogger wakeLockLogger,
            @Background Lazy<Handler> bgHandler,
            @Main Lazy<Handler> mainHandler) {
        super();
        mActivityStarter = activityStarter;
        mAlarmManager = alarmManager;
        mContext = context;
        mNotificationShadeWindowView = notificationShadeWindowView;
        mPowerInteractor = powerInteractor;
        mShadeViewController = shadeViewController;
        mWakelockLogger = wakeLockLogger;
        mBgHandler = bgHandler;
        mMainHandler = mainHandler;
    }

    @Override
    public void start() {
        AmbientIndicationContainer ambientIndicationContainer =
                (AmbientIndicationContainer)
                        mNotificationShadeWindowView.findViewById(
                                R.id.ambient_indication_container);
        ambientIndicationContainer.initializeView(
                mShadeViewController, mPowerInteractor, mActivityStarter, mWakelockLogger, mBgHandler, mMainHandler);
        addService(
                new AmbientIndicationService(mContext, ambientIndicationContainer, mAlarmManager));
    }

    @Override
    public void dump(PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < mServices.size(); i++) {
            if (mServices.get(i) instanceof Dumpable) {
                ((Dumpable) mServices.get(i)).dump(printWriter, strArr);
            }
        }
    }

    private void addService(Object obj) {
        if (obj != null) {
            mServices.add(obj);
        }
    }
}
