/*
 * Copyright (C) 2020 The Pixel Experience Project
 *               2021-2022 crDroid Android Project
 *               2019-2022 Evolution X
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.internal.util.pixys;

import android.app.Application;
import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PixelPropsUtils {

    private static final String TAG = PixelPropsUtils.class.getSimpleName();
    private static final boolean DEBUG = false;

    private static final String PACKAGE_GMS = "com.google.android.gms";
    private static final String PACKAGE_FINSKY = "com.android.vending";
    private static final boolean PRODUCT_SUPPORT_HIGH_FPS =
            SystemProperties.getBoolean("ro.device.support_high_fps", false);
    private static final boolean PRODUCT_SUPPORT_CONTENT_REFRESH =
            SystemProperties.getBoolean("ro.surface_flinger.use_content_detection_for_refresh_rate", false);
    private static final Map<String, Object> propsToChangePUBG;
    private static final Map<String, Object> propsToChangeCOD;
    private static final Map<String, Object> propsToChangePixel7Pro;
    private static final Map<String, Object> propsToChangePixel5;
    private static final Map<String, Object> propsToChangePixelXL;
    private static final Map<String, ArrayList<String>> propsToKeep;
    private static final String[] extraPackagesToChange = {
        "com.android.chrome",
        "com.breel.wallpapers20",
	"com.nothing.smartcenter"
    };

    private static final String[] packagesToChangeCOD = {
        "com.activision.callofduty.shooter"
    };

    private static final String[] packagesToChangePUBG = {
        "com.tencent.ig",
        "com.pubg.krmobile",
        "com.vng.pubgmobile",
        "com.rekoo.pubgm",
        "com.pubg.imobile",
        "com.pubg.newstate",
        "com.gameloft.android.ANMP.GloftA9HM" // Asphalt 9
    };

    private static final String[] packagesToChangePixel7Pro = {
            "com.google.android.gms",
            "com.google.android.apps.privacy.wildlife",
            "com.google.android.apps.wallpaper.pixel",
            "com.google.android.apps.wallpaper",
            "com.google.android.apps.subscriptions.red",
            "com.google.android.inputmethod.latin",
            "com.google.android.apps.wellbeing"

    };

    private static final String[] packagesToChangePixelXL = {
            "com.google.android.apps.photos",
            "com.samsung.accessory",
            "com.samsung.accessory.fridaymgr",
            "com.samsung.accessory.berrymgr",
            "com.samsung.accessory.neobeanmgr",
            "com.samsung.android.app.watchmanager",
            "com.samsung.android.geargplugin",
            "com.samsung.android.gearnplugin",
            "com.samsung.android.modenplugin",
            "com.samsung.android.neatplugin",
            "com.samsung.android.waterplugin"
    };

    private static final String[] packagesToKeep = {
	PACKAGE_FINSKY,
	PACKAGE_GMS,
        "com.google.android.GoogleCamera",
        "com.google.android.GoogleCamera.Cameight",
        "com.google.android.GoogleCamera.Go",
        "com.google.android.GoogleCamera.Urnyx",
        "com.google.android.GoogleCameraAsp",
        "com.google.android.GoogleCameraCVM",
        "com.google.android.GoogleCameraEng",
        "com.google.android.GoogleCameraEng2",
        "com.google.android.MTCL83",
        "com.google.android.UltraCVM",
        "com.google.android.apps.cameralite",
        "com.google.android.dialer",
        "com.google.ar.core"
    };

    static {
        propsToKeep = new HashMap<>();
        propsToKeep.put("com.google.android.settings.intelligence", new ArrayList<>(Collections.singletonList("FINGERPRINT")));
        propsToChangePixel7Pro = new HashMap<>();
        propsToChangePixel7Pro.put("BRAND", "google");
        propsToChangePixel7Pro.put("MANUFACTURER", "Google");
        propsToChangePixel7Pro.put("DEVICE", "cheetah");
        propsToChangePixel7Pro.put("PRODUCT", "cheetah");
        propsToChangePixel7Pro.put("MODEL", "Pixel 7 Pro");
        propsToChangePixel7Pro.put("FINGERPRINT", "google/cheetah/cheetah:13/TD1A.220804.031/9071314:user/release-keys");
        propsToChangePUBG = new HashMap<>();
        propsToChangePUBG.put("MODEL", "GM1917");
        propsToChangeCOD = new HashMap<>();
        propsToChangeCOD.put("MODEL", "SO-52A");
        propsToChangePixel5 = new HashMap<>();
        propsToChangePixel5.put("BRAND", "google");
        propsToChangePixel5.put("MANUFACTURER", "Google");
        propsToChangePixel5.put("DEVICE", "redfin");
        propsToChangePixel5.put("PRODUCT", "redfin");
        propsToChangePixel5.put("MODEL", "Pixel 5");
        propsToChangePixel5.put("FINGERPRINT", "google/redfin/redfin:13/TP1A.221005.002/9012097:user/release-keys");
        propsToChangePixelXL = new HashMap<>();
        propsToChangePixelXL.put("BRAND", "google");
        propsToChangePixelXL.put("MANUFACTURER", "Google");
        propsToChangePixelXL.put("DEVICE", "marlin");
        propsToChangePixelXL.put("PRODUCT", "marlin");
        propsToChangePixelXL.put("MODEL", "Pixel XL");
        propsToChangePixelXL.put("FINGERPRINT", "google/marlin/marlin:10/QP1A.191005.007.A3/5972272:user/release-keys");
    }

    public static void setProps(String packageName) {
        if (packageName == null) {
            return;
        }
        if (Arrays.asList(packagesToKeep).contains(packageName)) {
            return;
        }
        if (packageName.startsWith("com.google.")
                || Arrays.asList(extraPackagesToChange).contains(packageName)) {
            Map<String, Object> propsToChange = propsToChangePixel5;

            if (Arrays.asList(packagesToChangePixel7Pro).contains(packageName)) {
                propsToChange = propsToChangePixel7Pro;
            }

            if (DEBUG) Log.d(TAG, "Defining props for: " + packageName);
            for (Map.Entry<String, Object> prop : propsToChange.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                if (propsToKeep.containsKey(packageName) && propsToKeep.get(packageName).contains(key)) {
                    if (DEBUG) Log.d(TAG, "Not defining " + key + " prop for: " + packageName);
                    continue;
                }
                if (DEBUG) Log.d(TAG, "Defining " + key + " prop for: " + packageName);
                setPropValue(key, value);
                }
            }
        if (Arrays.asList(packagesToChangePixelXL).contains(packageName)){
            if (DEBUG){
                Log.d(TAG, "Defining props for: " + packageName);
            }
            for (Map.Entry<String, Object> prop : propsToChangePixelXL.entrySet()) {
                String key = prop.getKey();
                Object value = prop.getValue();
                setPropValue(key, value);
            }
        }
        if (PRODUCT_SUPPORT_HIGH_FPS || PRODUCT_SUPPORT_CONTENT_REFRESH) {
            if (Arrays.asList(packagesToChangePUBG).contains(packageName)){
                if (DEBUG){
                    Log.d(TAG, "Defining props for: " + packageName);
                }
                for (Map.Entry<String, Object> prop : propsToChangePUBG.entrySet()) {
                    String key = prop.getKey();
                    Object value = prop.getValue();
                    setPropValue(key, value);
                }
            }
            if (Arrays.asList(packagesToChangeCOD).contains(packageName)){
                if (DEBUG){
                    Log.d(TAG, "Defining props for: " + packageName);
                }
                for (Map.Entry<String, Object> prop : propsToChangeCOD.entrySet()) {
                    String key = prop.getKey();
                    Object value = prop.getValue();
                    setPropValue(key, value);
                }
            }
        }

    }

    private static void setPropValue(String key, Object value){
        try {
            if (DEBUG) Log.d(TAG, "Defining prop " + key + " to " + value.toString());
            Field field = Build.class.getDeclaredField(key);
            field.setAccessible(true);
            field.set(null, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "Failed to set prop " + key, e);
        }
    }
}
