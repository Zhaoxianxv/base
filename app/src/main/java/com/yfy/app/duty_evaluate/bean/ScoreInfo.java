package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ScoreInfo implements Parcelable {


    /**
     * courseid : 66
     * cousename : 数学
     */

    private String courseid;
    private String cousename;
    private List<ScoreBean> scores;

    public List<ScoreBean> getScores() {
        return scores;
    }

    public void setScores(List<ScoreBean> scores) {
        this.scores = scores;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCousename() {
        return cousename;
    }

    public void setCousename(String cousename) {
        this.cousename = cousename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseid);
        dest.writeString(this.cousename);
        dest.writeTypedList(this.scores);
    }

    public ScoreInfo() {
    }

    protected ScoreInfo(Parcel in) {
        this.courseid = in.readString();
        this.cousename = in.readString();
        this.scores = in.createTypedArrayList(ScoreBean.CREATOR);
    }

    public static final Creator<ScoreInfo> CREATOR = new Creator<ScoreInfo>() {
        @Override
        public ScoreInfo createFromParcel(Parcel source) {
            return new ScoreInfo(source);
        }

        @Override
        public ScoreInfo[] newArray(int size) {
            return new ScoreInfo[size];
        }
    };
}
