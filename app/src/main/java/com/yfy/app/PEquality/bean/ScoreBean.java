package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ScoreBean implements Parcelable {

    /**
     * coursename : 语文
     * courseid : 1
     * 半期 : 80
     * 期末 : 89.33333333333333333333333333
     */

    private String coursename;
    private String courseid;

    private List<Score> scores;


    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.coursename);
        dest.writeString(this.courseid);
        dest.writeTypedList(this.scores);
    }

    public ScoreBean() {
    }

    protected ScoreBean(Parcel in) {
        this.coursename = in.readString();
        this.courseid = in.readString();
        this.scores = in.createTypedArrayList(Score.CREATOR);
    }

    public static final Creator<ScoreBean> CREATOR = new Creator<ScoreBean>() {
        @Override
        public ScoreBean createFromParcel(Parcel source) {
            return new ScoreBean(source);
        }

        @Override
        public ScoreBean[] newArray(int size) {
            return new ScoreBean[size];
        }
    };
}
