




package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zxx.
 * Date: 2021/3/29
 */
public class QEHonorBean implements Parcelable {

    /**
     * id : 19.0
     * reson : 发
     * title : 校级
     * image : uploadfile/files/20210329165104778.jpg
     * date : 2021/03/29 00:00
     * content1 : null
     * adddate : 2021/03/29 16:51
     * score : 1.0
     * ispassed : 1
     */

    private Double id;
    private String reson;
    private String title;
    private String image;
    private String date;
    private String adddate;
    private String stuName;
    private float score;
    private Integer ispassed;


    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getReson() {
        return reson;
    }

    public void setReson(String reson) {
        this.reson = reson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAdddate() {
        return adddate;
    }

    public void setAdddate(String adddate) {
        this.adddate = adddate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getIspassed() {
        return ispassed;
    }

    public void setIspassed(Integer ispassed) {
        this.ispassed = ispassed;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.reson);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.date);
        dest.writeString(this.adddate);
        dest.writeFloat(this.score);
        dest.writeValue(this.ispassed);
    }

    public void readFromParcel(Parcel source) {
        this.id = (Double) source.readValue(Double.class.getClassLoader());
        this.reson = source.readString();
        this.title = source.readString();
        this.image = source.readString();
        this.date = source.readString();
        this.adddate = source.readString();
        this.score = source.readFloat();
        this.ispassed = (Integer) source.readValue(Integer.class.getClassLoader());
    }

    public QEHonorBean() {
    }

    protected QEHonorBean(Parcel in) {
        this.id = (Double) in.readValue(Double.class.getClassLoader());
        this.reson = in.readString();
        this.title = in.readString();
        this.image = in.readString();
        this.date = in.readString();
        this.adddate = in.readString();
        this.score = in.readFloat();
        this.ispassed = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<QEHonorBean> CREATOR = new Creator<QEHonorBean>() {
        @Override
        public QEHonorBean createFromParcel(Parcel source) {
            return new QEHonorBean(source);
        }

        @Override
        public QEHonorBean[] newArray(int size) {
            return new QEHonorBean[size];
        }
    };
}
