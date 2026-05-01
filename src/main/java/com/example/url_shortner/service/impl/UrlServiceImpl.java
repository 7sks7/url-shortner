package com.example.url_shortner.service.impl;

import com.example.url_shortner.dto.UrlRequestDTO;
import com.example.url_shortner.dto.UrlResponseDTO;
import com.example.url_shortner.exception.UrlNotFoundException;
import com.example.url_shortner.model.Url;
import com.example.url_shortner.repository.UrlRepository;
import com.example.url_shortner.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.url_shortner.utility.UrlEncoder.generateCode;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public UrlResponseDTO shortenUrl(UrlRequestDTO urlRequest) {

        String normalizedUrl = prepareUrl(urlRequest.getUrl());
        log.info("Final URL after normalizing: {}", normalizedUrl);

        return urlRepository.findByOriginalUrl(normalizedUrl)
                .map(this::buildExistingResponse)
                .orElseGet(() -> createAndReturnNewUrl(normalizedUrl));
    }

    private String prepareUrl(String url) {
        String fullUrl = ensureHttpScheme(url);
        return normalizeUrl(fullUrl);
    }

    private UrlResponseDTO buildExistingResponse(Url existing) {
        log.info("URL already exists, reusing short code");

        String shortUrl = buildShortUrl(existing.getShortCode());

        return UrlResponseDTO.builder()
                .shortUrl(shortUrl)
                .build();
    }

    private UrlResponseDTO createAndReturnNewUrl(String normalizedUrl) {

        log.info("URL not found, generating new short code");

        String code = generateCode();

        Url url = Url.builder()
                .originalUrl(normalizedUrl)
                .shortCode(code)
                .build();

        urlRepository.save(url);

        String shortUrl = buildShortUrl(code);

        return UrlResponseDTO.builder()
                .shortUrl(shortUrl)
                .build();
    }

    private String buildShortUrl(String code) {
        return UriComponentsBuilder
                .fromUriString(baseUrl)
                .pathSegment(code)
                .toUriString();
    }

    @Override
    public String getOriginalUrl(String code) {
        return urlRepository.findByShortCode(code)
                .map(Url::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException("URL not found for code: " + code));
    }

    private String normalizeUrl(String url) {
        return url.trim().toLowerCase().replaceAll("/$", "");
    }

    private String ensureHttpScheme(String url) {
        url = url.trim();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

}
