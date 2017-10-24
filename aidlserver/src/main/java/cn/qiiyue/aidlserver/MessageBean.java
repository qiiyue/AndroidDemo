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

    public static final Creator<MessageBean> CREATOR = new Creator<MessageBean>() {
        @Override
        public MessageBean createFromParcel(Parcel in) {
            return new MessageBean(in);
        }

        @Override
        public MessageBean[] newArray(int size) {
            return new MessageBean[size];
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
