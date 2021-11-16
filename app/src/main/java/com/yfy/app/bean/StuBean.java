package com.yfy.app.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class StuBean implements Parcelable {

    /**
     * stuid : 1908
     * stuname : 龙妙兮
     * headpic : https://www.cdeps.sc.cn/ClientBin/head.png
     */

    private String stuid;
    private String stuname;
    private String headpic;
    private String stusex;
    private boolean is_selected=false;//用于判断


    public boolean isIs_selected() {
        return is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected = is_selected;
    }


    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public StuBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stuid);
        dest.writeString(this.stuname);
        dest.writeString(this.headpic);
        dest.writeByte(this.is_selected ? (byte) 1 : (byte) 0);
    }

    protected StuBean(Parcel in) {
        this.stuid = in.readString();
        this.stuname = in.readString();
        this.headpic = in.readString();
        this.is_selected = in.readByte() != 0;
    }

    public static final Creator<StuBean> CREATOR = new Creator<StuBean>() {
        @Override
        public StuBean createFromParcel(Parcel source) {
            return new StuBean(source);
        }

        @Override
        public StuBean[] newArray(int size) {
            return new StuBean[size];
        }
    };
}
