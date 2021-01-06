package com.genius.demo.gateway;

import com.genius.demo.exception.SearchFailureException;
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

@Import(GeniusSearchClient.class)
class SearchClientTest extends ClientTest {

    @Autowired
    private GeniusSearchClient searchClient;

    @Test
    void whenSearchArists_thenUriCorrect() throws SearchFailureException {
        wireMockServer.stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("q", equalTo("Marshmello"))
                .withHeader("Authorization", equalTo("Bearer token"))
                .willReturn(okJson("{}"))
        );

        searchClient.getSearchResponse("Marshmello", "Bearer token");

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/search"))
                .withQueryParam("q", equalTo("Marshmello"))
                .withHeader("Authorization", matching("Bearer token"))
                .withHeader("Accept", containing("application/json")));
    }

    @Test
    void whenSearchArtists_thenResponseCorrect() throws SearchFailureException {
        wireMockServer.stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("q", equalTo("Marshmello"))
                .withHeader("Authorization", equalTo("Bearer token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json;charset=UTF-8")
                        .withBodyFile("artist-search.json")));

        var response = searchClient.getSearchResponse("Marshmello", "Bearer token");

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/search"))
                .withQueryParam("q", equalTo("Marshmello")));

        assertThat(response.getMeta().getStatus()).isEqualTo(200);
        assertThat(response.getResponse().getHits().size()).isEqualTo(10);
        assertThat(response.getResponse().getHits().get(0).getResult().getPrimaryArtist().getName())
                .contains("Marshmello");
    }

}
