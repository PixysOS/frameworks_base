package android.os;

import android.os.Parcelable;
import android.util.Log;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.annotation.NonNull;
import android.annotation.SuppressLint;

public class RichTapVibrationEffect {
    private static final int AAC_CLIENT = 16711680;
    private static final int EFFECT_ID_START = 4096;
    private static final int HONOR_CLIENT = 327680;
    private static final int LENOVO_CLIENT = 393216;
    private static final int MAJOR_RICHTAP_VERSION = 6144;
    private static final int MINOR_RICHTAP_VERSION = 16;
    private static final int MI_CLIENT = 196608;
    private static final int NT_CLIENT = 524288;
    private static final int ONEPLUS_CLIENT = 131072;
    private static final int OPPO_CLIENT = 65536;
    private static final int PARCEL_TOKEN_ENVELOPE = 502;
    private static final int PARCEL_TOKEN_EXT_PREBAKED = 501;
    private static final int PARCEL_TOKEN_PATTERN_HE = 503;
    private static final int PARCEL_TOKEN_PATTERN_HE_LOOP_PARAMETER = 504;
    private static final String TAG = "RichTapVibrationEffect";
    private static final int VIBRATION_EFFECT_SUPPORT_NO = 2;
    private static final int VIBRATION_EFFECT_SUPPORT_UNKNOWN = 0;
    private static final int VIBRATION_EFFECT_SUPPORT_YES = 1;
    private static final int VIVO_CLIENT = 262144;
    private static final int ZTE_CLIENT = 458752;
    private static Map<String, Integer> effectStrength;
    private static Map<String, Integer> commonEffects = new HashMap();
    private static String DEFAULT_EXT_PREBAKED_STRENGTH = "STRONG";

    @NonNull
    public static final Parcelable.Creator<VibrationEffect> CREATOR = new Parcelable.Creator<VibrationEffect>() {
        public VibrationEffect createFromParcel(Parcel in) {
            int token = in.readInt();
            Log.d(RichTapVibrationEffect.TAG, "read token: " + token + "!");
            switch (token) {
                case PARCEL_TOKEN_EXT_PREBAKED:
                    return new ExtPrebaked(in);
                case PARCEL_TOKEN_ENVELOPE:
                    return new Envelope(in);
                case PARCEL_TOKEN_PATTERN_HE:
                    return new PatternHe(in);
                case PARCEL_TOKEN_PATTERN_HE_LOOP_PARAMETER:
                    return new PatternHeParameter(in);
                default:
                    throw new IllegalStateException("Unexpected vibration event type token in parcel.");
            }
        }

        @Override
        public VibrationEffect[] newArray(int size) {
            return new VibrationEffect[size];
        }
    };

    static {
        HashMap hashMap = new HashMap();
        effectStrength = hashMap;
        hashMap.put("LIGHT", 0);
        effectStrength.put("MEDIUM", 1);
        effectStrength.put("STRONG", 2);
    }

    private RichTapVibrationEffect() {
    }

    @NonNull
    public static VibrationEffect createExtPreBaked(int effectId, int strength) {
        effectStrength.get(DEFAULT_EXT_PREBAKED_STRENGTH).intValue();
        VibrationEffect effect = new ExtPrebaked(effectId + 4096, strength);
        effect.validate();
        return effect;
    }

    @NonNull
    public static VibrationEffect createEnvelope(@NonNull int[] relativeTimeArr, @NonNull int[] scaleArr, @NonNull int[] freqArr, boolean steepMode, int amplitude) {
        VibrationEffect effect = new Envelope(relativeTimeArr, scaleArr, freqArr, steepMode, amplitude);
        effect.validate();
        return effect;
    }

    @NonNull
    public static VibrationEffect createPatternHeParameter(int interval, int amplitude, int freq) {
        VibrationEffect effect = new PatternHeParameter(interval, amplitude, freq);
        effect.validate();
        return effect;
    }

    @NonNull
    public static VibrationEffect createPatternHeWithParam(@NonNull int[] patternInfo, int looper, int interval, int amplitude, int freq) {
        VibrationEffect effect = new PatternHe(patternInfo, looper, interval, amplitude, freq);
        effect.validate();
        return effect;
    }

