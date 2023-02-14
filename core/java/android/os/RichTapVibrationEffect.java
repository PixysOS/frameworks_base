/*
 * Copyright (C) 2023 Paranoid Android
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

package android.os;

import android.content.res.Resources;
import android.util.Slog;

import com.android.internal.R;

/** @hide */
public class RichTapVibrationEffect {

    private static final String TAG = "RichTapVibrationEffect";

    public static boolean isSupported() {
        return Resources.getSystem().getBoolean(R.bool.config_usesRichtapVibration);
    }

    public static int[] getInnerEffect(int id) {
        switch (id) {
            case 0: // click
                return new int[]{1, 4097, 0, 100, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 1: // double click
                return new int[]{1, 4097, 0, 100, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4097, 70, 100, 80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 2: // tick
                return new int[]{1, 4097, 0, 100, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 3: // thud
                return new int[]{1, 4097, 0, 100, 50, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 4: // pop
                return new int[]{1, 4097, 0, 100, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 5: // heavy click
                return new int[]{1, 4097, 0, 100, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            case 21: // texture click
                return new int[]{1, 4097, 0, 50, 33, 29, 0, 0, 0, 12, 59, 0, 22, 75, -21, 29, 0, 0, 4097, 30, 100, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            default:
                Slog.d(TAG, "Exception encountered!", new IllegalStateException("Unexpected effect id: " + id));
                return null;
        }
    }

    public static int getInnerEffectStrength(int strength) {
        switch (strength) {
            case 0: // light
                return 150;
            case 1: // medium
                return 200;
            case 2: // strong
                return 250;
            default:
                Slog.e(TAG, "Wrong Effect Strength!!");
                return 0;
        }
    }
}
