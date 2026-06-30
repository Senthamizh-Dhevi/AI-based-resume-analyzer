# AI Resume Matcher

An AI-powered web application that analyzes how well a resume matches a job description, similar to how Applicant Tracking Systems (ATS) screen resumes. It goes a step further than basic keyword matching by giving personalized suggestions on how to improve the resume for a specific job. Built as a full-stack project for the MVIT Best Resume Competition (2028 Batch).

## What it does

Paste in your resume and a job description, and the app:
- Calculates a match score based on keyword overlap
- Highlights missing keywords you might want to add
- Generates personalized suggestions for improving your resume, categorized by skill type (languages, frameworks, tools, soft skills)
- Gives instant visual feedback with an animated circular score indicator

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript (Fetch API)
- **Backend:** Java, Spring Boot (REST API)
- **Build Tool:** Maven

## How it works

1. The frontend collects resume text and job description text from the user
2. This data is sent to a Java backend via a POST request to `/api/match`
3. The backend extracts meaningful keywords from both texts (removing common stopwords) and compares them to calculate a match percentage
4. A separate suggestion engine analyzes the missing keywords, categorizes them (programming languages, frameworks, tools/cloud platforms, soft skills), and generates targeted, human-readable advice based on the score and the gaps found
5. The score, missing keywords, and suggestions are sent back as JSON and displayed with an animated circular progress indicator and a suggestions panel

## Project Structure

```
├── index.html          # Main UI
├── style.css            # Styling
├── script.js             # Frontend logic (calls backend API)
└── backend/
    ├── pom.xml            # Maven dependencies
    └── src/main/java/com/resumematcher/app/
        ├── ResumeMatcherApplication.java   # Spring Boot entry point
        ├── MatchController.java             # REST API endpoint
        ├── MatchService.java                  # Core matching logic
        ├── SuggestionService.java             # Generates personalized resume suggestions
        ├── MatchRequest.java                  # Request data model
        └── MatchResponse.java                 # Response data model (score, missing keywords, suggestions)
```

## Key Feature: Smart Suggestions

Rather than just listing missing keywords, the app classifies them into categories and tailors its advice accordingly:

- **Programming languages** → suggests adding them to the Skills section if genuinely known
- **Frameworks** → suggests building or mentioning a relevant project
- **Tools/cloud platforms** → suggests highlighting even basic exposure
- **Soft skills** → suggests weaving them naturally into project/experience descriptions

It also gives an overall verdict based on the match score (e.g., "Good match overall" vs. "This role may need a more tailored resume version").

## Running Locally

### Backend
```bash
cd backend
mvn spring-boot:run
```
Server starts on `http://localhost:8080`

### Frontend
Open `index.html` directly in your browser.

## Future Improvements

- Persist match history using MySQL
- Improve keyword matching using actual NLP/semantic similarity instead of exact word matching
- Add resume format/structure scoring (not just keywords)
- Allow PDF/DOCX resume upload instead of copy-pasting text

## Author

Built by Senthamizh Dhevi R — 3rd Year CSE, Manakula Vinayagar Institute of Technology
