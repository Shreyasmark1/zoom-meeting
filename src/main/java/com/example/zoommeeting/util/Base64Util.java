package com.example.zoommeeting.util;

import java.util.Base64;

public class Base64Util {

    public static String encode(String plainText){
        byte[] plainBytes = plainText.getBytes();
        byte[] base64Bytes = Base64.getEncoder().encode(plainBytes);
        return new String(base64Bytes);
    }

    public static String basicAuthEncoder(String username, String password){
        return encode(username + ":" + password);
    }
}
