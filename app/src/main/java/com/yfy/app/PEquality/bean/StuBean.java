package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class StuBean implements Parcelable {
    /**
     * stuname : 邹欢
     * stuheadpic : http://whqfgk.yfyit.com/ClientBin/head.png
     * stuid : 103
     * gradeid : 9
     * stusex : 男
     * stunation : 阿尔及利亚
     */

    private String stuname;
    private String stuheadpic;
    private String stuid;
    private String gradeid;
    private String stusex;
    private String stunation;

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStuheadpic() {
        return stuheadpic;
    }

    public void setStuheadpic(String stuheadpic) {
        this.stuheadpic = stuheadpic;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getGradeid() {
        return gradeid;
    }

    public void setGradeid(String gradeid) {
        this.gradeid = gradeid;
    }

    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    public String getStunation() {
        return stunation;
    }

    public void setStunation(String stunation) {
        this.stunation = stunation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stuname);
        dest.writeString(this.stuheadpic);
        dest.writeString(this.stuid);
        dest.writeString(this.gradeid);
        dest.writeString(this.stusex);
        dest.writeString(this.stunation);
    }

    public StuBean() {
    }

    protected StuBean(Parcel in) {
        this.stuname = in.readString();
        this.stuheadpic = in.readString();
        this.stuid = in.readString();
        this.gradeid = in.readString();
        this.stusex = in.readString();
        this.stunation = in.readString();
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
