package com.yfy.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yfy.app.chart.bean.PileRes;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by zxx.
 * Date: 2021/4/7
 */
public class JsonTool {


    /**
     * ------obj -> json string
     */
    public static String objectToJson(PileRes bean, Gson gson) {
        return gson.toJson(bean, PileRes.class);
    }

    /**
     * 对象list转化为json string
     */
    public static String objectListToJson(ArrayList<JsonObj> list,Gson gson) {
        //要转化的类型
        //Type导入的是java.lang.reflect.Type的包
        //TypeToken导入的是 com.google.gson.reflect.TypeToken的包
        Type type = new TypeToken<ArrayList<JsonObj>>() {}.getType();
        return gson.toJson(list, type);
    }
}
