/**
 * Copyright (C), 2019, XXX有限公司
 * FileName: Md5Utils
 * Author:   kangtiancheng
 * Date:     2019/7/10 10:55
 * Description: MD5加密工具类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lengyan.lyblog.utils;

import java.security.MessageDigest;

/**
 * 〈MD5加密工具类〉
 *
 * @author kangtiancheng
 * @create 2019/7/10
 * @since 1.0.0
 */
public class Md5Utils {
    public static String MD5(String data){
        String sign = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] val = data.getBytes("utf-8");
            byte[] byt = md5.digest(val);
            sign = byteToHex(byt).toUpperCase();
        } catch (Exception e) {
            sign = null;
        }
        return sign;
    }

    public static String MD5(String data,boolean toUpperCase){
        String sign = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] val = data.getBytes("utf-8");
            byte[] byt = md5.digest(val);
            if(toUpperCase)
                sign = byteToHex(byt).toUpperCase();
            else
                sign = byteToHex(byt);
        } catch (Exception e) {
            sign = null;
        }
        return sign;
      }


    public static String byteToHex( byte[] b) {
        String sRet = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sRet = sRet + hex;
        }
        return sRet;
    }


    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b",
            "c", "d", "e", "f" };
}
