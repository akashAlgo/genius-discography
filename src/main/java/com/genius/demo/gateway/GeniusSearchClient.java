package com.genius.demo.gateway;

import com.genius.demo.config.GeniusServiceConfig;
import com.genius.demo.gateway.model.SearchResponse;
import com.genius.demo.service.SearchClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class GeniusSearchClient implements SearchClient {

    private final WebClient webClient;
    private final GeniusServiceConfig serviceConfig;

    public SearchResponse getSearchResponse(String artistName, String token) {
        URI uri = UriComponentsBuilder.fromHttpUrl(serviceConfig.getUrl())
                .path("search")
                .queryParam("q", artistName)
                .build()
                .toUri();

        return webClient.get()
                .uri(uri)
                .header("Authorization", "Bearer " + token)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(SearchResponse.class)
                .timeout(serviceConfig.getTimeout())
                .block();
    }
}
