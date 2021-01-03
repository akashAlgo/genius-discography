package com.genius.demo.api;

import com.genius.demo.exception.SearchFailureException;
import com.genius.demo.service.SongService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @SneakyThrows
    @GetMapping
    public List<String> getSongsByArtist(@RequestParam(name = "artist_name") String artistName,
                                         @RequestParam(name = "exact_match", required = false) boolean exactMatch,
                                         @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(name = "limit", required = false, defaultValue = "20") int limit,
                                         @RequestHeader(name = "Authorization") String authToken) throws SearchFailureException {

        return songService.getSongsByArtist(artistName, exactMatch, authToken, limit, page);
    }
}
