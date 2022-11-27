package vendor.aac.hardware.richtap.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import vendor.aac.hardware.richtap.vibrator.IRichtapCallback;

/* loaded from: classes2.dex */
public interface IRichtapVibrator extends IInterface {
    public static final String DESCRIPTOR = "vendor$aac$hardware$richtap$vibrator$IRichtapVibrator".replace('$', '.');
    public static final String HASH = "91cb4bbb88c1b67a487d70aa20da36a0163fbd55";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void init(IRichtapCallback iRichtapCallback) throws RemoteException;

    void off(IRichtapCallback iRichtapCallback) throws RemoteException;

    void on(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    int perform(int i, byte b, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performEnvelope(int[] iArr, boolean z, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performHe(int i, int i2, int i3, int i4, int[] iArr, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performHeParam(int i, int i2, int i3, IRichtapCallback iRichtapCallback) throws RemoteException;

    void performRtp(ParcelFileDescriptor parcelFileDescriptor, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setAmplitude(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setDynamicScale(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void setF0(int i, IRichtapCallback iRichtapCallback) throws RemoteException;

    void stop(IRichtapCallback iRichtapCallback) throws RemoteException;

    /* loaded from: classes2.dex */
    public static class Default implements IRichtapVibrator {
        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void init(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setDynamicScale(int scale, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setF0(int f0, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void stop(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void setAmplitude(int amplitude, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void off(IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void on(int timeoutMs, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public int perform(int effect_id, byte strength, IRichtapCallback callback) throws RemoteException {
            return 0;
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IRichtapVibrator {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_off = 7;
        static final int TRANSACTION_on = 8;
        static final int TRANSACTION_perform = 9;
        static final int TRANSACTION_performEnvelope = 10;
        static final int TRANSACTION_performHe = 12;
        static final int TRANSACTION_performHeParam = 6;
        static final int TRANSACTION_performRtp = 11;
        static final int TRANSACTION_setAmplitude = 5;
        static final int TRANSACTION_setDynamicScale = 2;
        static final int TRANSACTION_setF0 = 3;
        static final int TRANSACTION_stop = 4;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRichtapVibrator asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRichtapVibrator)) {
                return new Proxy(obj);
            }
            return (IRichtapVibrator) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            ParcelFileDescriptor _arg0;
            String descriptor = DESCRIPTOR;
            switch (code) {
                case TRANSACTION_getInterfaceHash:
                    data.enforceInterface(descriptor);
                    reply.writeNoException();
                    reply.writeString(getInterfaceHash());
                    return true;
                case TRANSACTION_getInterfaceVersion:
                    data.enforceInterface(descriptor);
                    reply.writeNoException();
                    reply.writeInt(getInterfaceVersion());
                    return true;
                case INTERFACE_TRANSACTION:
                    reply.writeString(descriptor);
                    return true;
                default:
                    switch (code) {
                        case TRANSACTION_init:
                            data.enforceInterface(descriptor);
                            IRichtapCallback _arg02 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            init(_arg02);
                            return true;
                        case TRANSACTION_setDynamicScale:
                            data.enforceInterface(descriptor);
                            int _arg03 = data.readInt();
                            IRichtapCallback _arg1 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            setDynamicScale(_arg03, _arg1);
                            return true;
                        case TRANSACTION_setF0:
                            data.enforceInterface(descriptor);
                            int _arg04 = data.readInt();
                            IRichtapCallback _arg12 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            setF0(_arg04, _arg12);
                            return true;
                        case TRANSACTION_stop:
                            data.enforceInterface(descriptor);
                            IRichtapCallback _arg05 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            stop(_arg05);
                            reply.writeNoException();
                            return true;
                        case TRANSACTION_setAmplitude:
                            data.enforceInterface(descriptor);
                            int _arg06 = data.readInt();
                            IRichtapCallback _arg13 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            setAmplitude(_arg06, _arg13);
                            return true;
                        case TRANSACTION_performHeParam:
                            data.enforceInterface(descriptor);
                            int _arg07 = data.readInt();
                            int _arg14 = data.readInt();
                            int _arg2 = data.readInt();
                            IRichtapCallback _arg3 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            performHeParam(_arg07, _arg14, _arg2, _arg3);
                            reply.writeNoException();
                            return true;
                        case TRANSACTION_off:
                            data.enforceInterface(descriptor);
                            IRichtapCallback _arg08 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            off(_arg08);
                            return true;
                        case TRANSACTION_on:
                            data.enforceInterface(descriptor);
                            int _arg09 = data.readInt();
                            IRichtapCallback _arg15 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            on(_arg09, _arg15);
                            return true;
                        case TRANSACTION_perform:
                            data.enforceInterface(descriptor);
                            int _arg010 = data.readInt();
                            byte _arg16 = data.readByte();
                            IRichtapCallback _arg22 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            int _result = perform(_arg010, _arg16, _arg22);
                            reply.writeNoException();
                            reply.writeInt(_result);
                            return true;
                        case TRANSACTION_performEnvelope:
                            data.enforceInterface(descriptor);
                            int[] _arg011 = data.createIntArray();
                            boolean _arg17 = data.readInt() != 0;
                            IRichtapCallback _arg23 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            performEnvelope(_arg011, _arg17, _arg23);
                            return true;
                        case TRANSACTION_performRtp:
                            data.enforceInterface(descriptor);
                            if (data.readInt() != 0) {
                                _arg0 = (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(data);
                            } else {
                                _arg0 = null;
                            }
                            IRichtapCallback _arg18 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            performRtp(_arg0, _arg18);
                            return true;
                        case TRANSACTION_performHe:
                            data.enforceInterface(descriptor);
                            int _arg012 = data.readInt();
                            int _arg19 = data.readInt();
                            int _arg24 = data.readInt();
                            int _arg32 = data.readInt();
                            int[] _arg4 = data.createIntArray();
                            IRichtapCallback _arg5 = IRichtapCallback.Stub.asInterface(data.readStrongBinder());
                            performHe(_arg012, _arg19, _arg24, _arg32, _arg4, _arg5);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        public static class Proxy implements IRichtapVibrator {
            public static IRichtapVibrator sDefaultImpl;
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void init(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(1, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().init(callback);
                        return;
                    }
                    throw new RemoteException("Method init is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setDynamicScale(int scale, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(scale);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(2, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDynamicScale(scale, callback);
                        return;
                    }
                    throw new RemoteException("Method setDynamicScale is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setF0(int f0, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(f0);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(3, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setF0(f0, callback);
                        return;
                    }
                    throw new RemoteException("Method setF0 is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void stop(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(4, _data, _reply, 0);
                    if (_status) {
                        _reply.readException();
                    } else if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop(callback);
                    } else {
                        throw new RemoteException("Method stop is unimplemented.");
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void setAmplitude(int amplitude, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(amplitude);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(5, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAmplitude(amplitude, callback);
                        return;
                    }
                    throw new RemoteException("Method setAmplitude is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performHeParam(int interval, int amplitude, int freq, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(interval);
                    _data.writeInt(amplitude);
                    _data.writeInt(freq);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(6, _data, _reply, 0);
                    if (_status) {
                        _reply.readException();
                    } else if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performHeParam(interval, amplitude, freq, callback);
                    } else {
                        throw new RemoteException("Method performHeParam is unimplemented.");
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void off(IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(7, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().off(callback);
                        return;
                    }
                    throw new RemoteException("Method off is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void on(int timeoutMs, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(timeoutMs);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(8, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().on(timeoutMs, callback);
                        return;
                    }
                    throw new RemoteException("Method on is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public int perform(int effect_id, byte strength, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(effect_id);
                    _data.writeByte(strength);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(9, _data, _reply, 0);
                    if (_status) {
                        _reply.readException();
                        int _result = _reply.readInt();
                        return _result;
                    } else if (Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().perform(effect_id, strength, callback);
                    } else {
                        throw new RemoteException("Method perform is unimplemented.");
                    }
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performEnvelope(int[] envInfo, boolean fastFlag, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeIntArray(envInfo);
                    _data.writeInt(fastFlag ? 1 : 0);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(10, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performEnvelope(envInfo, fastFlag, callback);
                        return;
                    }
                    throw new RemoteException("Method performEnvelope is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performRtp(ParcelFileDescriptor hdl, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if (hdl != null) {
                        _data.writeInt(1);
                        hdl.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(11, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performRtp(hdl, callback);
                        return;
                    }
                    throw new RemoteException("Method performRtp is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public void performHe(int looper, int interval, int amplitude, int freq, int[] he, IRichtapCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(looper);
                    _data.writeInt(interval);
                    _data.writeInt(amplitude);
                    _data.writeInt(freq);
                    _data.writeIntArray(he);
                    _data.writeStrongBinder(callback != null ? callback.asBinder() : null);
                    boolean _status = mRemote.transact(12, _data, null, 1);
                    if (_status) {
                        _data.recycle();
                    } else if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performHe(looper, interval, amplitude, freq, he, callback);
                        _data.recycle();
                    } else {
                       throw new RemoteException("Method performHe is unimplemented.");
                    }
                } catch (Throwable th) {
                    _data.recycle();
                    throw th;
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public int getInterfaceVersion() throws RemoteException {
                if (mCachedVersion == -1) {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    try {
                        data.writeInterfaceToken(DESCRIPTOR);
                        boolean _status = mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, data, reply, 0);
                        if (!_status && Stub.getDefaultImpl() != null) {
                            return Stub.getDefaultImpl().getInterfaceVersion();
                        }
                        reply.readException();
                        mCachedVersion = reply.readInt();
                    } finally {
                        reply.recycle();
                        data.recycle();
                    }
                }
                return mCachedVersion;
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapVibrator
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(mCachedHash)) {
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    data.writeInterfaceToken(DESCRIPTOR);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getInterfaceHash, data, reply, 0);
                    if (_status || Stub.getDefaultImpl() == null) {
                        reply.readException();
                        mCachedHash = reply.readString();
                        reply.recycle();
                        data.recycle();
                    } else {
                        String interfaceHash = Stub.getDefaultImpl().getInterfaceHash();
                        reply.recycle();
                        data.recycle();
                        return interfaceHash;
                    }
                }
                return mCachedHash;
            }
        }

        public static boolean setDefaultImpl(IRichtapVibrator impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IRichtapVibrator getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
