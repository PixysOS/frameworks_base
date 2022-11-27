package vendor.aac.hardware.richtap.vibrator;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IRichtapCallback extends IInterface {
    public static final String DESCRIPTOR = "vendor$aac$hardware$richtap$vibrator$IRichtapCallback".replace('$', '.');
    public static final String HASH = "91cb4bbb88c1b67a487d70aa20da36a0163fbd55";
    public static final int VERSION = 1;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onCallback(int i) throws RemoteException;

    /* loaded from: classes2.dex */
    public static class Default implements IRichtapCallback {
        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
        public void onCallback(int status) throws RemoteException {
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    /* loaded from: classes2.dex */
    public static abstract class Stub extends Binder implements IRichtapCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onCallback = 1;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IRichtapCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IRichtapCallback)) {
                return new Proxy(obj);
            }
            return (IRichtapCallback) iin;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
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
                        case 1:
                            data.enforceInterface(descriptor);
                            int _arg0 = data.readInt();
                            onCallback(_arg0);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes2.dex */
        private static class Proxy implements IRichtapCallback {
            public static IRichtapCallback sDefaultImpl;
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

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
            public void onCallback(int status) throws RemoteException {
                Parcel _data = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(status);
                    boolean _status = mRemote.transact(1, _data, null, 1);
                    if (_status) {
                        return;
                    }
                    if (Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallback(status);
                        return;
                    }
                    throw new RemoteException("Method onCallback is unimplemented.");
                } finally {
                    _data.recycle();
                }
            }

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
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

            @Override // vendor.aac.hardware.richtap.vibrator.IRichtapCallback
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

        public static boolean setDefaultImpl(IRichtapCallback impl) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            } else if (impl == null) {
                return false;
            } else {
                Proxy.sDefaultImpl = impl;
                return true;
            }
        }

        public static IRichtapCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
