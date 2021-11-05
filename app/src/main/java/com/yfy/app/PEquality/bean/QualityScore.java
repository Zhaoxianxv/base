package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zxx.
 * Date: 2021/3/2
 */

public class QualityScore implements Parcelable {


    /**
     * courseid : -1
     * coursename : 选考项
     * percentage : 1.0000
     * score :
     */

    private String courseid;
    private String coursename;
    private String percentage;
    private String score;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.courseid);
        dest.writeString(this.coursename);
        dest.writeString(this.percentage);
        dest.writeString(this.score);
        dest.writeString(this.value);
    }

    public void readFromParcel(Parcel source) {
        this.courseid = source.readString();
        this.coursename = source.readString();
        this.percentage = source.readString();
        this.score = source.readString();
        this.value = source.readString();
    }

    public QualityScore() {
    }

    protected QualityScore(Parcel in) {
        this.courseid = in.readString();
        this.coursename = in.readString();
        this.percentage = in.readString();
        this.score = in.readString();
        this.value = in.readString();
    }

    public static final Creator<QualityScore> CREATOR = new Creator<QualityScore>() {
        @Override
        public QualityScore createFromParcel(Parcel source) {
            return new QualityScore(source);
        }

        @Override
        public QualityScore[] newArray(int size) {
            return new QualityScore[size];
        }
    };
}
