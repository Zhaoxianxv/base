package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ReportTwo implements Parcelable {

    /**
     * tableid : 203
     * tablename : 二级指标
     * tabletype : select
     */

    private String tableid;
    private String tablename;
    private String tabletype;

    private List<ReportThree> tablelist;


    public List<ReportThree> getTablelist() {
        return tablelist;
    }

    public void setTablelist(List<ReportThree> tablelist) {
        this.tablelist = tablelist;
    }

    public String getTableid() {
        return tableid;
    }

    public void setTableid(String tableid) {
        this.tableid = tableid;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tableid);
        dest.writeString(this.tablename);
        dest.writeString(this.tabletype);
        dest.writeTypedList(this.tablelist);
    }

    public ReportTwo() {
    }

    protected ReportTwo(Parcel in) {
        this.tableid = in.readString();
        this.tablename = in.readString();
        this.tabletype = in.readString();
        this.tablelist = in.createTypedArrayList(ReportThree.CREATOR);
    }

    public static final Creator<ReportTwo> CREATOR = new Creator<ReportTwo>() {
        @Override
        public ReportTwo createFromParcel(Parcel source) {
            return new ReportTwo(source);
        }

        @Override
        public ReportTwo[] newArray(int size) {
            return new ReportTwo[size];
        }
    };
}
