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
    @SerializedName("angleAxis")
    private AngleAxis angleAxis;

    @SerializedName("radiusAxis")
    private RadiusAxis radiusAxis;

    @SerializedName("polar")
    private Polar polar;

    @SerializedName("series")
    private List<Series> series;

    @SerializedName("legend")
    private Legend legend;

    public AngleAxis getAngleAxis() {
        return angleAxis;
    }

    public void setAngleAxis(AngleAxis angleAxis) {
        this.angleAxis = angleAxis;
    }

    public RadiusAxis getRadiusAxis() {
        return radiusAxis;
    }

    public void setRadiusAxis(RadiusAxis radiusAxis) {
        this.radiusAxis = radiusAxis;
    }

    public Polar getPolar() {
        return polar;
    }

    public void setPolar(Polar polar) {
        this.polar = polar;
    }

    public Legend getLegend() {
        return legend;
    }

    public void setLegend(Legend legend) {
        this.legend = legend;
    }

    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }
}
