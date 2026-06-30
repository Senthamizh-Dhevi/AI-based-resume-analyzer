package com.resumematcher.app;

import org.springframework.stereotype.Service;
import java.util.*;

// This class generates human-readable suggestions for improving a resume,
// based on the score and the keywords that are missing.
@Service
public class SuggestionService {

    // Known keyword categories - helps us give more specific advice
    // (e.g. "add a project using X" vs "mention this skill in your summary")
    private static final Set<String> LANGUAGES = Set.of(
        "java", "python", "javascript", "typescript", "c++", "c", "sql", "html", "css"
    );

    private static final Set<String> FRAMEWORKS = Set.of(
        "react", "angular", "vue", "spring", "django", "flask", "nodejs", "node",
        "express", "springboot", "bootstrap", "tailwind"
    );

    private static final Set<String> TOOLS_CLOUD = Set.of(
        "aws", "azure", "gcp", "docker", "kubernetes", "git", "github", "jenkins",
        "mongodb", "mysql", "postgresql", "linux", "jira"
    );

    private static final Set<String> SOFT_SKILLS = Set.of(
        "leadership", "communication", "teamwork", "collaboration", "agile",
        "scrum", "management", "problemsolving"
    );

    public List<String> generateSuggestions(int score, List<String> missingKeywords) {
        List<String> suggestions = new ArrayList<>();

        // 1. Overall guidance based on score range
        if (score >= 80) {
            suggestions.add("Your resume is a strong match for this role. Focus on polishing formatting and quantifying your achievements with numbers.");
        } else if (score >= 60) {
            suggestions.add("Good match overall. Adding a few of the missing keywords below (where genuinely applicable) could push your score higher.");
        } else if (score >= 40) {
            suggestions.add("Moderate match. Consider tailoring your resume more closely to this specific job description, especially your skills and project sections.");
        } else {
            suggestions.add("This role may need a more tailored resume version, or it may not be the closest fit for your current skill set - consider reviewing the core requirements closely.");
        }

        // 2. Categorize missing keywords and give targeted advice
        List<String> missingLanguages = new ArrayList<>();
        List<String> missingFrameworks = new ArrayList<>();
        List<String> missingTools = new ArrayList<>();
        List<String> missingSoftSkills = new ArrayList<>();

        for (String word : missingKeywords) {
            if (LANGUAGES.contains(word)) missingLanguages.add(word);
            else if (FRAMEWORKS.contains(word)) missingFrameworks.add(word);
            else if (TOOLS_CLOUD.contains(word)) missingTools.add(word);
            else if (SOFT_SKILLS.contains(word)) missingSoftSkills.add(word);
        }

        if (!missingLanguages.isEmpty()) {
            suggestions.add("Programming languages to highlight if you know them: " + String.join(", ", missingLanguages) +
                ". If you've used these in coursework or personal projects, add them to your Skills section.");
        }

        if (!missingFrameworks.isEmpty()) {
            suggestions.add("Consider building or mentioning a project using: " + String.join(", ", missingFrameworks) +
                ". Frameworks listed in the job description carry significant weight with recruiters.");
        }

        if (!missingTools.isEmpty()) {
            suggestions.add("Tools/platforms this role values: " + String.join(", ", missingTools) +
                ". Even basic exposure (tutorials, small projects) is worth mentioning if true.");
        }

        if (!missingSoftSkills.isEmpty()) {
            suggestions.add("This role also emphasizes: " + String.join(", ", missingSoftSkills) +
                ". Try weaving these into your project descriptions through specific examples (e.g., 'collaborated with a team of 4 to...').");
        }

        // 3. General resume hygiene tip - always useful
        suggestions.add("Tip: Don't just list keywords - use them naturally within real project or experience descriptions, since ATS systems and recruiters both check for context, not just word-matching.");

        return suggestions;
    }
}

