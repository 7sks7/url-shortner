package com.example.url_shortner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlResponseDTO {
    private String shortUrl;
}
