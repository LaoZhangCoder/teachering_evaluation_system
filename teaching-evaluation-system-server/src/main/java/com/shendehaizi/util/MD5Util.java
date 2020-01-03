package com.shendehaizi.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class MD5Util {
    public static String encrypt_Base64(String str) {
        String result="";
        try {
            result = Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String decrypt_Base64(String str){
        String result="";
        try {
            byte[] asBytes = Base64.getDecoder().decode(str);
             result = new String(asBytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String s = MD5Util.encrypt_Base64("6112118_admin");
        System.out.println(s);
        String s1 = MD5Util.decrypt_Base64(s);
        System.out.println(s1);
    }
}
