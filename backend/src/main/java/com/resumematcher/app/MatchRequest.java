package com.resumematcher.app;

// This class represents the JSON data the frontend will send us.
// Spring Boot automatically converts incoming JSON into this object.
public class MatchRequest {
    private String resumeText;
    private String jdText;

    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }

    public String getJdText() { return jdText; }
    public void setJdText(String jdText) { this.jdText = jdText; }
}
