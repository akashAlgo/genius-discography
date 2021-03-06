package com.genius.demo.gateway;

import com.genius.demo.config.GeniusServiceConfig;
import com.genius.demo.exception.SongClientFailureException;
import com.genius.demo.gateway.model.SongResponse;
import com.genius.demo.service.SongClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class GeniusSongClient implements SongClient {

    private final WebClient webClient;
    private final GeniusServiceConfig serviceConfig;

    public SongResponse getSongs(String artistId, String token, Integer perPage, Integer page) throws SongClientFailureException {
        URI uri = UriComponentsBuilder.fromHttpUrl(serviceConfig.getUrl())
                .path("artists/")
                .path(artistId)
                .path("/songs")
                .queryParam("per_page", perPage)
                .queryParam("page", page)
                .build()
                .toUri();

        try {
            return webClient.get()
                    .uri(uri)
                    .header("Authorization", token)
                    .accept(APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(SongResponse.class)
                    .timeout(serviceConfig.getTimeout())
                    .block();
        } catch (WebClientResponseException e) {
            throw new SongClientFailureException("Song API failed", e);
        }
    }
}
