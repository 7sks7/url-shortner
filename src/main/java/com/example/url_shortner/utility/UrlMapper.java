package com.example.url_shortner.utility;

import com.example.url_shortner.model.Url;

public class UrlMapper {
    public static Url toEntity(String originalUrl, String code) {
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortCode(code);
        return url;
    }
}
