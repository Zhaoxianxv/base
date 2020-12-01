package com.yfy.app.netHttp.result;



/**
 */


public class BaseResResult extends Result {
    public String result;
    @Override
    public BaseRes build() {
        BaseRes res = new BaseRes();
        res.setResult(result);

        return res;
    }
}
