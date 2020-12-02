package com.yfy.app.httppost.netHttp.result;


public abstract class Result {
    public int errcode;

    public abstract <T> T build();
}
