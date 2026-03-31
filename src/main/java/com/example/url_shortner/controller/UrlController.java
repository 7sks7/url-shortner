package com.example.url_shortner.controller;

import com.example.url_shortner.dto.UrlRequestDTO;
import com.example.url_shortner.dto.UrlResponseDTO;
import com.example.url_shortner.service.UrlService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Slf4j
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponseDTO> shorten(@Valid @RequestBody UrlRequestDTO urlRequest){
        UrlResponseDTO urlResponseDTO = urlService.shortenUrl(urlRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(urlResponseDTO);
    }

    @GetMapping("/get-url/{code}")
    public ResponseEntity<String> redirect(@PathVariable String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        return ResponseEntity.ok(originalUrl);
    }
}
