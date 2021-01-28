package com.yfy.app.netHttp.bean;

/**
 * Created by Aj
 */
public abstract class Result {
    public String result;
    public String error_code;
    public abstract <T> T build();
}
