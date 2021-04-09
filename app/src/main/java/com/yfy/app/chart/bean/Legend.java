package com.yfy.app.chart.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/8
 */
public class Legend implements Serializable ,Type{

    @SerializedName(value = "show")
    public boolean a;
    @SerializedName(value = "data")
    public List<String> d;


    public Legend() {
    }


    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public List<String> getD() {
        return d;
    }

    public void setD(List<String> d) {
        this.d = d;
    }
}
