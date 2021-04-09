package com.yfy.app.chart.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/7
 */

public class Series implements Serializable ,Type{

    @SerializedName(value = "type")
    public String a;

    @SerializedName(value = "data")
    public List<Integer> b;

    @SerializedName(value = "coordinateSystem")
    public String c;

    @SerializedName(value = "name")
    public String d;

    @SerializedName(value = "stack")
    public String e;


    public Series(String a, String c, String d, String e) {
        this.a = a;
        this.c = c;
        this.d = d;
        this.e = e;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public List<Integer> getB() {
        return b;
    }

    public void setB(List<Integer> b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
