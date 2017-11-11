package cn.qiiyue.p2pdemp;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Created by qiiyue
 * @Time 2017/11/9 17:38
 * <p>
 * 发送文件任务
 */

public class SendFileService extends IntentService {

    private final String TAG = getClass().getSimpleName();
    public static final String EXTRA_URI_STRING = "extra_uri_string";
    public static final String EXTRA_HOST = "host";
    public static final String EXTRA_PORT = "port";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SendFileService(String name) {
        super(name);
    }

    public SendFileService() {
        super("SendFileService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String uriStr = intent.getStringExtra(EXTRA_URI_STRING);
        String host = intent.getStringExtra(EXTRA_HOST);
        int port = intent.getIntExtra(EXTRA_PORT, -1);
        Log.d(TAG, "host: " + host);
        Log.d(TAG, "port: " + port);
        Log.d(TAG, "uriStr: " + uriStr);
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            OutputStream outputStream = socket.getOutputStream();
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream;
            inputStream = contentResolver.openInputStream(Uri.parse(uriStr));
            CopyFileUtil.copyFile(inputStream, outputStream);
            Log.d(TAG, "try finish");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
