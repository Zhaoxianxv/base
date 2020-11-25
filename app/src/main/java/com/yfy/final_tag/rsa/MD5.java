package com.yfy.final_tag.rsa;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yfy1 on 2016/10/20.
 */
public class MD5 {
    public static String code="";
    public static String getMD5(String val) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] m = MessageDigest.getInstance("MD5").digest(val.getBytes("UTF-8")); //加密
        return getString(m);
    }
    private static String getString(byte[] b){
        StringBuffer hex  = new StringBuffer(b.length * 2);
        for (byte a : b) {
            if ((a & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(a & 0xFF));
        }
        return hex.toString();// 32位
//        return hex.toString().toString().substring(8, 24);// 16位
    }
}
