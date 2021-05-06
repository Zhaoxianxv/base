package com.yfy.app.duty_evaluate.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HealthBean implements Parcelable {

    /**
     * healthitem : 身高
     * healthitemid : 1
     * healthvalue :
     */

    private String healthitem;
    private String healthitemid;
    private String healthvalue;

    public String getHealthitem() {
        return healthitem;
    }

    public void setHealthitem(String healthitem) {
        this.healthitem = healthitem;
    }

    public String getHealthitemid() {
        return healthitemid;
    }

    public void setHealthitemid(String healthitemid) {
        this.healthitemid = healthitemid;
    }

    public String getHealthvalue() {
        return healthvalue;
    }

    public void setHealthvalue(String healthvalue) {
        this.healthvalue = healthvalue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.healthitem);
        dest.writeString(this.healthitemid);
        dest.writeString(this.healthvalue);
    }

    public HealthBean() {
    }

    protected HealthBean(Parcel in) {
        this.healthitem = in.readString();
        this.healthitemid = in.readString();
        this.healthvalue = in.readString();
    }

    public static final Creator<HealthBean> CREATOR = new Creator<HealthBean>() {
        @Override
        public HealthBean createFromParcel(Parcel source) {
            return new HealthBean(source);
        }

        @Override
        public HealthBean[] newArray(int size) {
            return new HealthBean[size];
        }
    };
}
