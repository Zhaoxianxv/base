package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class StuReportBase implements Parcelable {

    /**
     * stuid : 12590
     * stuname : 测试
     * stuheadpic : http://gxxc.yfyit.com/uploadfile/photo/20200106115616.jpg
     * stusex : 男
     * stuBirthday : 2013/01/01
     * classname : 一年级1班
     * fathername : 11
     * fatherwork : 11
     * fathertel : 11
     * mothername :
     * motherwork :
     * mothertel :
     * homeaddr : 11
     * Deliverymethod : 家长自行接送
     * Nation : 汉族
     */

    private String stuid;
    private String stuname;
    private String stuheadpic;
    private String stusex;
    private String stuBirthday;
    private String classname;
    private String fathername;
    private String fatherwork;
    private String fathertel;
    private String mothername;
    private String motherwork;
    private String mothertel;
    private String homeaddr;
    private String Deliverymethod;
    private String Nation;

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

    public String getStuheadpic() {
        return stuheadpic;
    }

    public void setStuheadpic(String stuheadpic) {
        this.stuheadpic = stuheadpic;
    }

    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    public String getStuBirthday() {
        return stuBirthday;
    }

    public void setStuBirthday(String stuBirthday) {
        this.stuBirthday = stuBirthday;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getFatherwork() {
        return fatherwork;
    }

    public void setFatherwork(String fatherwork) {
        this.fatherwork = fatherwork;
    }

    public String getFathertel() {
        return fathertel;
    }

    public void setFathertel(String fathertel) {
        this.fathertel = fathertel;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getMotherwork() {
        return motherwork;
    }

    public void setMotherwork(String motherwork) {
        this.motherwork = motherwork;
    }

    public String getMothertel() {
        return mothertel;
    }

    public void setMothertel(String mothertel) {
        this.mothertel = mothertel;
    }

    public String getHomeaddr() {
        return homeaddr;
    }

    public void setHomeaddr(String homeaddr) {
        this.homeaddr = homeaddr;
    }

    public String getDeliverymethod() {
        return Deliverymethod;
    }

    public void setDeliverymethod(String Deliverymethod) {
        this.Deliverymethod = Deliverymethod;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String Nation) {
        this.Nation = Nation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.stuid);
        dest.writeString(this.stuname);
        dest.writeString(this.stuheadpic);
        dest.writeString(this.stusex);
        dest.writeString(this.stuBirthday);
        dest.writeString(this.classname);
        dest.writeString(this.fathername);
        dest.writeString(this.fatherwork);
        dest.writeString(this.fathertel);
        dest.writeString(this.mothername);
        dest.writeString(this.motherwork);
        dest.writeString(this.mothertel);
        dest.writeString(this.homeaddr);
        dest.writeString(this.Deliverymethod);
        dest.writeString(this.Nation);
    }

    public StuReportBase() {
    }

    protected StuReportBase(Parcel in) {
        this.stuid = in.readString();
        this.stuname = in.readString();
        this.stuheadpic = in.readString();
        this.stusex = in.readString();
        this.stuBirthday = in.readString();
        this.classname = in.readString();
        this.fathername = in.readString();
        this.fatherwork = in.readString();
        this.fathertel = in.readString();
        this.mothername = in.readString();
        this.motherwork = in.readString();
        this.mothertel = in.readString();
        this.homeaddr = in.readString();
        this.Deliverymethod = in.readString();
        this.Nation = in.readString();
    }

    public static final Creator<StuReportBase> CREATOR = new Creator<StuReportBase>() {
        @Override
        public StuReportBase createFromParcel(Parcel source) {
            return new StuReportBase(source);
        }

        @Override
        public StuReportBase[] newArray(int size) {
            return new StuReportBase[size];
        }
    };
}
