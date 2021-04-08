package com.yfy.app.chart.bean;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/4/8
 */
public class Legend implements Serializable ,Type{
    public boolean show;
    public List<String> data;


    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }


    public boolean isShow() {

        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
