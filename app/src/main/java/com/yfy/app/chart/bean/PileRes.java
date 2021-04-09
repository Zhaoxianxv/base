package com.yfy.app.chart.bean;

import com.google.gson.annotations.SerializedName;

import org.simpleframework.xml.Order;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by zxx.
 * Date: 2021/4/7
 */


public class PileRes implements Serializable ,Type{

    @SerializedName(value = "angleAxis")
    private AngleAxis a;

    @SerializedName("radiusAxis")
    private RadiusAxis b;

    @SerializedName("polar")
    private Polar c;

    @SerializedName("series")
    private List<Series> d;

    @SerializedName("legend")
    private Legend e;


    public PileRes() {
    }


    public AngleAxis getA() {
        return a;
    }

    public void setA(AngleAxis a) {
        this.a = a;
    }

    public RadiusAxis getB() {
        return b;
    }

    public void setB(RadiusAxis b) {
        this.b = b;
    }

    public Polar getC() {
        return c;
    }

    public void setC(Polar c) {
        this.c = c;
    }

    public List<Series> getD() {
        return d;
    }

    public void setD(List<Series> d) {
        this.d = d;
    }

    public Legend getE() {
        return e;
    }

    public void setE(Legend e) {
        this.e = e;
    }
}
