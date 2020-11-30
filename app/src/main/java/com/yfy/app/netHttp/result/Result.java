package com.yfy.app.netHttp.result;


public abstract class Result {
    public int errcode;

    public abstract <T> T build();
}
