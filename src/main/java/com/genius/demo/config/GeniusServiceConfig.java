package com.genius.demo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "services.genius")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeniusServiceConfig {

    private String url;
    private Duration timeout;
}
