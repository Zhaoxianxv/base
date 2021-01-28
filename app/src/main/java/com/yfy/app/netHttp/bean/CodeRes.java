package com.yfy.app.netHttp.bean;


/**

 */


public class CodeRes extends Result {


    @Override
    public Code build() {
        Code codes = new Code();
        codes.setError_code(error_code);
        codes.setResult(result);
        return codes;
    }
}
