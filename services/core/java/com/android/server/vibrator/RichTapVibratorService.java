package com.android.server.vibrator;

import android.content.Context;
import android.hardware.vibrator.IVibrator;
import android.os.Binder;
import android.os.CombinedVibration;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RichTapVibrationEffect;
import android.os.ServiceManager;
import android.os.VibrationEffect;
import android.telephony.TelephonyManager;
import android.util.Slog;
import vendor.aac.hardware.richtap.vibrator.IRichtapCallback;
import vendor.aac.hardware.richtap.vibrator.IRichtapVibrator;

public class RichTapVibratorService {
    private static final boolean DEBUG = true;
    private static final String TAG = "RichTapVibratorService";
    static SenderId mCurrentSenderId = new SenderId(0, 0);
    private IRichtapCallback mCallback;
    private boolean mSupportRichTap;
    private ParcelFileDescriptor rtpFD;
    private volatile IRichtapVibrator sRichtapVibratorService = null;
    private VibHalDeathRecipient mHalDeathLinker = null;
    private final int SDK_HAL_NEW_FORMAT_DATA_VERSION = 2;

    private IRichtapVibrator getRichtapService() {
        synchronized (RichTapVibratorService.class) {
            if (sRichtapVibratorService == null) {
                String vibratorDescriptor = "android$hardware$vibrator$IVibrator".replace('$', '.') + "/default";
                Slog.d(TAG, "vibratorDescriptor:" + vibratorDescriptor);
                IVibrator vibratorHalService = IVibrator.Stub.asInterface(ServiceManager.getService(vibratorDescriptor));
                if (vibratorHalService == null) {
                    Slog.d(TAG, "can not get hal service");
                    return null;
                }
                Slog.d(TAG, "vibratorHalService:" + vibratorHalService);
                try {
                    Slog.d(TAG, "Capabilities:" + vibratorHalService.getCapabilities());
                } catch (Exception e) {
                    Slog.d(TAG, "getCapabilities faile", e);
                }
                try {
                    IBinder binder = vibratorHalService.asBinder().getExtension();
                    if (binder != null) {
                        sRichtapVibratorService = IRichtapVibrator.Stub.asInterface(Binder.allowBlocking(binder));
                        mHalDeathLinker = new VibHalDeathRecipient(this);
                        mCurrentSenderId.reset();
                        binder.linkToDeath(mHalDeathLinker, 0);
                    } else {
                        sRichtapVibratorService = null;
                        Slog.e(TAG, "getExtension == null");
                    }
                } catch (Exception e2) {
                    Slog.e(TAG, "getExtension fail", e2);
                }
            }
            return sRichtapVibratorService;
        }
    }

    public RichTapVibratorService(boolean supportRichTap, IRichtapCallback callback) {
        mSupportRichTap = false;
        mSupportRichTap = supportRichTap;
        mCallback = callback;
    }

    public boolean disposeTelephonyCallState(Context context) {
        boolean calling = false;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (2 == telephonyManager.getCallState()) {
            calling = true;
        }
        if (calling) {
            Slog.i(TAG, "current is calling state, stop richtap effect loop");
            richTapVibratorStop();
        }
        return calling;
    }

    public boolean disposeRichtapEffectParams(CombinedVibration combEffect) {
        if (!(combEffect instanceof CombinedVibration.Mono)) {
            return false;
        }
        VibrationEffect effect = ((CombinedVibration.Mono) combEffect).getEffect();
        if (effect instanceof RichTapVibrationEffect.PatternHeParameter) {
            RichTapVibrationEffect.PatternHeParameter param = (RichTapVibrationEffect.PatternHeParameter)effect;
            int interval = param.getInterval();
            int amplitude = param.getAmplitude();
            int freq = param.getFreq();
            Slog.d(TAG, "recive data  interval:" + interval + " amplitude:" + amplitude + " freq:" + freq);
            try {
                IRichtapVibrator service = getRichtapService();
                if (service == null) {
                    return true;
                }
                Slog.d(TAG, "aac richtap performHeParam");
                service.performHeParam(interval, amplitude, freq, mCallback);
                return true;
            } catch (Exception e) {
                Slog.e(TAG, "aac richtap performHeParam fail.", e);
                return true;
            }
        } else {
            Slog.d(TAG, "none richtap effect, do nothing!!");
            return false;
        }
    }

