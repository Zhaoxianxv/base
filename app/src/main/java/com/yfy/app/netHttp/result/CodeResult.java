package com.yfy.app.netHttp.result;



/**
 * User: Evan(yangyouwei@qq.com)
 * Date: 2016-05-09
 * Time: 10:47
 * FIXME
 */


public class CodeResult extends Result {
    public String code;
    @Override
    public Code build() {
        Code codes = new Code();
        codes.setErrcode(errcode);
        codes.setCodes(code);
        return codes;
    }
}
