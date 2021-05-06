package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreBean implements Parcelable {

    /**
     * examid : 321
     * examname : 语文
     * scoreid : 722459
     * score : 93
     */

    private String examid;
    private String examname;
    private String scoreid;
    private String score;

    public String getExamid() {
        return examid;
    }

    public void setExamid(String examid) {
        this.examid = examid;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getScoreid() {
        return scoreid;
    }

    public void setScoreid(String scoreid) {
        this.scoreid = scoreid;
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
        dest.writeString(this.examid);
        dest.writeString(this.examname);
        dest.writeString(this.scoreid);
        dest.writeString(this.score);
    }

    public ScoreBean() {
    }

    protected ScoreBean(Parcel in) {
        this.examid = in.readString();
        this.examname = in.readString();
        this.scoreid = in.readString();
        this.score = in.readString();
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
