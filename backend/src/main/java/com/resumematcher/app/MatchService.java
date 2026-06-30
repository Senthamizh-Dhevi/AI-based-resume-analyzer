package com.resumematcher.app;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

// This is the main orchestrator: it uses TF-IDF + Cosine Similarity (our AI
// scoring engine) to calculate the match score, and separately identifies
// missing keywords to power the suggestions feature.
@Service
public class MatchService {

    private final SuggestionService suggestionService;
    private final SynonymService synonymService;
    private final TfIdfService tfIdfService;

    public MatchService(SuggestionService suggestionService,
                         SynonymService synonymService,
                         TfIdfService tfIdfService) {
        this.suggestionService = suggestionService;
        this.synonymService = synonymService;
        this.tfIdfService = tfIdfService;
    }

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a","an","the","and","or","but","is","are","was","were","be","been",
        "in","on","at","to","for","of","with","by","as","this","that","it",
        "from","will","can","has","have","had","i","you","we","they","your",
        "our","their","my","his","her","its"
    ));

    // Used only for finding MISSING keywords (for the suggestions feature) -
    // the actual score now comes from TF-IDF + Cosine Similarity below.
    private Set<String> extractKeywords(String text) {
        String[] words = text.toLowerCase()
                              .replaceAll("[^a-z0-9\\s]", " ")
                              .split("\\s+");

        return Arrays.stream(words)
                     .filter(w -> w.length() > 2 && !STOPWORDS.contains(w))
                     .map(synonymService::normalize)
                     .collect(Collectors.toSet());
    }

    public MatchResponse calculateMatch(String resumeText, String jdText) {
        Set<String> resumeWords = extractKeywords(resumeText);
        Set<String> jdWords = extractKeywords(jdText);

        List<String> missing = jdWords.stream()
                                       .filter(word -> !resumeWords.contains(word))
                                       .collect(Collectors.toList());

        // The actual AI-based score: TF-IDF vectorization + Cosine Similarity
        int score = tfIdfService.calculateSimilarityScore(resumeText, jdText);

        List<String> suggestions = suggestionService.generateSuggestions(score, missing);

        return new MatchResponse(score, missing, suggestions);
    }
}