    public static int checkIfRichTapSupport() {
        return 16717840;
    }

    public static final class ExtPrebaked extends VibrationEffect implements Parcelable {
        @NonNull public static final Parcelable.Creator<ExtPrebaked> CREATOR = new Parcelable.Creator<ExtPrebaked>() {
            @Override
            public ExtPrebaked createFromParcel(Parcel in) {
                in.readInt();
                return new ExtPrebaked(in);
            }

            @Override
            public ExtPrebaked[] newArray(int size) {
                return new ExtPrebaked[size];
            }
        };
        private int mEffectId;
        private int mStrength;

        private ExtPrebaked(@NonNull Parcel in) {
            this(in.readInt(), in.readInt());
        }

        public ExtPrebaked(int effectId, int strength) {
            mEffectId = effectId;
            mStrength = strength;
        }

        public int getId() {
            return mEffectId;
        }

        public int getScale() {
            return mStrength;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @NonNull
        @Override
        public ExtPrebaked resolve(int defaultAmplitude) {
            return this;
        }

        @NonNull
        @Override
        public ExtPrebaked scale(float scaleFactor) {
            return this;
        }

        @Override
        public long getDuration() {
            return -1L;
        }

        @Override
        public void validate() {
            if (mEffectId >= 0) {
                int i = mStrength;
                if (i < 1 || i > 100) {
                    throw new IllegalArgumentException("mStrength must be between 1 and 100 inclusive (mStrength=" + mStrength + ")");
                }
                return;
            }
            throw new IllegalArgumentException("Unknown ExtPrebaked effect type (value=" + mEffectId + ")");
        }

        public boolean equals(Object o) {
            if (!(o instanceof ExtPrebaked)) {
                return false;
            }
            ExtPrebaked other = (ExtPrebaked) o;
            return mEffectId == other.mEffectId;
        }

        public int hashCode() {
            return mEffectId;
        }

        public String toString() {
            return "ExtPrebaked{mEffectId=" + mEffectId + "mStrength = " + mStrength + "}";
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(PARCEL_TOKEN_EXT_PREBAKED);
            out.writeInt(mEffectId);
            out.writeInt(mStrength);
        }
    }

    public static final class Envelope extends VibrationEffect implements Parcelable {
        @NonNull public static final Parcelable.Creator<Envelope> CREATOR = new Parcelable.Creator<Envelope>() {

            @Override
            public Envelope createFromParcel(Parcel in) {
                in.readInt();
                return new Envelope(in);
            }

            @Override
            public Envelope[] newArray(int size) {
                return new Envelope[size];
            }
        };
        private int amplitude;
        private int[] freqArr;
        private int[] relativeTimeArr;
        private int[] scaleArr;
        private boolean steepMode;

        private Envelope(@NonNull Parcel in) {
            this(in.createIntArray(), in.createIntArray(), in.createIntArray(), in.readInt() != 1 ? false : true, in.readInt());
        }

        public Envelope(@NonNull int[] relativeTimeArr, @NonNull int[] scaleArr, @NonNull int[] freqArr, boolean steepMode, int amplitude) {
            relativeTimeArr = Arrays.copyOf(relativeTimeArr, 4);
            scaleArr = Arrays.copyOf(scaleArr, 4);
            freqArr = Arrays.copyOf(freqArr, 4);
            steepMode = steepMode;
            amplitude = amplitude;
        }

        @NonNull
        public int[] getRelativeTimeArr() {
            return relativeTimeArr;
        }

        @NonNull
        public int[] getScaleArr() {
            return scaleArr;
        }

        @NonNull
        public int[] getFreqArr() {
            return freqArr;
        }

        public boolean isSteepMode() {
            return steepMode;
        }

