package com.resumematcher.app;

import org.springframework.stereotype.Service;
import java.util.Map;

// This is our "simple AI" layer: it recognizes that different words can mean
// the same thing (e.g. "js" and "javascript"), and normalizes them to one
// canonical term before comparing. Without this, a resume that says "JS"
// wouldn't get credit for a job description that says "JavaScript" - even
// though they mean the same thing. This is a simplified version of what's
// called "synonym normalization" in real NLP systems.
@Service
public class SynonymService {

    private static final Map<String, String> SYNONYMS = Map.ofEntries(
        Map.entry("js", "javascript"),
        Map.entry("reactjs", "react"),
        Map.entry("react.js", "react"),
        Map.entry("nodejs", "node"),
        Map.entry("node.js", "node"),
        Map.entry("expressjs", "express"),
        Map.entry("vuejs", "vue"),
        Map.entry("angularjs", "angular"),
        Map.entry("py", "python"),
        Map.entry("springboot", "spring"),
        Map.entry("spring boot", "spring"),
        Map.entry("postgres", "postgresql"),
        Map.entry("mongo", "mongodb"),
        Map.entry("k8s", "kubernetes"),
        Map.entry("ml", "machinelearning"),
        Map.entry("ai", "artificialintelligence"),
        Map.entry("oop", "objectoriented"),
        Map.entry("dsa", "datastructures"),
        Map.entry("api", "applicationprogramminginterface"),
        Map.entry("ui", "userinterface"),
        Map.entry("ux", "userexperience"),
        Map.entry("html5", "html"),
        Map.entry("css3", "css")
    );

    // Returns the canonical/standard form of a word, or the word itself
    // if no synonym mapping exists.
    public String normalize(String word) {
        return SYNONYMS.getOrDefault(word, word);
    }
}
