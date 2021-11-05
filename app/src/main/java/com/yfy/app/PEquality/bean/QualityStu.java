package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QualityStu implements Parcelable {

    /**
     * stuid : 5627
     * stuname : 曾楷焮
     * headpic : /head.png
     * score :
     * value :
     */

    private String stuid;
    private String stuname;
    private String headpic;
    private String score;
    private String value;
    private String title="";
    private String rank="";
    private String percent="";
    private String score_type="";
    private List<QualityScore> scores;




    public List<QualityScore> getScores() {
        return scores;
    }

    public void setScores(List<QualityScore> scores) {
        this.scores = scores;
    }

    public String getScore_type() {
        return score_type;
    }

    public void setScore_type(String score_type) {
        this.score_type = score_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

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

    public String getHeadpic() {
        return headpic;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
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
        dest.writeString(this.stuid);
        dest.writeString(this.stuname);
        dest.writeString(this.headpic);
        dest.writeString(this.score);
        dest.writeString(this.value);
        dest.writeString(this.title);
        dest.writeString(this.rank);
        dest.writeString(this.percent);
        dest.writeString(this.score_type);
        dest.writeTypedList(this.scores);
    }

    public void readFromParcel(Parcel source) {
        this.stuid = source.readString();
        this.stuname = source.readString();
        this.headpic = source.readString();
        this.score = source.readString();
        this.value = source.readString();
        this.title = source.readString();
        this.rank = source.readString();
        this.percent = source.readString();
        this.score_type = source.readString();
        this.scores = source.createTypedArrayList(QualityScore.CREATOR);
    }

    public QualityStu() {
    }

    protected QualityStu(Parcel in) {
        this.stuid = in.readString();
        this.stuname = in.readString();
        this.headpic = in.readString();
        this.score = in.readString();
        this.value = in.readString();
        this.title = in.readString();
        this.rank = in.readString();
        this.percent = in.readString();
        this.score_type = in.readString();
        this.scores = in.createTypedArrayList(QualityScore.CREATOR);
    }

    public static final Creator<QualityStu> CREATOR = new Creator<QualityStu>() {
        @Override
        public QualityStu createFromParcel(Parcel source) {
            return new QualityStu(source);
        }

        @Override
        public QualityStu[] newArray(int size) {
            return new QualityStu[size];
        }
    };
}
