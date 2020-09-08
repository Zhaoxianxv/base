package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Motbean implements Parcelable {

    /**
     * nation_name : 阿尔及利亚
     * nation_stu : 4
     */

    private String nation_name;
    private String nation_stu;

    public String getNation_name() {
        return nation_name;
    }

    public void setNation_name(String nation_name) {
        this.nation_name = nation_name;
    }

    public String getNation_stu() {
        return nation_stu;
    }

    public void setNation_stu(String nation_stu) {
        this.nation_stu = nation_stu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nation_name);
        dest.writeString(this.nation_stu);
    }

    public Motbean() {
    }

    protected Motbean(Parcel in) {
        this.nation_name = in.readString();
        this.nation_stu = in.readString();
    }

    public static final Creator<Motbean> CREATOR = new Creator<Motbean>() {
        @Override
        public Motbean createFromParcel(Parcel source) {
            return new Motbean(source);
        }

        @Override
        public Motbean[] newArray(int size) {
            return new Motbean[size];
        }
    };
}
