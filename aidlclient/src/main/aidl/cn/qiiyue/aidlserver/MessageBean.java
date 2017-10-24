package cn.qiiyue.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Created by qiiyue
 * @Time 2017/10/22 20:22
 */

public class MessageBean implements Parcelable {

    private String name;
    private String content;

    public MessageBean() {
    }

    public MessageBean(String name, String content) {
        this.name = name;
        this.content = content;
    }

    protected MessageBean(Parcel in) {
        name = in.readString();
        content = in.readString();
    }

    public static final Creator<cn.qiiyue.aidlserver.MessageBean> CREATOR = new Creator<cn.qiiyue.aidlserver.MessageBean>() {
        @Override
        public cn.qiiyue.aidlserver.MessageBean createFromParcel(Parcel in) {
            return new cn.qiiyue.aidlserver.MessageBean(in);
        }

        @Override
        public cn.qiiyue.aidlserver.MessageBean[] newArray(int size) {
            return new cn.qiiyue.aidlserver.MessageBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(content);
    }

    public void readFromParcel(Parcel dest) {
        this.name = dest.readString();
        this.content = dest.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
