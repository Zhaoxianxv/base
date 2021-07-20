package com.yfy.app.PEquality.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yfy on 2017/6/30.
 */

public class ChartBeans implements Parcelable{


    /**
     * evaluat_id : 1
     * evaluat_name : 无
     * evaluat_score : 0
     */
    private String evaluat_id;
    private String evaluat_name;
    private String evaluat_score;
    private String name;
    private String id;


    public String getEvaluat_id() {
        return evaluat_id;
    }

    public void setEvaluat_id(String evaluat_id) {
        this.evaluat_id = evaluat_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluat_score() {
        return evaluat_score;
    }

    public void setEvaluat_score(String evaluat_score) {
        this.evaluat_score = evaluat_score;
    }

    public String getEvaluat_name() {
        return evaluat_name;
    }

    public void setEvaluat_name(String evaluat_name) {
        this.evaluat_name = evaluat_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.evaluat_id);
        dest.writeString(this.evaluat_name);
        dest.writeString(this.evaluat_score);
        dest.writeString(this.name);
        dest.writeString(this.id);
    }

    public ChartBeans() {
    }

    protected ChartBeans(Parcel in) {
        this.evaluat_id = in.readString();
        this.evaluat_name = in.readString();
        this.evaluat_score = in.readString();
        this.name = in.readString();
        this.id = in.readString();
    }

    public static final Creator<ChartBeans> CREATOR = new Creator<ChartBeans>() {
        @Override
        public ChartBeans createFromParcel(Parcel source) {
            return new ChartBeans(source);
        }

        @Override
        public ChartBeans[] newArray(int size) {
            return new ChartBeans[size];
        }
    };
}
