package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ReportThree implements Parcelable {

    /**
     * tableid : 204
     * tablename : 三级指标
     * tabletype : select
     * teacherselect : false
     * stuselect : false
     */
    private int view_type;
    private String tableid;
    private String tablename;
    private String tablename_parent;
    private String tabletype;
    private String teacherselect;
    private String stuselect;
    private String isselect;
    private String stucount;
    private String stuselectcount;



    //                                    "listid": "447460",
    //                                    "listadddate": "2020/06/23",
    //                                    "listtitle": "管理1的话",
    //                                    "listcontent": "你很好，是个很勇敢的孩子！",
    //                                    "listimage": ""
    private String listid;
    private String listadddate;
    private String listtitle;
    private String listcontent;
    private String listimage;


    public String getListid() {
        return listid;
    }

    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getListadddate() {
        return listadddate;
    }

    public void setListadddate(String listadddate) {
        this.listadddate = listadddate;
    }

    public String getListtitle() {
        return listtitle;
    }

    public void setListtitle(String listtitle) {
        this.listtitle = listtitle;
    }

    public String getListcontent() {
        return listcontent;
    }

    public void setListcontent(String listcontent) {
        this.listcontent = listcontent;
    }

    public String getListimage() {
        return listimage;
    }

    public void setListimage(String listimage) {
        this.listimage = listimage;
    }

    public String getStucount() {
        return stucount;
    }

    public void setStucount(String stucount) {
        this.stucount = stucount;
    }

    public String getStuselectcount() {
        return stuselectcount;
    }

    public void setStuselectcount(String stuselectcount) {
        this.stuselectcount = stuselectcount;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }


    public String getIsselect() {
        return isselect;
    }

    public void setIsselect(String isselect) {
        this.isselect = isselect;
    }

    public String getTablename_parent() {
        return tablename_parent;
    }

    public void setTablename_parent(String tablename_parent) {
        this.tablename_parent = tablename_parent;
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

    public String getTeacherselect() {
        return teacherselect;
    }

    public void setTeacherselect(String teacherselect) {
        this.teacherselect = teacherselect;
    }

    public String getStuselect() {
        return stuselect;
    }

    public void setStuselect(String stuselect) {
        this.stuselect = stuselect;
    }

    public ReportThree() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.view_type);
        dest.writeString(this.tableid);
        dest.writeString(this.tablename);
        dest.writeString(this.tablename_parent);
        dest.writeString(this.tabletype);
        dest.writeString(this.teacherselect);
        dest.writeString(this.stuselect);
        dest.writeString(this.isselect);
        dest.writeString(this.stucount);
        dest.writeString(this.stuselectcount);
        dest.writeString(this.listid);
        dest.writeString(this.listadddate);
        dest.writeString(this.listtitle);
        dest.writeString(this.listcontent);
        dest.writeString(this.listimage);
    }

    protected ReportThree(Parcel in) {
        this.view_type = in.readInt();
        this.tableid = in.readString();
        this.tablename = in.readString();
        this.tablename_parent = in.readString();
        this.tabletype = in.readString();
        this.teacherselect = in.readString();
        this.stuselect = in.readString();
        this.isselect = in.readString();
        this.stucount = in.readString();
        this.stuselectcount = in.readString();
        this.listid = in.readString();
        this.listadddate = in.readString();
        this.listtitle = in.readString();
        this.listcontent = in.readString();
        this.listimage = in.readString();
    }

    public static final Creator<ReportThree> CREATOR = new Creator<ReportThree>() {
        @Override
        public ReportThree createFromParcel(Parcel source) {
            return new ReportThree(source);
        }

        @Override
        public ReportThree[] newArray(int size) {
            return new ReportThree[size];
        }
    };
}