        public int getAmplitude() {
            return amplitude;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @NonNull
        @Override
        public Envelope resolve(int defaultAmplitude) {
            return this;
        }

        @NonNull
        @Override
        public Envelope scale(float scaleFactor) {
            return this;
        }

        @Override
        public long getDuration() {
            return -1L;
        }

        @Override
        public void validate() {
            for (int i = 0; i < 4; i++) {
                if (relativeTimeArr[i] < 0) {
                    throw new IllegalArgumentException("relative time can not be negative");
                } else if (scaleArr[i] < 0) {
                    throw new IllegalArgumentException("scale can not be negative");
                } else if (freqArr[i] < 0) {
                    throw new IllegalArgumentException("freq must be positive");
                }
            }
            int i2 = amplitude;
            if (i2 < -1 || i2 == 0 || i2 > 255) {
                throw new IllegalArgumentException("amplitude must either be DEFAULT_AMPLITUDE, or between 1 and 255 inclusive (amplitude=" + amplitude + ")");
            }
        }

        public boolean equals(Object o) {
            if (!(o instanceof Envelope)) {
                return false;
            }
            Envelope other = (Envelope) o;
            int[] timeArr = other.getRelativeTimeArr();
            int[] scArr = other.getScaleArr();
            int[] frArr = other.getFreqArr();
            return amplitude == other.getAmplitude() && Arrays.equals(timeArr, relativeTimeArr) && Arrays.equals(scArr, scaleArr) && Arrays.equals(frArr, freqArr) && other.isSteepMode() == steepMode;
        }

        public int hashCode() {
            return relativeTimeArr[2] + scaleArr[2] + freqArr[2];
        }

        public String toString() {
            return "Envelope: {relativeTimeArr=" + relativeTimeArr + ", scaleArr = " + scaleArr + ", freqArr = " + freqArr + ", SteepMode = " + steepMode + ", Amplitude = " + amplitude + "}";
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(PARCEL_TOKEN_ENVELOPE);
            out.writeIntArray(relativeTimeArr);
            out.writeIntArray(scaleArr);
            out.writeIntArray(freqArr);
            out.writeInt(steepMode ? 1 : 0);
            out.writeInt(amplitude);
        }
    }

    @SuppressLint("UserHandleName")
    public static final class PatternHeParameter extends VibrationEffect implements Parcelable {
        @NonNull public static final Parcelable.Creator<PatternHeParameter> CREATOR = new Parcelable.Creator<PatternHeParameter>() {

            @Override
            public PatternHeParameter createFromParcel(Parcel in) {
                in.readInt();
                return new PatternHeParameter(in);
            }

            @Override
            public PatternHeParameter[] newArray(int size) {
                return new PatternHeParameter[size];
            }
        };
        private final String TAG = "PatternHeParameter";
        private int mAmplitude;
        private int mFreq;
        private int mInterval;

        private PatternHeParameter(@NonNull Parcel in) {
            mInterval = in.readInt();
            mAmplitude = in.readInt();
            mFreq = in.readInt();
            Log.d("PatternHeParameter", "parcel mInterval:" + mInterval + " mAmplitude:" + mAmplitude + " mFreq:" + mFreq);
        }

        public PatternHeParameter(int interval, int amplitude, int freq) {
            mInterval = interval;
            mAmplitude = amplitude;
            mFreq = freq;
            Log.d("PatternHeParameter", "mInterval:" + mInterval + " mAmplitude:" + mAmplitude + " mFreq:" + mFreq);
        }

        public int getInterval() {
            return mInterval;
        }

        public int getAmplitude() {
            return mAmplitude;
        }

        public int getFreq() {
            return mFreq;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @NonNull
        @Override
        public PatternHeParameter resolve(int defaultAmplitude) {
            return this;
        }

        @NonNull
        @Override
        public PatternHeParameter scale(float scaleFactor) {
            return this;
        }

        @Override
        public long getDuration() {
            return -1L;
        }

        @Override
        public void validate() {
            int i = mAmplitude;
            if (i < -1 || i > 255 || mInterval < -1 || mFreq < -1) {
                throw new IllegalArgumentException("mAmplitude=" + mAmplitude + " mInterval=" + mInterval + " mFreq=" + mFreq);
            }
        }

        public boolean equals(Object o) {
            if (!(o instanceof PatternHeParameter)) {
                return false;
            }
            PatternHeParameter other = (PatternHeParameter) o;
            int interval = other.getInterval();
            int amplitude = other.getAmplitude();
            int freq = other.getFreq();
            return interval == mInterval && amplitude == mAmplitude && freq == mFreq;
        }

        public int hashCode() {
            int result = 14 + (mInterval * 37);
            return result + (mAmplitude * 37);
        }

        public String toString() {
            return "PatternHeParameter: {mAmplitude=" + mAmplitude + "}{mInterval=" + mInterval + "}";
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(PARCEL_TOKEN_PATTERN_HE_LOOP_PARAMETER);
            out.writeInt(mInterval);
            out.writeInt(mAmplitude);
            out.writeInt(mFreq);
            Log.d("PatternHeParameter", "writeToParcel mInterval:" + mInterval + " mAmplitude:" + mAmplitude + " mFreq:" + mFreq);
        }
    }

