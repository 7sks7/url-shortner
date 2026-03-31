package com.example.url_shortner.utility;

import java.util.Random;

public class UrlEncoder {

    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    public static String generateCode(){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i<CODE_LENGTH; i++){
            stringBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }

        return stringBuilder.toString();
    }
}
