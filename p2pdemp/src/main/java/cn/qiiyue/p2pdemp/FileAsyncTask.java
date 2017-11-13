package cn.qiiyue.p2pdemp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Created by qiiyue
 * @Time 2017/11/11 15:29
 */

public class FileAsyncTask extends AsyncTask {

    private int port;
    private File file;
    private Context context;
    private MainActivity activity;

    @Override
    protected Object doInBackground(Object[] objects) {
        context = (Context) objects[0];
        activity = (MainActivity)context;
        port = (int) objects[1];
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
            file = new File(path, "p2p.png");
            CopyFileUtil.copyFile(inputStream, new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        activity.showImg(file);
    }

}