    public void richTapVibratorOn(long millis) {
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "aac richtap doVibratorOn");
                service.on((int) millis, mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorOn fail.", e);
        }
    }

    public void richTapVibratorOff() {
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "aac richtap doVibratorOff");
                service.off(mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorOff fail.", e);
        }
    }

    public void richTapVibratorSetAmplitude(int amplitude) {
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "aac richtap doVibratorSetAmplitude");
                service.setAmplitude(amplitude, mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorSetAmplitude fail.", e);
        }
    }

    public int richTapVibratorPerform(int id, byte scale) {
        int timeout = 0;
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "perform richtap vibrator");
                timeout = service.perform(id, scale, mCallback);
                Slog.d(TAG, "aac richtap perform timeout:" + timeout);
                return timeout;
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap perform fail.", e);
        }
        return timeout;
    }

    public int getRichTapPrebakStrength(int effectStrength) {
        switch (effectStrength) {
            case 0:
                return 69;
            case 1:
                return 89;
            case 2:
                return 99;
            default:
                Slog.d(TAG, "wrong Effect Strength!!");
                return 0;
        }
    }

    public void richTapVibratorOnEnvelope(int[] relativeTime, int[] scaleArr, int[] freqArr, boolean steepMode, int amplitude) {
        int[] params = new int[12];
        for (int i = 0; i < relativeTime.length; i++) {
            params[i * 3] = relativeTime[i];
            params[(i * 3) + 1] = scaleArr[i];
            params[(i * 3) + 2] = freqArr[i];
            String temp = String.format("relativeTime, scale, freq = { %d, %d, %d }", Integer.valueOf(params[i * 3]), Integer.valueOf(params[(i * 3) + 1]), Integer.valueOf(params[(i * 3) + 2]));
            Slog.d(TAG, temp);
        }
        Slog.d(TAG, "vibrator perform envelope");
        richTapVibratorSetAmplitude(amplitude);
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "aac richtap performEnvelope");
                service.performEnvelope(params, steepMode, mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap performEnvelope fail.", e);
        }
    }

    public void richTapVibratorOnPatternHe(VibrationEffect effect) {
        RichTapVibrationEffect.PatternHe newEffect = (RichTapVibrationEffect.PatternHe) effect;
        int[] pattern = newEffect.getPatternInfo();
        int looper = newEffect.getLooper();
        int interval = newEffect.getInterval();
        int amplitude = newEffect.getAmplitude();
        int freq = newEffect.getFreq();
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                service.performHe(looper, interval, amplitude, freq, pattern, mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorOnPatternHe fail.", e);
        }
    }

    public void richTapVibratorOnRawPattern(int[] pattern, int amplitude, int freq) {
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                service.performHe(1, 0, amplitude, freq, pattern, mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorOnPatternHe fail.", e);
        }
    }

    public void richTapVibratorStop() {
        try {
            IRichtapVibrator service = getRichtapService();
            if (service != null) {
                Slog.d(TAG, "aac richtap doVibratorStop");
                Slog.d(TAG, "richtap service stop!!");
                service.stop(mCallback);
            }
        } catch (Exception e) {
            Slog.e(TAG, "aac richtap doVibratorStop fail.", e);
        }
    }

    void resetHalServiceProxy() {
        sRichtapVibratorService = null;
    }

    private static final class VibHalDeathRecipient implements IBinder.DeathRecipient {
        RichTapVibratorService mRichTapService;

        VibHalDeathRecipient(RichTapVibratorService richtapService) {
            mRichTapService = richtapService;
        }

        @Override
        public void binderDied() {
            Slog.d(RichTapVibratorService.TAG, "vibrator hal died,should reset hal proxy!!");
            synchronized (VibHalDeathRecipient.class) {
                if (mRichTapService != null) {
                    Slog.d(RichTapVibratorService.TAG, "vibrator hal reset hal proxy");
                    mRichTapService.resetHalServiceProxy();
                }
            }
        }
    }

    public boolean checkIfPrevPatternData(SenderId senderId) {
        if (senderId.getPid() == mCurrentSenderId.getPid() && senderId.getSeq() == mCurrentSenderId.getSeq()) {
            return true;
        }
        return false;
    }

    public void setCurrentSenderId(SenderId senderId) {
        mCurrentSenderId.setPid(senderId.getPid());
        mCurrentSenderId.setSeq(senderId.getSeq());
    }

    public void resetCurrentSenderId() {
        mCurrentSenderId.reset();
    }

    public SenderId getSenderId(VibrationEffect effect) {
        if (!(effect instanceof RichTapVibrationEffect.PatternHe)) {
            return null;
        }
        RichTapVibrationEffect.PatternHe patternHe = (RichTapVibrationEffect.PatternHe) effect;
        int[] patternData = patternHe.getPatternInfo();
        if (patternData == null || patternData.length <= 0) {
            return null;
        }
        int versionOrType = patternData[0];
        if (versionOrType != 2) {
            return null;
        }
        int pid = patternData[2];
        int seq = patternData[3];
        Slog.d(TAG, "get sender id pid:" + pid + " seq:" + seq);
        return new SenderId(pid, seq);
    }

    public boolean checkIfEffectHe2_0(VibrationEffect effect) {
        if (effect instanceof RichTapVibrationEffect.PatternHe) {
            RichTapVibrationEffect.PatternHe patternHe = (RichTapVibrationEffect.PatternHe) effect;
            int[] patternData = patternHe.getPatternInfo();
            int versionOrType = patternData[0];
            if (versionOrType == 2) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfFirstHe2_0Package(VibrationEffect effect) {
        if (!(effect instanceof RichTapVibrationEffect.PatternHe)) {
            return false;
        }
        RichTapVibrationEffect.PatternHe patternHe = (RichTapVibrationEffect.PatternHe) effect;
        int[] patternData = patternHe.getPatternInfo();
        int firstPatternIndexInPackage = patternData[5];
        Slog.d(TAG, "checkIfFirstHe2_0Package firstPatternIndexInPackage: " + firstPatternIndexInPackage);
        if (firstPatternIndexInPackage == 0) {
            return true;
        }
        return false;
    }

    public static class SenderId {
        int mPid;
        int mSeq;

        SenderId(int pid, int seq) {
            mPid = pid;
            mSeq = seq;
        }

        void setPid(int pid) {
            mPid = pid;
        }

        int getPid() {
            return mPid;
        }

        void setSeq(int seq) {
            mSeq = seq;
        }

        int getSeq() {
            return mSeq;
        }

        void reset() {
            mPid = 0;
            mSeq = 0;
        }
    }
}
