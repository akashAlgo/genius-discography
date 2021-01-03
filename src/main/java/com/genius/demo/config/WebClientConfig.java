package com.genius.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import static org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY;

@Component
@RequiredArgsConstructor
public class WebClientConfig {

    private final ObjectMapper mapper;

    @Bean
    WebClient webClient() {
        var uriBuilderFactory = new DefaultUriBuilderFactory();
        uriBuilderFactory.setEncodingMode(VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(uriBuilderFactory)
                .exchangeStrategies(globalMapper())
                .build();
    }

    private ExchangeStrategies globalMapper() {
        return ExchangeStrategies.builder()
                .codecs(configurer -> {
                    var defaultCodecs = configurer.defaultCodecs();
                    defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(mapper));
                    defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(mapper));
                })
                .build();
    }
}
