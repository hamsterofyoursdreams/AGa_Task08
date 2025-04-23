package org.AGa.rsService;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@Startup
public class DictionaryService {

    private Trie trie = new Trie();
    private int searchLimit;

    @PostConstruct
    public void load() {
        String dictionaryPath = ConfigurationLoader.getProperty("dictionary.path");
        String limitStr = ConfigurationLoader.getProperty("search.limit");
        searchLimit = (limitStr != null) ? Integer.parseInt(limitStr) : 100;

        if (dictionaryPath == null || dictionaryPath.isEmpty()) {
            System.err.println("Dictionary path is not configured.");
            return;
        }

        try {
            List<String> words = Files.readAllLines(Paths.get(dictionaryPath))
                    .stream()
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .collect(Collectors.toList());
            for (String word : words) {
                trie.insert(word.toLowerCase(Locale.ROOT));
            }
            System.out.println("Dictionary loaded. Total words: " + words.size());
        } catch (IOException e) {
            System.err.println("Failed to load dictionary: " + e.getMessage());
        }
    }

    public List<String> findWords(String prefix) {
        if (prefix == null || prefix.isEmpty()) return Collections.emptyList();
        List<String> found = trie.search(prefix.toLowerCase(Locale.ROOT));
        return found.stream().limit(searchLimit).collect(Collectors.toList());
    }
}
