package com.example.url_shortner.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Url {

    @Id
    private String id;
    private String originalUrl;
    private String shortCode;
}
