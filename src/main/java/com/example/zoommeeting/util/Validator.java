package com.example.zoommeeting.util;

import com.example.zoommeeting.security.exception.BaseApiException;

public class Validator {

    public static String ONLY_ALPHABETS = "^[a-zA-Z ]*$";
    public static String EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
    public static String MOBILE = "^[0-9]\\d{9}$";

    public static String name(String name){
        name = StringUtil.trim(name);
        if(name.isBlank()) throw new BaseApiException("Name cannot be empty");
        if(StringUtil.length(name) > 50) throw new BaseApiException("Name cannot be more than 50 characters");
        if(!StringUtil.isRegexMatch(ONLY_ALPHABETS, name)) throw new BaseApiException("Name can only include alphabets");
        return name;
    }

    public static String email(String email){
        email = StringUtil.trim(email);
        if(email.isBlank()) throw new BaseApiException("Email cannot be empty");
        if(!StringUtil.isRegexMatch(EMAIL, email)) throw new BaseApiException("Invalid email");
        return email.toLowerCase();
    }
    
    public static String mobile(String mobile){
        mobile = StringUtil.trim(mobile);
        if(mobile.isBlank()) throw new BaseApiException("Mobile cannot be empty");
        if (!StringUtil.isRegexMatch(MOBILE, mobile)) throw new BaseApiException("Invalid mobile number");
        return mobile;
    }
}
