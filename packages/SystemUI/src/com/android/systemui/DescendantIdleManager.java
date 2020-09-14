/*
 * Copyright (C) 2019 Descendant
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

package com.android.systemui;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AlarmManager.AlarmClockInfo;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.SystemProperties;
import android.location.LocationManager;
import android.provider.Settings;
import android.util.Log;

import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.LocationController;

import java.util.ArrayList;
import java.util.List;

public class DescendantIdleManager {
    static String TAG = "DescendantIdleManager";

    static Handler h = new Handler();
    static Runnable rStateTwo;
    static Runnable rStateThree;
    static List<ActivityManager.RunningAppProcessInfo> RunningServices;
    static ActivityManager localActivityManager;
    static boolean prevMasterSyncStatus;
    static boolean isLocationEnabled;
    static Context imContext;
    static ContentResolver mContentResolver;
    static List<String> killablePackages;
    static final long IDLE_TIME_NEEDED = 3600000;
    static int ultraSaverStatus;
    static final String[] LOG_MSGS = { "just ran ",
                                       "rStateTwo Immediate!",
                                       "rStateTwo",
                                       "rStateThree",
                                       "alarmTime ",
                                       "realTime " };

    public static void initManager(Context mContext) {
        imContext = mContext;
        killablePackages = new ArrayList<>();
        localActivityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        mContentResolver = mContext.getContentResolver();

        rStateTwo = new Runnable() {
            public void run() {
                    syncHandler(false);
                    servicesKiller();
            }
        };
        rStateThree = new Runnable() {
            public void run() {
                haltManager();
            }
        };
    }

    public static void executeManager() {
        String TAG_SUBCLASS = "executeManager ";
        RunningServices = localActivityManager.getRunningAppProcesses();

        if (IDLE_TIME_NEEDED > msTillAlarm(imContext) && msTillAlarm(imContext) != 0) {
            IdleManLog(TAG_SUBCLASS + LOG_MSGS[1]);
            h.postDelayed(rStateTwo,100);
        } else {
            IdleManLog(TAG_SUBCLASS + LOG_MSGS[2]);
            h.postDelayed(rStateTwo,IDLE_TIME_NEEDED /*1hr*/);
        }
        if (msTillAlarm(imContext) != 0) {
            IdleManLog(TAG_SUBCLASS + LOG_MSGS[3]);
            h.postDelayed(rStateThree,(msTillAlarm(imContext) - 900000));
        }
    }

    public static void haltManager() {
        String TAG_SUBCLASS = "haltManager";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        h.removeCallbacks(rStateTwo);
        theAwakening();
    }

    public static void theAwakening() {
        String TAG_SUBCLASS = "theAwakening";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        h.removeCallbacks(rStateThree);
        syncHandler(true);
    }

    public static long msTillAlarm(Context imContext) {
        String TAG_SUBCLASS = "msTillAlarm";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        AlarmManager.AlarmClockInfo info =
                ((AlarmManager)imContext.getSystemService(Context.ALARM_SERVICE)).getNextAlarmClock();
        if (info != null) {
            long alarmTime = info.getTriggerTime();
            IdleManLog(TAG_SUBCLASS + LOG_MSGS[4] + Long.toString(alarmTime));
            long realTime = alarmTime - System.currentTimeMillis();
            IdleManLog(TAG_SUBCLASS + LOG_MSGS[5] + Long.toString(realTime));
            return realTime;
        } else {
            return 0;
        }
    }

    public static void syncHandler(boolean state) {
        String TAG_SUBCLASS = "syncHandler";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        ContentResolver.setMasterSyncAutomatically(state);
    }

    public static void positionHandler(boolean state) {
        String TAG_SUBCLASS = "positionHandler";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        LocationController mController = Dependency.get(LocationController.class);
        mController.setLocationEnabled(state ? Settings.Secure.LOCATION_MODE_HIGH_ACCURACY : Settings.Secure.LOCATION_MODE_OFF);
    }

    public static void servicesKiller() {
        String TAG_SUBCLASS = "servicesKiller";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        localActivityManager = (ActivityManager) imContext.getSystemService(Context.ACTIVITY_SERVICE);
        RunningServices = localActivityManager.getRunningAppProcesses();
        for (int i=0; i < RunningServices.size(); i++) {
            if (!RunningServices.get(i).pkgList[0].toString().contains("com.android.") &&
                !RunningServices.get(i).pkgList[0].toString().equals("android") &&
                !RunningServices.get(i).pkgList[0].toString().contains("launcher") &&
                !RunningServices.get(i).pkgList[0].toString().contains("ims")) {
                    localActivityManager.killBackgroundProcesses(RunningServices.get(i).pkgList[0].toString());
            }
        }
    }

    /*public static void ultraSaver(Context uCon, int state) {
        String TAG_SUBCLASS = "ultraSaver";
        IdleManLog(LOG_MSGS[0] + TAG_SUBCLASS);
        ultraSaverStatus = state;
        syncHandler(ultraSaverStatus == 1 ? false : true);
        positionHandler(ultraSaverStatus == 1 ? false : true);
        PackageManager pm = uCon.getPackageManager();
        localActivityManager = (ActivityManager) uCon.getSystemService(Context.ACTIVITY_SERVICE);
        RunningServices = localActivityManager.getRunningAppProcesses();
        for (int i=0; i < RunningServices.size(); i++) {
            if(RunningServices.get(i).pkgList[0].toString().equals("com.google.android.gms"))
                pm.setApplicationEnabledSetting(RunningServices.get(i).pkgList[0].toString(),
                                                ultraSaverStatus == 1 ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER : PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                PackageManager.DONT_KILL_APP);
        }
    }*/

    private static void IdleManLog(String msg) {
            Log.d(TAG, msg);
    }
}