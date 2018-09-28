/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.android.internal.statusbar;

import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.util.Log;

public class ThemeAccentUtils {
    public static final String TAG = "ThemeAccentUtils";

    private static final String[] ACCENTS = {
        "default_accent", // 0
        "com.google.android.theme.newhouseorange", // 1
        "com.google.android.theme.sunsetorange", // 2
        "com.google.android.theme.warmthorange", // 3
        "com.google.android.theme.maniamber", // 4
        "com.google.android.theme.limedgreen", // 5
        "com.google.android.theme.diffdaygreen", // 6
        "com.google.android.theme.spoofygreen", // 7
        "com.google.android.theme.movemint", // 8
        "com.google.android.theme.naturedgreen", // 9
        "com.google.android.theme.stock", // 10
        "com.google.android.theme.drownedaqua", // 11
        "com.google.android.theme.holillusion", // 12
        "com.google.android.theme.coldbleu", // 13
        "com.google.android.theme.heirloombleu", // 14
        "com.google.android.theme.obfusbleu", // 15
        "com.google.android.theme.almostproblue", // 16
        "com.google.android.theme.lunablue", // 17
        "com.google.android.theme.frenchbleu", // 18
        "com.google.android.theme.dreamypurple", // 19
        "com.google.android.theme.notimppurple", // 20
        "com.google.android.theme.grapespurple", // 21
        "com.google.android.theme.spookedpurple", // 22
        "com.google.android.theme.dimigouig", // 23
        "com.google.android.theme.duskpurple", // 24
        "com.google.android.theme.bubblegumpink", // 25
        "com.google.android.theme.dawnred", // 26
        "com.google.android.theme.burningred", // 27
        "com.google.android.theme.labouchered", // 28
        "com.google.android.theme.misleadingred", // 29
        "com.google.android.theme.whythisgrey", // 30
    };

    private static final String[] QS_TILE_THEMES = {
        "default", // 0
        "com.android.systemui.qstile.square", // 1
        "com.android.systemui.qstile.roundedsquare", // 2
        "com.android.systemui.qstile.squircle", // 3
        "com.android.systemui.qstile.teardrop", // 4
        "com.android.systemui.qstile.circlegradient", //5
        "com.android.systemui.qstile.circleoutline", //6
        "com.android.systemui.qstile.justicons", //7
    };

    private static final String[] DARK_THEMES = {
        "com.android.system.theme.darktheem", // 0
        "com.android.settings.theme.darktheem", // 1
        "com.android.systemui.theme.darktheem", // 2
        "com.android.updater.theme.dark.pixys", // 3
    };

    private static final String[] BLACK_THEMES = {
        "com.android.system.theme.blacktheem", // 0
        "com.android.settings.theme.blacktheem", // 1
        "com.android.systemui.theme.blacktheem", // 2
        "com.android.updater.theme.black.pixys", // 3
    };

    // Switches theme accent from to another or back to stock
    public static void updateAccents(IOverlayManager om, int userId, int accentSetting) {
        if (accentSetting == 0) {
            unloadAccents(om, userId);
        } else {
            try {
                om.setEnabled(ACCENTS[accentSetting],
                        true, userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change theme", e);
            }
        }
    }

    // Unload all the theme accents
    public static void unloadAccents(IOverlayManager om, int userId) {
        // skip index 0
        for (int i = 1; i < ACCENTS.length; i++) {
            String accent = ACCENTS[i];
            try {
                om.setEnabled(accent,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Check for the dark system theme
    public static boolean isUsingDarkTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(DARK_THEMES[0],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }

    // Check for the black system theme
    public static boolean isUsingBlackTheme(IOverlayManager om, int userId) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(BLACK_THEMES[0],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
     }

    public static void setLightDarkTheme(IOverlayManager om, int userId, boolean useDarkTheme) {
        for (String theme : DARK_THEMES) {
                try {
                    om.setEnabled(theme,
                        useDarkTheme, userId);
                } catch (RemoteException e) {
                    Log.w(TAG, "Can't change theme", e);
                }
        }
    }

    public static void setLightBlackTheme(IOverlayManager om, int userId, boolean useBlackTheme) {
        for (String theme : BLACK_THEMES) {
                try {
                    om.setEnabled(theme,
                        useBlackTheme, userId);
                } catch (RemoteException e) {
                    Log.w(TAG, "Can't change theme", e);
                }
        }
    }

    // Switches qs tile style to user selected.
    public static void updateTileStyle(IOverlayManager om, int userId, int qsTileStyle) {
        if (qsTileStyle == 0) {
            unlockQsTileStyles(om, userId);
        } else {
            try {
                om.setEnabled(QS_TILE_THEMES[qsTileStyle],
                        true, userId);
            } catch (RemoteException e) {
            }
        }
    }

    // Unload all the qs tile styles
    public static void unlockQsTileStyles(IOverlayManager om, int userId) {
        // skip index 0
        for (int i = 1; i < QS_TILE_THEMES.length; i++) {
            String qstiletheme = QS_TILE_THEMES[i];
            try {
                om.setEnabled(qstiletheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Check for any QS tile styles overlay
    public static boolean isUsingQsTileStyles(IOverlayManager om, int userId, int qsstyle) {
        OverlayInfo themeInfo = null;
        try {
            themeInfo = om.getOverlayInfo(QS_TILE_THEMES[qsstyle],
                    userId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return themeInfo != null && themeInfo.isEnabled();
    }
}
