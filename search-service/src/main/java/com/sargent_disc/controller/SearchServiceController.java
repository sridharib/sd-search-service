package com.sargent_disc.controller;

import com.sargent_disc.model.SearchResult;
import com.sargent_disc.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class SearchServiceController {

    private final SearchService searchService;

    public SearchServiceController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/search")
    public Mono<SearchResult> search(@RequestParam String searchQuery) {
        log.debug("Inside SearchServiceController search. Search Query -> {}", searchQuery);
        return Mono.just(searchService.search(searchQuery));
    }

}