package com.houhong.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @program: algorithm-work
 * @description:
 * @author: houhong
 * @create: 2022-09-01 13:24
 **/

public class AesSecurityUtil {

    /**
     * 加密使用的向量，必须要16位的长度
     */
    private static final String iv = "8872887399996789";

    /**
     * 加密用的key，可以由26个字母和数字组成，使用AES-128-CBC加密模式,key需要为16位
     */
    private static final String key;
    static {
       key = "1234567812345678";
    }


    /**
     *
     * @author aran
     * @Description AES算法加密明文
     * @param data 明文
     * @return 密文
     */
    public static  String encryptByAES(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;

            if(plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(),"AES");
            // CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec,ivspec);

            byte[] encrypted = cipher.doFinal(plaintext);

            return AesSecurityUtil.encode(encrypted);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author aran
     * @Description AES算法解密密文
     * @param data data
     * @return 明文
     */
    public static String decryptAES(String data) {
        try {
            //先用base64解密
            byte[] encrypted = AesSecurityUtil.decode(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(),"AES");

            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE,keyspec,ivspec);

            byte[] original = cipher.doFinal(encrypted);
            String originalString = new String(original);
            return originalString.trim();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 编码
     * @param byteArray 数组
     * @return 字符串
     */
    public static String encode(byte[] byteArray) {
        return new String(new Base64().encode(byteArray));
    }

    public static byte[] decode(String base64EncodedStr) {
        return new Base64().decode(base64EncodedStr);
    }


    public static void main(String[] args) {
        String data = "测试这一段abc9992";
        System.out.println("加密之前的明文：" + data);
        String encrypData = AesSecurityUtil.encryptByAES(data);
        System.out.println("加密之后的密文为:" + encrypData);
        String decryptData = AesSecurityUtil.decryptAES(encrypData);
        System.out.println("解密后的明文为：" + decryptData);
    }
}