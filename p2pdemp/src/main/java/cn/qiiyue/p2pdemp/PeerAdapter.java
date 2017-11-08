package cn.qiiyue.p2pdemp;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.v7.widget.RecyclerView;
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
        PeerViewHolder viewHolder = (PeerViewHolder) holder;
        viewHolder.tvPeerAddress.setText(peers.get(position).deviceAddress);
        viewHolder.btnConnectPeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.connectPeer(peers.get(position));
            }
        });
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
