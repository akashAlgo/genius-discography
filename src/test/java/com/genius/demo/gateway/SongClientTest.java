package com.genius.demo.gateway;

import com.genius.demo.exception.SongClientFailureException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.assertj.core.api.Assertions.assertThat;

@Import(GeniusSongClient.class)
class SongClientTest extends ClientTest {

    @Autowired
    private GeniusSongClient songClient;

    @Test
    void whenSearchSongs_thenUriCorrect() throws SongClientFailureException {
        wireMockServer.stubFor(get(urlPathEqualTo("/artists/1402544/songs"))
                .withHeader("Authorization", equalTo("Bearer token"))
                .willReturn(okJson("{}"))
        );

        songClient.getSongs("1402544", "Bearer token", 10, 1);

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/artists/1402544/songs"))
                .withHeader("Authorization", matching("Bearer token"))
                .withHeader("Accept", containing("application/json")));
    }

    @Test
    void whenSearchArtists_thenResponseCorrect() throws SongClientFailureException {
        wireMockServer.stubFor(get(urlPathEqualTo("/artists/1402544/songs"))
                .withHeader("Authorization", equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("songs.json")));

        var response = songClient.getSongs("1402544", "Bearer token", 10, 1);

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/artists/1402544/songs"))
                .withHeader("Authorization", matching("Bearer token")));

        assertThat(response.getMeta().getStatus()).isEqualTo(200);
        assertThat(response.getResponse().getSongs().size()).isEqualTo(10);
        assertThat(response.getResponse().getSongs().get(0).getFullTitle()).contains("Marshmello");
    }
}
