package com.yfy.app.chart.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/8
 */
public class RadiusAxis implements Serializable ,Type{

    @SerializedName(value = "type")
    public String a;

    @SerializedName(value = "data")
    public List<String> b;

    @SerializedName(value = "z")
    public int c;


    public RadiusAxis() {
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public List<String> getB() {
        return b;
    }

    public void setB(List<String> b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }
}
