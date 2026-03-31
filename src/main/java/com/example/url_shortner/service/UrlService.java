package com.example.url_shortner.service;

import com.example.url_shortner.dto.UrlRequestDTO;
import com.example.url_shortner.dto.UrlResponseDTO;

public interface UrlService {
    public UrlResponseDTO shortenUrl(UrlRequestDTO urlRequest);
    public String getOriginalUrl(String code);
}
