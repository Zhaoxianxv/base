package com.yfy.app.chart.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/8
 */
public class RadiusAxis implements Serializable ,Type{
    public String type;
    public List<String> data;
    public int z;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
