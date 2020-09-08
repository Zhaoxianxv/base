package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;


import com.yfy.final_tag.data.TagFinal;

import java.util.List;

public class ArtBean implements Parcelable {

    /**
     * artname : 课程学习
     * maxvalue : 25
     * value : 未打分
     */

    private int view_type=TagFinal.TYPE_ITEM;
    private String artname;
    private String maxvalue;
    private String value;


    private List<ScoreBean> scoreBeanList;

    public List<ScoreBean> getScoreBeanList() {
        return scoreBeanList;
    }

    public void setScoreBeanList(List<ScoreBean> scoreBeanList) {
        this.scoreBeanList = scoreBeanList;
    }

    public int getView_type() {
        return view_type;
    }

    public void setView_type(int view_type) {
        this.view_type = view_type;
    }

    public String getArtname() {
        return artname;
    }

    public void setArtname(String artname) {
        this.artname = artname;
    }

    public String getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(String maxvalue) {
        this.maxvalue = maxvalue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.artname);
        dest.writeString(this.maxvalue);
        dest.writeString(this.value);
    }

    public ArtBean() {
    }

    protected ArtBean(Parcel in) {
        this.artname = in.readString();
        this.maxvalue = in.readString();
        this.value = in.readString();
    }

    public static final Creator<ArtBean> CREATOR = new Creator<ArtBean>() {
        @Override
        public ArtBean createFromParcel(Parcel source) {
            return new ArtBean(source);
        }

        @Override
        public ArtBean[] newArray(int size) {
            return new ArtBean[size];
        }
    };
}
