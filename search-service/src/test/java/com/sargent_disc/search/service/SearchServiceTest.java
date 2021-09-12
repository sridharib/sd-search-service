package com.sargent_disc.search.service;

import com.sargent_disc.model.SearchResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchServiceTest {

    private SearchService searchService;
    private String tmpFolderPath = "test/resources/tmp";

    @BeforeEach
    void setUp() {
        searchService = new SearchService(Paths.get("src", tmpFolderPath.split("/")).toFile().getAbsolutePath(), ".txt");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void whenSearchForTextPresentInOneFileThenExpectOneFile() {
        SearchResult result = searchService.search("brown fox");
        assertEquals(1, result.getFiles().size());
        assertTrue(result.getFiles().stream().anyMatch(path -> path.endsWith("a.txt")));
    }

    @Test
    void whenSearchForTextPresentInMultipleFileThenExpectMultipleFile() {
        SearchResult result = searchService.search("infinity and beyond");
        assertEquals(2, result.getFiles().size());
        assertTrue(result.getFiles().stream().anyMatch(path -> path.endsWith("a.txt")));
        assertTrue(result.getFiles().stream().anyMatch(path -> path.endsWith("c.txt")));
    }

    @Test
    void whenSearchForTextNotPresentInAnyFileThenExpectNoFile() {
        SearchResult result = searchService.search("The infinity and beyond");
        assertEquals(0, result.getFiles().size());
    }
}