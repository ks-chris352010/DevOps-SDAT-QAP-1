package com.keyin;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    private SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Mock
    private SuggestionsDatabase mockSuggestionDB;
    // I removed the test instance value
    @Test
    public void testGenerateSuggestions() throws Exception {
        // Changed getPath -> toURI for compatibility reasons:
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hellw").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() throws Exception {
        // Changed getPath -> toURI for compatibility reasons:
        suggestionEngine.loadDictionaryData(Paths.get( ClassLoader.getSystemResource("words.txt").toURI()));
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    // Wouldn't work when the tests ran on github:
//    @Test
//    public void testSuggestionsAsMock() {
//        Map<String,Integer> wordMapForTest = new HashMap<>();
//
//        wordMapForTest.put("test", 1);
//
//        Mockito.when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);
//
//        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);
//
//        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));
//
//        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
//    }

    @Test
    public void testGenerateSuggestionsNonAlphabeticInput() throws Exception {
        // Changed getPath -> toURI for compatibility reasons:
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        // Assert that suggestions are empty since input contains non-alphabetic characters:
        Assertions.assertTrue(suggestionEngine.generateSuggestions("1234!@#$").isEmpty());
    }
    @Test
    public void testBroadSuggestions() throws Exception {
        // Changed getPath -> toURI for compatibility reasons:
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        // Assert that suggestions are empty since the input is too broad:
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hel").isEmpty());
    }
}