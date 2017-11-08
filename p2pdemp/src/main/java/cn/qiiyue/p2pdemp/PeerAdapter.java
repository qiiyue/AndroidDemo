package cn.qiiyue.p2pdemp;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @Created by qiiyue
 * @Time 2017/11/8 14:10
 */

public class PeerAdapter extends RecyclerView.Adapter {

    private final String TAG = getClass().getSimpleName();
    private Context context;
    private MainActivity activity;
    private ArrayList<WifiP2pDevice> peers;

    public PeerAdapter(Context context, ArrayList peers) {
        this.context = context;
        activity = (MainActivity) context;
        this.peers = peers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_peer_list, parent, false);
        PeerViewHolder viewHolder = new PeerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PeerViewHolder viewHolder = (PeerViewHolder) holder;
        viewHolder.tvPeerAddress.setText(peers.get(position).deviceName);
        initBtnClick(viewHolder.btnConnectPeer, peers.get(position));
    }

    private void initBtnClick(final Button btnConnectPeer, final WifiP2pDevice peer) {
        final int type = 0;//0-连接； 1-浏览文件； 2-发送文件
        btnConnectPeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 0) {
                    activity.connectPeer(peer, new WifiP2pManager.ActionListener() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "connectPeer onSuccess");
                            btnConnectPeer.setText("发送文件");
                        }

                        @Override
                        public void onFailure(int reason) {
                            Log.e(TAG, "connectPeer onFailure: " + reason);
                        }
                    });
                } else if (type == 1) {
                    sendFile();
                }

            }
        });
    }

    private void sendFile() {
        // TODO: 2017/11/8 发送文件
    }

    @Override
    public int getItemCount() {
        return peers.size();
    }

    class PeerViewHolder extends RecyclerView.ViewHolder {

        private TextView tvPeerAddress;
        private final Button btnConnectPeer;

        public PeerViewHolder(View itemView) {
            super(itemView);
            tvPeerAddress = itemView.findViewById(R.id.tv_peer_address);
            btnConnectPeer = itemView.findViewById(R.id.btn_connect_peer);
        }
    }


}
