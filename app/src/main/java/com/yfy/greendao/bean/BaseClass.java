package com.yfy.greendao.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseClass implements Parcelable {


    /**
     * classid : 1140
     * classname : 测试八班
     */

    private String classid;
    private String classname;

    public BaseClass() {
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.classid);
        dest.writeString(this.classname);
    }



    protected BaseClass(Parcel in) {
        this.classid = in.readString();
        this.classname = in.readString();
    }

    public static final Creator<BaseClass> CREATOR = new Creator<BaseClass>() {
        @Override
        public BaseClass createFromParcel(Parcel source) {
            return new BaseClass(source);
        }

        @Override
        public BaseClass[] newArray(int size) {
            return new BaseClass[size];
        }
    };
}
