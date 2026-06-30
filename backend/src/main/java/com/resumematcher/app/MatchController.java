package com.resumematcher.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

// This class exposes our logic as REST API endpoints that the
// frontend (JavaScript fetch call) can talk to.
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allows frontend (different port/file) to call this API
public class MatchController {

    private final MatchService matchService;
    private final FileExtractionService fileExtractionService;

    public MatchController(MatchService matchService, FileExtractionService fileExtractionService) {
        this.matchService = matchService;
        this.fileExtractionService = fileExtractionService;
    }

    // Existing endpoint: compares plain text resume vs job description
    @PostMapping("/match")
    public MatchResponse match(@RequestBody MatchRequest request) {
        return matchService.calculateMatch(request.getResumeText(), request.getJdText());
    }

    // New endpoint: accepts an uploaded PDF/DOCX file and extracts its text.
    // The frontend will call this first, then put the extracted text into
    // the resume textarea automatically.
    @PostMapping("/extract-text")
    public Map<String, String> extractText(@RequestParam("file") MultipartFile file) {
        try {
            String text = fileExtractionService.extractText(file);
            return Map.of("text", text);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + e.getMessage());
        }
    }
}
