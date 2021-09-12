package com.sargent_disc.search.service;

import com.sargent_disc.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchService {

    private static final String WORD_DELIMITER = " ";

    private final String folderPath;
    private final String fileExtension;

    public SearchService(@Value("${search_service.folder.path}") String folderPath,
                         @Value("${search_service.file.extension}") String fileExtension) {
        this.folderPath = folderPath;
        this.fileExtension = fileExtension;
    }

    public SearchResult search(String searchQuery) {

        List<String> files = new ArrayList<>();
        String[] searchQueries = searchQuery.split(WORD_DELIMITER);
        log.debug("Inside SearchService search. Going to search -> {} in the files under the folder -> {}", searchQuery, folderPath);

        try {
            List<Path> paths = Files.walk(Paths.get(folderPath), FileVisitOption.FOLLOW_LINKS)
                    .filter(fileName -> fileName.toString().toLowerCase().endsWith(fileExtension)).collect(Collectors.toList());

            for(Path path : paths) {
                List<String> result = Files.readAllLines(path).stream()
                        .flatMap(line ->  Arrays.asList(line.split(WORD_DELIMITER)).stream())
                        .distinct().collect(Collectors.toList());
                List<String> words = Arrays.asList(searchQueries);
                if(result.containsAll(words)) {
                    files.add(path.toFile().getAbsolutePath());
                }
            }
        } catch (Exception e) {
            log.error("Exception occurred while reading files. {}", e.getMessage());
        }
        return SearchResult.builder().files(files).build();
    }
}