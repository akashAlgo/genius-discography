package com.genius.demo.service;

import com.genius.demo.exception.SearchFailureException;
import com.genius.demo.exception.SongClientFailureException;
import com.genius.demo.gateway.model.Meta;
import com.genius.demo.gateway.model.SearchResponse;
import com.genius.demo.gateway.model.SearchResponse.Response;
import com.genius.demo.gateway.model.SearchResponse.Response.Hit.Result.PrimaryArtist;
import com.genius.demo.gateway.model.SongResponse;
import com.genius.demo.gateway.model.SongResponse.Response.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.genius.demo.gateway.model.SearchResponse.Response.Hit.Result;
import static com.genius.demo.gateway.model.SearchResponse.Response.Hit.builder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

    @Mock
    private SearchClient searchClient;

    @Mock
    private SongClient songClient;

    @InjectMocks
    private SongService songService;

    @BeforeEach
    void setup() throws SearchFailureException, SongClientFailureException {
        when(searchClient.getSearchResponse("Singer", "Bearer token")).thenReturn(searchResponse());
        when(songClient.getSongs("1234", "Bearer token", 10, 1)).thenReturn(songResponse());
    }

    @Test
    void testGetSongsByArtist() throws SearchFailureException, SongClientFailureException {

        var response = songService.getSongsByArtist("Singer", true, "Bearer token", 10, 1);
        assertThat(response.size()).isEqualTo(1);
        assertThat(response.get(0)).isEqualTo("Sample Title");
    }


    private SearchResponse searchResponse() {

        return SearchResponse.builder()
                .meta(new Meta(200))
                .response(
                        Response.builder()
                                .hits(List.of(builder()
                                        .index("1")
                                        .result(Result.builder()
                                                .fullTitle("Sample Title by Singer")
                                                .title("Sample Title")
                                                .primaryArtist(PrimaryArtist.builder().id("1234").name("Singer").build())
                                                .build())
                                        .build()
                                )).build())
                .build();
    }

    private SongResponse songResponse() {
        return SongResponse.builder()
                .meta(new Meta(200))
                .response(
                        SongResponse.Response.builder()
                                .songs(List.of(Song.builder()
                                        .fullTitle("Sample Title by Singer")
                                        .title("Sample Title")
                                        .build())
                                ).build())
                .build();
    }

}
