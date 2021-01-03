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
public class SearchResponse {

    private Meta meta;
    private Response response;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        @Builder.Default
        List<Hit> hits = new ArrayList<>();

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Hit {

            private String index;
            private String type;
            private Result result;

            @Data
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Result {

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
}
