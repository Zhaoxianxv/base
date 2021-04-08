package com.yfy.app.chart.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/7
 */

public class Series implements Serializable ,Type{
    public String type;
    public List<Integer> data;
    public String coordinateSystem;
    public String name;
    public String stack;


    public Series(String type, String coordinateSystem, String name, String stack) {
        this.type = type;
        this.coordinateSystem = coordinateSystem;
        this.name = name;
        this.stack = stack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getData() {
        return data;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

    public String getCoordinateSystem() {
        return coordinateSystem;
    }

    public void setCoordinateSystem(String coordinateSystem) {
        this.coordinateSystem = coordinateSystem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }
}
