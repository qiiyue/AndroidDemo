package cn.qiiyue.p2pdemp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * @Created by qiiyue
 * @Time 2017/11/7 16:03
 * <p>
 * wifi-p2p广播接收器
 */

public class WifiP2pBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = getClass().getSimpleName();
    private MainActivity activity;

    public WifiP2pBroadcastReceiver() {
    }

    public WifiP2pBroadcastReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.toString());
        String action = intent.getAction();
        if (action == null || action.isEmpty()) return;
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            Log.d(TAG, "WIFI_P2P_STATE_CHANGED_ACTION");
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {

            } else {

            }

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            Log.d(TAG, "WIFI_P2P_CONNECTION_CHANGED_ACTION");
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.isConnected()) {
                activity.handleNetworkInfo(networkInfo);
            }

        } else if (WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION.equals(action)) {
            Log.d(TAG, "WIFI_P2P_DISCOVERY_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            activity.requestPeer();Log.d(TAG, "WIFI_P2P_PEERS_CHANGED_ACTION");
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            Log.d(TAG, "WIFI_P2P_THIS_DEVICE_CHANGED_ACTION");
        }
    }

}
