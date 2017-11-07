package cn.qiiyue.p2pdemp;

import android.content.Context;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private WifiP2pManager mWifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pBroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private WifiP2pManager.PeerListListener mPeerListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建p2p管理类
        mWifiP2pManager = (WifiP2pManager) getApplicationContext().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), null);
        mBroadcastReceiver = new WifiP2pBroadcastReceiver();

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }

    /**
     * 查找设备
     */
    private void searchPeer() {
        mWifiP2pManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "searchPeer onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "searchPeer onFailure: " + reason);
            }
        });
    }

    /**
     * 查找设备
     */
    private void discoverPeer() {
        mWifiP2pManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "discoverPeer onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "discoverPeer onFailure: " + reason);
            }
        });

        mPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                // TODO: 2017/11/7 添加可连接设备
            }
        };
    }

    /**
     * 连接设备
     */
    private void connectPeer(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        mWifiP2pManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "connectPeer onSuccess");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "connectPeer onFailure: " + reason);
            }
        });
    }

    /**
     * 请求当前的设备列表
     */
    public void requestPeer() {
        mWifiP2pManager.requestPeers(mChannel, mPeerListListener);
    }

    public void handleNetworkInfo(NetworkInfo networkInfo) {
        mWifiP2pManager.requestConnectionInfo(mChannel, new WifiP2pManager.ConnectionInfoListener() {
            @Override
            public void onConnectionInfoAvailable(WifiP2pInfo info) {
                if (!info.groupFormed) return;
                if (info.isGroupOwner) {
// TODO: 2017/11/7 执行组长任务
                } else {
// TODO: 2017/11/7 执行普通组员任务
                }
            }
        });
    }

}
