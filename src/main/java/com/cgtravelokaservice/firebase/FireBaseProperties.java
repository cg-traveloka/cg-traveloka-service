package com.cgtravelokaservice.firebase;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FireBaseProperties {
    public String bucketName;
    public String imageUrl;
}
