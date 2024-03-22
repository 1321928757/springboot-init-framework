package com.luckysj.springbootinit.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author www.luckysj.top 刘仕杰
 * @description MD5加密工具类，支持位数，加盐，无盐，大小写
 */
public class MD5Util {

    /**
     * @Title: md5Lower
     * @Description:不加盐值32位小写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5Lower(String plainText) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes("UTF-8"));
                md5 =  new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5Lower
     * @Description:加盐值32位小写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5Lower(String plainText, String saltValue) {
        String md5 = null;
        if (null != plainText && !"".equals(plainText) && null != saltValue && !"".equals(saltValue)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(plainText.getBytes("UTF-8"));
                md.update(saltValue.getBytes("UTF-8"));
                md5 = new BigInteger(1, md.digest()).toString(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return md5;
    }

    /**
     * @Title: md5_16Lower
     * @Description:不加盐值16位小写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5_16Lower(String plainText) {
        String md5 = md5Lower(plainText);
        return null==md5?md5:md5.substring(8, 24);
    }

    /**
     * @Title: md5_16Lower
     * @Description:加盐值16位小写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5_16Lower(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null==md5?md5:md5.substring(8, 24);
    }

    /**
     * @Title: md5_16Upper
     * @Description:不加盐值16位大写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5_16Upper(String plainText) {
        String md5 = md5_16Lower(plainText);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5_16Upper
     * @Description:加盐值16位大写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5_16Upper(String plainText, String saltValue) {
        String md5 = md5_16Lower(plainText, saltValue);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:不加盐值32位大写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5Upper(String plainText) {
        String md5 = md5Lower(plainText);
        return null==md5?md5:md5.toUpperCase();
    }

    /**
     * @Title: md5Upper
     * @Description:加盐值32位大写
     * @author www.luckysj.top 刘仕杰
     */
    public static String md5Upper(String plainText, String saltValue) {
        String md5 = md5Lower(plainText, saltValue);
        return null==md5?md5:md5.toUpperCase();
    }

}