    public static final class PatternHe extends VibrationEffect implements Parcelable {
        @NonNull public static final Parcelable.Creator<PatternHe> CREATOR = new Parcelable.Creator<PatternHe>() {

            @Override
            public PatternHe createFromParcel(Parcel in) {
                in.readInt();
                return new PatternHe(in);
            }

            @Override
            public PatternHe[] newArray(int size) {
                return new PatternHe[size];
            }
        };
        private int mAmplitude;
        private long mDuration;
        private int mEventCount;
        private int mFreq;
        private int mInterval;
        private int mLooper;
        private int[] mPatternInfo;

        private PatternHe(@NonNull Parcel in) {
            mDuration = 100L;
            mPatternInfo = in.createIntArray();
            mLooper = in.readInt();
            mInterval = in.readInt();
            mAmplitude = in.readInt();
            mFreq = in.readInt();
        }

        public PatternHe(@NonNull int[] patternInfo, long duration, int eventCount) {
            mDuration = 100L;
            mPatternInfo = patternInfo;
            mDuration = duration;
            mEventCount = eventCount;
        }

        public PatternHe(@NonNull int[] patternInfo, int looper, int interval, int amplitude, int freq) {
            mDuration = 100L;
            mPatternInfo = patternInfo;
            mLooper = looper;
            mInterval = interval;
            mFreq = freq;
            mAmplitude = amplitude;
            mDuration = 100L;
            mEventCount = 0;
        }

        @Override
        public long getDuration() {
            return mDuration;
        }

        public int getEventCount() {
            return mEventCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @NonNull
        @Override
        public PatternHe resolve(int defaultAmplitude) {
            return this;
        }

        @NonNull
        @Override
        public PatternHe scale(float scaleFactor) {
            return this;
        }

        @NonNull
        public int[] getPatternInfo() {
            return mPatternInfo;
        }

        public int getLooper() {
            return mLooper;
        }

        public int getInterval() {
            return mInterval;
        }

        public int getAmplitude() {
            return mAmplitude;
        }

        public int getFreq() {
            return mFreq;
        }

        @Override
        public void validate() {
            if (mDuration <= 0) {
                throw new IllegalArgumentException("duration must be positive (duration=" + mDuration + ")");
            }
        }

        public boolean equals(Object o) {
            if (!(o instanceof PatternHe)) {
                return false;
            }
            PatternHe other = (PatternHe) o;
            return other.mDuration == mDuration && other.mPatternInfo == mPatternInfo;
        }

        public int hashCode() {
            int result = 17 + (((int) mDuration) * 37);
            return result + (mEventCount * 37);
        }

        public String toString() {
            return "PatternHe{mLooper=" + mLooper + ", mInterval=" + mInterval + "}";
        }

        @Override
        public void writeToParcel(@NonNull Parcel out, int flags) {
            out.writeInt(PARCEL_TOKEN_PATTERN_HE);
            out.writeIntArray(mPatternInfo);
            out.writeInt(mLooper);
            out.writeInt(mInterval);
            out.writeInt(mAmplitude);
            out.writeInt(mFreq);
        }
    }

    public static final boolean isExtendedEffect(int token) {
        switch (token) {
            case PARCEL_TOKEN_EXT_PREBAKED:
            case PARCEL_TOKEN_ENVELOPE:
            case PARCEL_TOKEN_PATTERN_HE:
            case PARCEL_TOKEN_PATTERN_HE_LOOP_PARAMETER:
                return true;
            default:
                return false;
        }
    }

    @NonNull
    public static final VibrationEffect createExtendedEffect(@NonNull Parcel in) {
        int offset = in.dataPosition() - 4;
        in.setDataPosition(offset);
        return CREATOR.createFromParcel(in);
    }
}
