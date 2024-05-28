package com.keyin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private boolean testInstanceSame = false;

    @BeforeEach
    public void setup() {
        // Initialize mockSuggestionDB before each test method
        mockSuggestionDB = Mockito.mock(SuggestionsDatabase.class);
    }

    @Test
    public void testGenerateSuggestions() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("hellw").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        testInstanceSame = true;
        Assertions.assertTrue(testInstanceSame);
        Assertions.assertFalse(suggestionEngine.generateSuggestions("hello").contains("hello"));
    }

    @Test
    public void testSuggestionsAsMock() {
        Map<String,Integer> wordMapForTest = new HashMap<>();
        wordMapForTest.put("test", 1);
        Mockito.when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);
        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);
        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));
        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
    }

    @Test
    public void testGenerateSuggestionsKnownWords() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        String suggestions = suggestionEngine.generateSuggestions("hello");
        Assertions.assertEquals("", suggestions);
    }

    @Test
    public void testGenerateSuggestionsUnknownWords() throws Exception {
        suggestionEngine.loadDictionaryData(Paths.get(ClassLoader.getSystemResource("words.txt").toURI()));
        String suggestions = suggestionEngine.generateSuggestions("hellw");
        Assertions.assertTrue(suggestions.contains("hello"));
    }
}
