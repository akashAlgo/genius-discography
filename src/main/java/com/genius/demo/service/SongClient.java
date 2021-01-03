package com.genius.demo.service;

import com.genius.demo.gateway.model.SongResponse;

public interface SongClient {

    SongResponse getSongs(String artistId, String token, Integer perPage, Integer page);
}
