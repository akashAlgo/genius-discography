package com.genius.demo.gateway;

import com.genius.demo.config.GeniusServiceConfig;
import com.genius.demo.config.JacksonConfig;
import com.genius.demo.config.WebClientConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;

import java.time.Duration;

import static org.mockito.Mockito.when;

@RestClientTest
@AutoConfigureJson
@AutoConfigureWireMock
@Import({WebClientConfig.class, JacksonConfig.class})
public abstract class ClientTest {

    @MockBean
    GeniusServiceConfig serviceConfig;

    @Autowired
    protected WireMockServer wireMockServer;

    @BeforeEach
    void setup() {
        when(serviceConfig.getUrl()).thenReturn("http://localhost:" + wireMockServer.port());
        when(serviceConfig.getTimeout()).thenReturn(Duration.ofSeconds(5));
    }

}
