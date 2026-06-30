package com.resumematcher.app;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

// This is the "brain" of the app - the same logic from script.js,
// just rewritten in Java. Keeping logic in a separate Service class
// (instead of directly in the Controller) is a common Java best practice.
@Service
public class MatchService {

    private final SuggestionService suggestionService;

    public MatchService(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a","an","the","and","or","but","is","are","was","were","be","been",
        "in","on","at","to","for","of","with","by","as","this","that","it",
        "from","will","can","has","have","had","i","you","we","they","your",
        "our","their","my","his","her","its"
    ));

    // Cleans text and extracts unique keywords
    private Set<String> extractKeywords(String text) {
        String[] words = text.toLowerCase()
                              .replaceAll("[^a-z0-9\\s]", " ")
                              .split("\\s+");

        return Arrays.stream(words)
                     .filter(w -> w.length() > 2 && !STOPWORDS.contains(w))
                     .collect(Collectors.toSet());
    }

    // Compares resume vs job description and returns score + missing keywords + suggestions
    public MatchResponse calculateMatch(String resumeText, String jdText) {
        Set<String> resumeWords = extractKeywords(resumeText);
        Set<String> jdWords = extractKeywords(jdText);

        List<String> missing = jdWords.stream()
                                       .filter(word -> !resumeWords.contains(word))
                                       .collect(Collectors.toList());

        int matchedCount = jdWords.size() - missing.size();
        int score = jdWords.isEmpty() ? 0 : (matchedCount * 100) / jdWords.size();

        // Generate personalized suggestions based on score and missing keywords
        List<String> suggestions = suggestionService.generateSuggestions(score, missing);

        return new MatchResponse(score, missing, suggestions);
    }
}
