package com.resumematcher.app;

import java.util.List;

// This class represents the JSON data we send BACK to the frontend.
public class MatchResponse {
    private int score;
    private List<String> missingKeywords;
    private List<String> suggestions;

    public MatchResponse(int score, List<String> missingKeywords, List<String> suggestions) {
        this.score = score;
        this.missingKeywords = missingKeywords;
        this.suggestions = suggestions;
    }

    public int getScore() { return score; }
    public List<String> getMissingKeywords() { return missingKeywords; }
    public List<String> getSuggestions() { return suggestions; }
}