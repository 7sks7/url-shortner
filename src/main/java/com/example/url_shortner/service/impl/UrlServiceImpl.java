package com.example.url_shortner.service.impl;

import com.example.url_shortner.dto.UrlRequestDTO;
import com.example.url_shortner.dto.UrlResponseDTO;
import com.example.url_shortner.model.Url;
import com.example.url_shortner.repository.UrlRepository;
import com.example.url_shortner.service.UrlService;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.example.url_shortner.utility.UrlEncoder.generateCode;

@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlResponseDTO shortenUrl(UrlRequestDTO urlRequest) {
        String code = generateCode();

        Url url = Url.builder().originalUrl(urlRequest.getUrl()).shortCode(code).build();
        urlRepository.save(url);

        return UrlResponseDTO.builder()
                .shortUrl("http://localhost:8080/" + code)
                .build();
    }

    @Override
    public String getOriginalUrl(String code) {
        return urlRepository.findByShortCode(code)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new RuntimeException("URL not found!"));
    }

}
