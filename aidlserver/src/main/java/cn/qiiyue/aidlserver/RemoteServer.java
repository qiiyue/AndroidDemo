package cn.qiiyue.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteServer extends Service {

    private final String TAG = "RemoteServer";

    private IBinder iBinder = new ICalculator.Stub() {

        @Override
        public int add(int a, int b) throws RemoteException {
            Log.d(TAG, "RemoteServer add");
            return a + b;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        //绑定service后返回ibinder
        Log.d(TAG, "RemoteServer onBind");
        return iBinder;
    }

}
