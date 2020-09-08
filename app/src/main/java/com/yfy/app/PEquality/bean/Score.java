package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    /**
     * examname : 期末
     * examscore : 64
     */

    private String examname;
    private String examscore;

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getExamscore() {
        return examscore;
    }

    public void setExamscore(String examscore) {
        this.examscore = examscore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.examname);
        dest.writeString(this.examscore);
    }

    public Score() {
    }

    protected Score(Parcel in) {
        this.examname = in.readString();
        this.examscore = in.readString();
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel source) {
            return new Score(source);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
