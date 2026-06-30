package com.resumematcher.app;

import org.springframework.web.bind.annotation.*;

// This class exposes our logic as a REST API endpoint that the
// frontend (JavaScript fetch call) can talk to.
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allows frontend (different port/file) to call this API
public class MatchController {

    private final MatchService matchService;

    // Spring automatically gives us a MatchService instance here (Dependency Injection)
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // This runs when frontend sends a POST request to /api/match
    @PostMapping("/match")
    public MatchResponse match(@RequestBody MatchRequest request) {
        return matchService.calculateMatch(request.getResumeText(), request.getJdText());
    }
}
