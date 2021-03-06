package cn.qiiyue.p2pdemp;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    public final int REQUEST_CODE_SELECT_FILE = 131;
    public final int SOCKET_PORT = 8001;
    private WifiP2pManager mWifiP2pManager;
    private WifiP2pManager.Channel mChannel;
    private WifiP2pBroadcastReceiver mBroadcastReceiver;
    private IntentFilter mIntentFilter;
    private WifiP2pManager.PeerListListener mPeerListListener;
    private Button mSearchBtn;
    private RecyclerView mContainerRl;
    private ArrayList<WifiP2pDevice> mPeerList;
    private PeerAdapter mPeerAdapter;
    private WifiP2pInfo mWifiP2pInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initListener();

        initP2pManager();

    }

    /**
     * 初始化p2p manager 相关
     */
    private void initP2pManager() {
        //创建p2p管理类
        mWifiP2pManager = (WifiP2pManager) getApplicationContext().getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mWifiP2pManager.initialize(this, getMainLooper(), null);
        mBroadcastReceiver = new WifiP2pBroadcastReceiver(this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_DISCOVERY_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mPeerList = new ArrayList<>();
        mPeerAdapter = new PeerAdapter(this, mPeerList);
        mContainerRl.setAdapter(mPeerAdapter);

    }

    private void initListener() {
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPeer();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mContainerRl = (RecyclerView) findViewById(R.id.rl_container);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mContainerRl.setLayoutManager(layoutManager);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult requestCode: " + requestCode + " ; resultCode: " + resultCode);
        if (requestCode == REQUEST_CODE_SELECT_FILE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.d(TAG, "uri: " + uri.toString());
            mPeerAdapter.sendFile(uri);
        }
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

        mPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                mPeerList.clear();
                mPeerList.addAll(peers.getDeviceList());
                mPeerAdapter.notifyDataSetChanged();
            }
        };
    }

    /**
     * 连接设备
     */
    public void connectPeer(WifiP2pDevice device, WifiP2pManager.ActionListener actionListener) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        mWifiP2pManager.connect(mChannel, config, actionListener);
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
                mWifiP2pInfo = info;
                if (!info.groupFormed) return;
                if (info.isGroupOwner) {
                    mPeerAdapter.setOwner(true);
                    new FileAsyncTask().execute(MainActivity.this, SOCKET_PORT);
                } else {
// TODO: 2017/11/7 执行普通组员任务
                    mPeerAdapter.setOwner(false);
                }
            }
        });
    }

    public WifiP2pInfo getmWifiP2pInfo() {
        return mWifiP2pInfo;
    }

    public void showImg(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Log.d(TAG, "showImg: " + file);
        intent.setDataAndType(Uri.parse("file://" + file), "image/*");
        startActivity(intent);
    }
}
