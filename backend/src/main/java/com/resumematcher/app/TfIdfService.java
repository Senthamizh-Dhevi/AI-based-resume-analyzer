package com.resumematcher.app;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

// This is the core "AI" engine of the project: it converts both texts into
// mathematical vectors using TF-IDF (Term Frequency - Inverse Document Frequency),
// then measures how similar those vectors are using Cosine Similarity.
// This is a well-established Information Retrieval technique - the same family
// of math used by early search engines to rank document relevance.
@Service
public class TfIdfService {

    private final SynonymService synonymService;

    public TfIdfService(SynonymService synonymService) {
        this.synonymService = synonymService;
    }

    private static final Set<String> STOPWORDS = new HashSet<>(Arrays.asList(
        "a","an","the","and","or","but","is","are","was","were","be","been",
        "in","on","at","to","for","of","with","by","as","this","that","it",
        "from","will","can","has","have","had","i","you","we","they","your",
        "our","their","my","his","her","its"
    ));

    // Step 1: Tokenize text into a list of cleaned, normalized words
    // (a List, not a Set, because TF-IDF needs word FREQUENCY - how many
    // times each word appears - not just whether it's present)
    private List<String> tokenize(String text) {
        String[] words = text.toLowerCase()
                              .replaceAll("[^a-z0-9\\s]", " ")
                              .split("\\s+");

        return Arrays.stream(words)
                     .filter(w -> w.length() > 2 && !STOPWORDS.contains(w))
                     .map(synonymService::normalize)
                     .collect(Collectors.toList());
    }

    // Step 2: Term Frequency - how often each word appears in a document,
    // normalized by document length (so longer documents aren't unfairly favored)
    private Map<String, Double> computeTF(List<String> tokens) {
        Map<String, Double> tf = new HashMap<>();
        int totalWords = tokens.size();

        for (String word : tokens) {
            tf.merge(word, 1.0, Double::sum);
        }
        for (String word : tf.keySet()) {
            tf.put(word, tf.get(word) / totalWords);
        }
        return tf;
    }

    // Step 3: Inverse Document Frequency - words that appear in BOTH documents
    // are less "distinctive" (e.g. common words like "experience"), while words
    // unique to one document carry more weight in distinguishing them.
    // We treat resume + JD as a 2-document corpus for this calculation.
    private Map<String, Double> computeIDF(List<String> resumeTokens, List<String> jdTokens) {
        Set<String> vocabulary = new HashSet<>();
        vocabulary.addAll(resumeTokens);
        vocabulary.addAll(jdTokens);

        Set<String> resumeSet = new HashSet<>(resumeTokens);
        Set<String> jdSet = new HashSet<>(jdTokens);

        Map<String, Double> idf = new HashMap<>();
        int totalDocs = 2;

        for (String word : vocabulary) {
            int docsContainingWord = 0;
            if (resumeSet.contains(word)) docsContainingWord++;
            if (jdSet.contains(word)) docsContainingWord++;

            // Standard smoothed IDF formula: log((N+1)/(df+1)) + 1
            double value = Math.log((double) (totalDocs + 1) / (docsContainingWord + 1)) + 1;
            idf.put(word, value);
        }
        return idf;
    }

    // Step 4: Build the final TF-IDF vector for one document over the shared vocabulary
    private double[] buildVector(Map<String, Double> tf, Map<String, Double> idf, List<String> vocabulary) {
        double[] vector = new double[vocabulary.size()];
        for (int i = 0; i < vocabulary.size(); i++) {
            String word = vocabulary.get(i);
            double tfValue = tf.getOrDefault(word, 0.0);
            double idfValue = idf.getOrDefault(word, 0.0);
            vector[i] = tfValue * idfValue;
        }
        return vector;
    }

    // Step 5: Cosine Similarity - measures the angle between two vectors.
    // 1.0 means identical direction (perfect match), 0 means completely unrelated.
    private double cosineSimilarity(double[] vecA, double[] vecB) {
        double dotProduct = 0.0, normA = 0.0, normB = 0.0;

        for (int i = 0; i < vecA.length; i++) {
            dotProduct += vecA[i] * vecB[i];
            normA += Math.pow(vecA[i], 2);
            normB += Math.pow(vecB[i], 2);
        }

        if (normA == 0 || normB == 0) return 0.0;
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    // Public method: ties all the steps together and returns a 0-100 score
    public int calculateSimilarityScore(String resumeText, String jdText) {
        List<String> resumeTokens = tokenize(resumeText);
        List<String> jdTokens = tokenize(jdText);

        if (resumeTokens.isEmpty() || jdTokens.isEmpty()) return 0;

        Map<String, Double> resumeTF = computeTF(resumeTokens);
        Map<String, Double> jdTF = computeTF(jdTokens);
        Map<String, Double> idf = computeIDF(resumeTokens, jdTokens);

        List<String> vocabulary = new ArrayList<>(idf.keySet());

        double[] resumeVector = buildVector(resumeTF, idf, vocabulary);
        double[] jdVector = buildVector(jdTF, idf, vocabulary);

        double similarity = cosineSimilarity(resumeVector, jdVector);
        return (int) Math.round(similarity * 100);
    }
}
