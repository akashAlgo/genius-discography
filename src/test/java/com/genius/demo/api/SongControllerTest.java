package com.genius.demo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.genius.demo.config.JacksonConfig;
import com.genius.demo.service.SongService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(JacksonConfig.class)
@WebMvcTest(SongController.class)
@ActiveProfiles({"test"})
class SongControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private SongService songService;

    @Test
    void testGetSongsByArtist() throws Exception {

        var request = MockMvcRequestBuilders.get("/songs")
                .queryParam("artist_name", "Marshmello")
                .queryParam("exact_match", "true")
                .header("Authorization", "token");

        mvc.perform(request)
                .andExpect(status().isOk());
    }
}
