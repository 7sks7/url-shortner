package com.example.url_shortner.controller;

import com.example.url_shortner.dto.UrlRequestDTO;
import com.example.url_shortner.dto.UrlResponseDTO;
import com.example.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shorten(@Valid @RequestBody UrlRequestDTO urlRequest){
        long startTime = System.currentTimeMillis();
        UrlResponseDTO urlResponseDTO = urlService.shortenUrl(urlRequest);
        long duration = System.currentTimeMillis() - startTime;
        log.info("Time taken to post URL for code {}: {} ms", urlResponseDTO, duration);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }

    @GetMapping("/get-url/{code}")
    public ResponseEntity<UrlResponseDTO> getUrl(@PathVariable String code) {
        long startTime = System.currentTimeMillis();
        String originalUrl = urlService.getOriginalUrl(code);
        long duration = System.currentTimeMillis() - startTime;
        log.info("Time taken to fetch URL for code {}: {} ms", code, duration);

        return ResponseEntity.ok(
                UrlResponseDTO.builder()
                        .shortUrl(originalUrl)
                        .build()
        );
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        long startTime = System.currentTimeMillis();
        String originalUrl = urlService.getOriginalUrl(code);
        long duration = System.currentTimeMillis() - startTime;
        log.info("Time taken to fetch URL for code {}: {} ms", code, duration);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
