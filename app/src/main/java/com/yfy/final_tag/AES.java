package com.yfy.final_tag;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static byte[]  mSecretKey; // 密钥
    private static String ALGORITHM = "AES"; //加解密算法
    private static String CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding"; // 加密算法/工作模式/填充方式
//    private static byte[]  iv = new byte[16]; // 用于初始化向量，必须是16位
    private static byte[]  iv = new byte[16]; // 用于初始化向量，必须是16位


    /**
     * 加密
     * @param plaintext 明文
     */
    public static String encrypt(String plaintext) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Key key = getSecretKey();
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE/*加密*/, key, new IvParameterSpec(iv)); // CBC模式需要初始化向量
        byte[] cipherTextInByte = cipher.doFinal(plaintext.getBytes());//将明文String转换为byte[]后加密， 得到byte[]

        return Base64.encodeToString(cipherTextInByte, Base64.DEFAULT); //对加密后得到的byte[]进行编码并返回String
    }

    /**
     * 解密
     * @param cipherText 密文
     */
    public  static String decrypT(String cipherText) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        Key key = getSecretKey();
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE/*解密*/, key, new IvParameterSpec(iv));// CBC模式需要初始化向量
        byte[] decryptTextInByte = cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));//对密文String解码为byte[]后解密，得到byte[]
        return new String(decryptTextInByte);//将解密后得到的byte[]转换为String
    }

    /**
     * 获取密钥
     */
    private static Key getSecretKey() throws NoSuchAlgorithmException {
        if(mSecretKey == null) {
            createSecretKey();
        }
        return new SecretKeySpec(mSecretKey, ALGORITHM);
    }

    /**
     * 生成密钥
     */
    private static void  createSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256);
        SecretKey secretKey  = keyGenerator.generateKey();
        mSecretKey = secretKey == null? null:secretKey.getEncoded();
    }






    public static String decrypTToString(String content){
        try {
//            String pubKey="sdf@#35456-0DFSD@#!";
//			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
////			// 通过种子初始化
//			byte[] values = new byte[256];
//			SecureRandom random = new SecureRandom();
//			random.nextBytes(values);
////
//			SecureRandom secureRandom = new SecureRandom();
//			random.setSeed(pubKey.getBytes("UTF-8"));
//			keyGenerator.init(256, secureRandom);
//			Logger.e(random.getAlgorithm());
//			keyGenerator.init(random);
////			keyGenerator.init();
//			Logger.e(keyGenerator.getAlgorithm());
            // 秘钥
//			byte[] enCodeFormat = keyGenerator.generateKey().getEncoded();
//			byte[] enCodeFormat = Base64.decode(pubKey.getBytes("UTF-8"), Base64.URL_SAFE);
//			byte[] enCodeFormat =pubKey.getBytes("UTF-8");

            byte[] enCodeFormat=new byte[]{(byte)146,
                    (byte) 211,(byte)133,54,
                    (byte)161,(byte)219,(byte)212,
                    (byte)227,53,51,72,5,(byte)225,72,3,
                    (byte)204,(byte)175,(byte)143,(byte)244,(byte)194,(byte)177,
                    (byte)179,0,(byte)216,65,83,(byte)184,54,(byte)162,(byte)148,15,
                    (byte)134};

//			byte[] md5key = MessageDigest.getInstance("MD5").digest(pubKey.getBytes("UTF-8"));
            byte[] md5key=new byte[]{110, (byte) 237, (byte) 235, (byte) 227, (byte) 242,79,88,53, (byte) 225,54,26,127, (byte) 135,90, (byte) 159,8};
            // 创建AES秘钥
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(md5key);
            /*使用Cipher加密*/
            /*定义加密方式*/
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            /*使用公钥和加密模式初始化*/
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec,ivParameterSpec);
            /*获取加密内容以UTF-8为标准转化的字节进行加密后再使用base64编码成字符串*/
//            Base64 可以将二进制转码成可见字符方便进行http传输，但是base64转码时会生成“+”，“/”，“=”这些被URL进行转码的特殊字符，导致两方面数据不一致。
            /*加密后的字符串*/
            return Base64.encodeToString(cipher.doFinal(content.getBytes("UTF-8")),Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}