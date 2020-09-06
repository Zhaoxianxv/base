package com.yfy.final_tag.rsa;

import android.util.Base64;
import android.util.Log;

import com.yfy.final_tag.StringJudge;
import com.yfy.final_tag.StringUtils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSATools {
    /**RSA算法*/

    //参考网站：https://www.jianshu.com/p/7841eae98d16
    public static final String RSA = "RSA";
    /**加密方式，android的*/
//  public static final String TRANSFORMATION = "RSA/None/NoPadding";
    /**加密方式，标准jdk的*/
    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    public static byte[] StringToArraysbyte(String content ){

        return content.getBytes();
    }
    public static String byteArrayToString(byte[] encrypt ){
        StringBuilder sb = new StringBuilder();
        for (byte name : encrypt) {
            sb.append(name);
        }
        String result = sb.toString();
        if (StringJudge.isEmpty(result))result="";
        return result;
    }
    private void test(){
        String data = "hello world";
        try {
            int keyLength = 1024;
            //生成密钥对
            KeyPair keyPair = RSATools.generateRSAKeyPair(keyLength);
            //获取公钥
            byte[] publicKey = RSATools.getPublicKey(keyPair);
            Log.e("zxx", "获取公钥：" + StringUtils.stringToByteArrayGetString(publicKey));
            //获取私钥
            byte[] privateKey = RSATools.getPrivateKey(keyPair);
            Log.e("zxx", "获取私钥：" + StringUtils.stringToByteArrayGetString(privateKey));

            //用公钥加密
            byte[] encrypt = RSATools.encryptByPublicKey(data.getBytes(), publicKey);
            Log.e("zxx", "加密后的数据：" + StringUtils.stringToByteArrayGetString(encrypt));

            //用私钥解密
            byte[] decrypt = RSATools.decryptByPrivateKey(encrypt, privateKey);
            Log.e("zxx", "解密后的数据：" + new String(decrypt, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /** 生成密钥对，即公钥和私钥。key长度是512-2048，一般为1024 */
    public static KeyPair generateRSAKeyPair(int keyLength) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);
        kpg.initialize(keyLength);
        return kpg.genKeyPair();
    }

    /** 获取公钥，打印为48-12613448136942-12272-122-913111503-126115048-12...等等一长串用-拼接的数字 */
    public static byte[] getPublicKey(KeyPair keyPair) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return rsaPublicKey.getEncoded();
    }
    //获取固定公钥
//    public static byte[] getPublicKey(String s) {
//
//        return StringToArraysbyte(s);
//    }
    public static byte[] getPublicKey(String s,int keyLength) throws NoSuchAlgorithmException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(s);
        kpg.initialize(keyLength);
        RSAPublicKey rsaPublicKey = (RSAPublicKey) kpg.genKeyPair().getPublic();
        return rsaPublicKey.getEncoded();

    }

    /** 获取私钥，同上 */
    public static byte[] getPrivateKey(KeyPair keyPair) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return rsaPrivateKey.getEncoded();
    }
    public static byte[] getPrivateKey(String s) {

        return StringToArraysbyte(s);
    }







    //根据公钥加密
    public static String getEncrypt(String pubKey,String msg)throws Exception {
        //android端使用Base64.decode(src,Base64.URL_SAFE)不会出现问题
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decode(pubKey,Base64.URL_SAFE));
        //获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey publicKey =  keyFactory.generatePublic(x509KeySpec);
        /*使用Cipher加密*/
        /*定义加密方式*/
        Cipher cipher = Cipher.getInstance(RSA);
        /*使用公钥和加密模式初始化*/
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        /*获取加密内容以UTF-8为标准转化的字节进行加密后再使用base64编码成字符串*/
        /*加密后的字符串*/
        String enmsg=Base64.encodeToString(cipher.doFinal(msg.getBytes("UTF-8")),Base64.URL_SAFE);
        return enmsg;
    }










    /** 使用公钥加密 */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(TRANSFORMATION);
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

    /** 使用私钥解密 */
    public static byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
        // 得到私钥对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);
        // 解密数据
        Cipher cp = Cipher.getInstance(TRANSFORMATION);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(encrypted);
        return arr;
    }
}
