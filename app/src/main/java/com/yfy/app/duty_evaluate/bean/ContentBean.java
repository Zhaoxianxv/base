package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ContentBean implements Parcelable {

    /**
     * listid : 447456
     * listadddate : 2020/06/17
     * listtitle : 我的自画像
     * listcontent :
     * listimage : http://gxxc.yfyit.com//uploadfile/files/20200617152154781.jpg
     */

    private String listid;
    private String listadddate;
    private String listtitle;
    private String listcontent;
    private String listimage;



// "classid": "191",
//                    "clasname": "一年级1班",
//                    "stucount": "44",

    private String classid;
    private String clasname;
    private String stucount;
    private String canedit;
    private List<StuBeanReport> stulist;


    public String getCanedit() {
        return canedit;
    }

    public void setCanedit(String canedit) {
        this.canedit = canedit;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getClasname() {
        return clasname;
    }

    public void setClasname(String clasname) {
        this.clasname = clasname;
    }

    public String getStucount() {
        return stucount;
    }

    public void setStucount(String stucount) {
        this.stucount = stucount;
    }

    public List<StuBeanReport> getStulist() {
        return stulist;
    }

    public void setStulist(List<StuBeanReport> stulist) {
        this.stulist = stulist;
    }

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

    public ContentBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.listid);
        dest.writeString(this.listadddate);
        dest.writeString(this.listtitle);
        dest.writeString(this.listcontent);
        dest.writeString(this.listimage);
        dest.writeString(this.classid);
        dest.writeString(this.clasname);
        dest.writeString(this.stucount);
        dest.writeString(this.canedit);
        dest.writeTypedList(this.stulist);
    }

    protected ContentBean(Parcel in) {
        this.listid = in.readString();
        this.listadddate = in.readString();
        this.listtitle = in.readString();
        this.listcontent = in.readString();
        this.listimage = in.readString();
        this.classid = in.readString();
        this.clasname = in.readString();
        this.stucount = in.readString();
        this.canedit = in.readString();
        this.stulist = in.createTypedArrayList(StuBeanReport.CREATOR);
    }

    public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
        @Override
        public ContentBean createFromParcel(Parcel source) {
            return new ContentBean(source);
        }

        @Override
        public ContentBean[] newArray(int size) {
            return new ContentBean[size];
        }
    };
}
