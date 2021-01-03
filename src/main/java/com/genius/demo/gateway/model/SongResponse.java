package com.genius.demo.gateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongResponse {

    private Meta meta;
    private Response response;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        @Builder.Default
        List<Song> songs = new ArrayList<>();

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Song {

            private String fullTitle;
            private String title;
            private PrimaryArtist primaryArtist;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class PrimaryArtist {

                private String id;
                private String name;
            }
        }
    }
}
