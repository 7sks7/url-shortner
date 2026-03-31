package com.example.url_shortner.utility;

public class Base62Util {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String encode(long value){
        StringBuilder stringBuilder = new StringBuilder();

        while (value > 0){
            stringBuilder.append((int) (value%62));
            value /= 62;
        }
        return stringBuilder.reverse().toString();
    }
}
