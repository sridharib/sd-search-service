package com.sargent_disc.controller;

import com.sargent_disc.model.SearchResult;
import com.sargent_disc.search.service.SearchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceControllerTest {

    private SearchServiceController searchServiceController;
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = mock(SearchService.class);
        searchServiceController = new SearchServiceController(searchService);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void search() {
        when(searchService.search(anyString())).thenReturn(SearchResult.builder().build());
        searchServiceController.search("brown fox");
        verify(searchService).search(anyString());
    }
}