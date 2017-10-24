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
            return a + b;
        }

        @Override
        public MessageBean getMessageBean() throws RemoteException {
            MessageBean messageBean = new MessageBean("Kobe", "black mamba");
            return messageBean;
        }

        @Override
        public void messageIn(MessageBean msg) throws RemoteException {
            Log.d(TAG, "messageIn: " + msg.toString());
        }

        @Override
        public void messageOut(MessageBean msg) throws RemoteException {
            Log.d(TAG, "messageOut: " + msg.toString());
            msg.setName("James");
            msg.setContent("is number.2");
        }

        @Override
        public void messageInOut(MessageBean msg) throws RemoteException {
            Log.d(TAG, "messageInOut: " + msg.toString());
            msg.setName("Wade");
            msg.setContent("flash man");
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        //绑定service后返回ibinder
        Log.d(TAG, "RemoteServer onBind");
        return iBinder;
    }

}
