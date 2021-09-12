package com.sargent_disc.integration;

import com.sargent_disc.controller.SearchServiceController;
import com.sargent_disc.model.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebFluxTest(SearchServiceController.class)
class SearchServiceIntegrationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    void testSearch() {
        SearchResult result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("searchQuery", "all work and no play")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(SearchResult.class)
                .returnResult().getResponseBody();

        assertTrue(result.getFiles().stream().anyMatch(path -> path.endsWith("a.txt")));
        assertTrue(result.getFiles().stream().anyMatch(path -> path.endsWith("b.txt")));
    }

    @Test
    void whenSearchForTextNotPresentInAnyFileThenExpectNoFile() {
        SearchResult result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("searchQuery", "They call me Mr. Tibbs")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(SearchResult.class)
                .returnResult().getResponseBody();

        assertEquals(0, result.getFiles().size());
    }

}