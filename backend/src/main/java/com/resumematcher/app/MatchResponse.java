package com.resumematcher.app;

import java.util.List;

// This class represents the JSON data we send BACK to the frontend.
public class MatchResponse {
    private int score;
    private List<String> missingKeywords;

    public MatchResponse(int score, List<String> missingKeywords) {
        this.score = score;
        this.missingKeywords = missingKeywords;
    }

    public int getScore() { return score; }
    public List<String> getMissingKeywords() { return missingKeywords; }
}
