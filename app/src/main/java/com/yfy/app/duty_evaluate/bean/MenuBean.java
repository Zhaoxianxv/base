package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class MenuBean implements Parcelable {

    /**
     * ------------studies_menu-----------------
     * menuid : 44
     * menuname : 我的资料
     */

    private String menuid;
    private String menuname;
    /**
     * ------------studies_content-----------------
     */
    private String contenttype;
    private String canedit;
    private String contentname;
    private String contentid;

    private List<ContentBean> contentlist;
    private List<ContentBean> classlist;

    private int view_type;



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

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public List<ContentBean> getClasslist() {
        return classlist;
    }

    public void setClasslist(List<ContentBean> classlist) {
        this.classlist = classlist;
    }

    public List<ContentBean> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentBean> contentlist) {
        this.contentlist = contentlist;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getCanedit() {
        return canedit;
    }

    public void setCanedit(String canedit) {
        this.canedit = canedit;
    }

    public String getContentname() {
        return contentname;
    }

    public void setContentname(String contentname) {
        this.contentname = contentname;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public MenuBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.menuid);
        dest.writeString(this.menuname);
        dest.writeString(this.contenttype);
        dest.writeString(this.canedit);
        dest.writeString(this.contentname);
        dest.writeString(this.contentid);
        dest.writeTypedList(this.contentlist);
        dest.writeTypedList(this.classlist);
        dest.writeInt(this.view_type);
        dest.writeString(this.listid);
        dest.writeString(this.listadddate);
        dest.writeString(this.listtitle);
        dest.writeString(this.listcontent);
        dest.writeString(this.listimage);
    }

    protected MenuBean(Parcel in) {
        this.menuid = in.readString();
        this.menuname = in.readString();
        this.contenttype = in.readString();
        this.canedit = in.readString();
        this.contentname = in.readString();
        this.contentid = in.readString();
        this.contentlist = in.createTypedArrayList(ContentBean.CREATOR);
        this.classlist = in.createTypedArrayList(ContentBean.CREATOR);
        this.view_type = in.readInt();
        this.listid = in.readString();
        this.listadddate = in.readString();
        this.listtitle = in.readString();
        this.listcontent = in.readString();
        this.listimage = in.readString();
    }

    public static final Creator<MenuBean> CREATOR = new Creator<MenuBean>() {
        @Override
        public MenuBean createFromParcel(Parcel source) {
            return new MenuBean(source);
        }

        @Override
        public MenuBean[] newArray(int size) {
            return new MenuBean[size];
        }
    };
}
