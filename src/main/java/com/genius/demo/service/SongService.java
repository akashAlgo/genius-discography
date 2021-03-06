package com.genius.demo.service;

import com.genius.demo.exception.SearchFailureException;
import com.genius.demo.exception.SongClientFailureException;
import com.genius.demo.gateway.model.Meta;
import com.genius.demo.gateway.model.SearchResponse.Response.Hit.Result.PrimaryArtist;
import com.genius.demo.gateway.model.SongResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SongService {

    private final SearchClient searchClient;
    private final SongClient songClient;

    public List<String> getSongsByArtist(String searchString, boolean exactMatch, String token, Integer perPage, Integer page) throws SearchFailureException, SongClientFailureException {

        var artistId = getMatchedArtistId(searchString, exactMatch, token);

        if (artistId.isPresent()) {
            return getSongsBy(artistId.get(), token, perPage, page);
        } else {
            throw new SearchFailureException("Artist Search Failed");
        }
    }

    public Optional<String> getMatchedArtistId(String searchString, boolean exactMatch, String token) throws SearchFailureException {

        var response = searchClient.getSearchResponse(searchString, token);

        var status = Optional.ofNullable(response.getMeta()).map(Meta::getStatus);

        if (status.isEmpty() || status.get() != HttpStatus.OK.value()) {
            log.error("Artist search failed");
            throw new SearchFailureException("Artist search failed");
        }

        var primaryArtists = response.getResponse().getHits().stream()
                .map(hit -> hit.getResult().getPrimaryArtist())
                .collect(Collectors.toList());

        var matchedArtist = primaryArtists.stream()
                .filter(artist -> artist.getName().equals(searchString))
                .findFirst();

        // If exact match doesn't work, then consider collabs
        if (!exactMatch && matchedArtist.isEmpty()) {
            matchedArtist = primaryArtists.stream()
                    .filter(primaryArtist -> primaryArtist.getName().contains(searchString))
                    .findFirst();
        }

        return matchedArtist
                .map(PrimaryArtist::getId);
    }

    public List<String> getSongsBy(String artistId, String token, Integer perPage, Integer page) throws SongClientFailureException {

        var response = songClient.getSongs(artistId, token, perPage, page);

        var status = Optional.ofNullable(response.getMeta()).map(Meta::getStatus);

        if (status.isEmpty() || status.get() != HttpStatus.OK.value()) {
            log.error("Song Client failed");
            throw new SongClientFailureException("Song Client failed");
        }

        return response.getResponse().getSongs().stream()
                .map(SongResponse.Response.Song::getTitle)
                .collect(Collectors.toList());
    }

}
