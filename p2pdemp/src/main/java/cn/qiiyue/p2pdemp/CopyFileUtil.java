package cn.qiiyue.p2pdemp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Created by qiiyue
 * @Time 2017/11/11 14:00
 */

public class CopyFileUtil {

    public static void copyFile(InputStream in, OutputStream out) {
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
