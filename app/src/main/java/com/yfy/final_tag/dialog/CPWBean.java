package com.yfy.final_tag.dialog;

import android.os.Parcel;
import android.os.Parcelable;

public class CPWBean implements Parcelable {
    private String name;
    private String id;
    private String value;
    private String type;
    private boolean is_select;
    private boolean is_show=false;


    public CPWBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public CPWBean() {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIs_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.id);
        dest.writeString(this.value);
        dest.writeString(this.type);
        dest.writeByte(this.is_select ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is_show ? (byte) 1 : (byte) 0);
    }

    protected CPWBean(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.value = in.readString();
        this.type = in.readString();
        this.is_select = in.readByte() != 0;
        this.is_show = in.readByte() != 0;
    }

    public static final Creator<CPWBean> CREATOR = new Creator<CPWBean>() {
        @Override
        public CPWBean createFromParcel(Parcel source) {
            return new CPWBean(source);
        }

        @Override
        public CPWBean[] newArray(int size) {
            return new CPWBean[size];
        }
    };
}
