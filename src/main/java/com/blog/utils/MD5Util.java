package com.blog.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] byteDigest = md.digest();
            int i;
            StringBuffer buff=new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i=byteDigest[offset];
                if(i<0){
                    i+=256;
                }
                if(i<16){
                    buff.append("0");
                }
                buff.append(Integer.toHexString(i));
            }
            return buff.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(code("123456"));
    }

}
