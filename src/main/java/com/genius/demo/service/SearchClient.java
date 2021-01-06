package com.genius.demo.service;

import com.genius.demo.exception.SearchFailureException;
import com.genius.demo.gateway.model.SearchResponse;

public interface SearchClient {

    SearchResponse getSearchResponse(String searchString, String token) throws SearchFailureException;

}
