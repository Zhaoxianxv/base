package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class StuBeanReport implements Parcelable {



    private String stuid;
    private String stuname;
    private String isadd;
    private String headpic;
    private String votecount;
    private List<ReportThree> tablelist;
//---------------------------------------
    private String stuheadpic;
    private String contentcount;
    private List<ContentBean> contentlist;

    public String getStuheadpic() {
        return stuheadpic;
    }

    public void setStuheadpic(String stuheadpic) {
        this.stuheadpic = stuheadpic;
    }

    public String getContentcount() {
        return contentcount;
    }

    public void setContentcount(String contentcount) {
        this.contentcount = contentcount;
    }


    public List<ContentBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentBean> contentlist) {
        this.contentlist = contentlist;
    }

    public List<ReportThree> getTablelist() {
        return tablelist;
    }

    public void setTablelist(List<ReportThree> tablelist) {
        this.tablelist = tablelist;
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

    public String getVotecount() {
        return votecount;
    }

    public void setVotecount(String votecount) {
        this.votecount = votecount;
    }

    public String getIsadd() {
        return isadd;
    }

    public void setIsadd(String isadd) {
        this.isadd = isadd;
    }

    public StuBeanReport() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stuid);
        dest.writeString(this.stuname);
        dest.writeString(this.isadd);
        dest.writeString(this.headpic);
        dest.writeString(this.votecount);
        dest.writeTypedList(this.tablelist);
        dest.writeString(this.stuheadpic);
        dest.writeString(this.contentcount);
        dest.writeTypedList(this.contentlist);
    }

    protected StuBeanReport(Parcel in) {
        this.stuid = in.readString();
        this.stuname = in.readString();
        this.isadd = in.readString();
        this.headpic = in.readString();
        this.votecount = in.readString();
        this.tablelist = in.createTypedArrayList(ReportThree.CREATOR);
        this.stuheadpic = in.readString();
        this.contentcount = in.readString();
        this.contentlist = in.createTypedArrayList(ContentBean.CREATOR);
    }

    public static final Creator<StuBeanReport> CREATOR = new Creator<StuBeanReport>() {
        @Override
        public StuBeanReport createFromParcel(Parcel source) {
            return new StuBeanReport(source);
        }

        @Override
        public StuBeanReport[] newArray(int size) {
            return new StuBeanReport[size];
        }
    };
}
