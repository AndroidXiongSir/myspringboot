package com.xiong.exam.utils;


import com.xiong.exam.common.StaticConstant;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

public class SignUtils {


//    C0B6716C3E46CA62CD2EA5B51B67A440

    public static void main(String[] args) {
        HashMap<String, Object> signMap = new HashMap<String, Object>();
        signMap.put("uname", "xiong");

        System.out.printf(System.currentTimeMillis()+"");
        String secret = StaticConstant.ACCESS_SECRET;
        String SignHashMap = SignUtils.createSign(signMap, secret);
        System.out.println("SignHashMap:" + SignHashMap);

    }

    public static String createSign(Map<String,Object> params,String accessSecret){
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuilder temp = new StringBuilder();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = String.valueOf(value);
            }
            temp.append(valueString);
        }
        System.out.printf(temp.toString());
        temp.append("&").append(StaticConstant.ACCESS_SECRET).append("=").append(accessSecret);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = getMD5Digest(temp.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sign = byte2hex(md5Digest);
        return sign;
    }


    public static boolean verificationSign(HttpServletRequest request, String accessSecret) throws UnsupportedEncodingException {
        Enumeration<?> pNames = request.getParameterNames();
        Map<String, Object> params = new HashMap<String, Object>();
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            if (StaticConstant.SIGN_KEY.equals(pName)) continue;
            Object pValue = request.getParameter(pName);
            params.put(pName, pValue);
        }
        String originSign = request.getParameter(StaticConstant.SIGN_KEY);
        String sign = createSign(params, accessSecret);
        return sign.equals(originSign);
    }



    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static byte[] getSHA1Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }

    //DF1E0B2CD4B310EC5CB10E10BD03B685
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

}
