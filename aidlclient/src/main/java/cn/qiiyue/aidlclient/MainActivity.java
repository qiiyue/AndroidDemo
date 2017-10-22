package cn.qiiyue.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.qiiyue.aidlserver.ICalculator;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ICalculator iCalculator;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "serviceConnection onServiceConnected");
            iCalculator = ICalculator.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "serviceConnection onServiceDisconnected");
            iCalculator = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.tv);

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("cn.qiiyue.aidlserver", "cn.qiiyue.aidlserver.RemoteServer"));
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = iCalculator.add(1, 2);
                    Log.d(TAG, "result: " + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
