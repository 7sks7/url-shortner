package com.example.url_shortner.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlRequestDTO {
    @NotBlank(message = "URL cannot be empty")
    private String url;
}
