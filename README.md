# AI Resume Matcher

An AI-powered web application that analyzes how well a resume matches a job description, similar to how Applicant Tracking Systems (ATS) screen resumes. Built as a full-stack project for the MVIT Best Resume Competition (2028 Batch).

## What it does

Paste in your resume and a job description, and the app:
- Calculates a match score based on keyword overlap
- Highlights missing keywords you might want to add
- Gives instant visual feedback with an animated score indicator

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript (Fetch API)
- **Backend:** Java, Spring Boot (REST API)
- **Build Tool:** Maven

## How it works

1. The frontend collects resume text and job description text from the user
2. This data is sent to a Java backend via a POST request to `/api/match`
3. The backend extracts meaningful keywords from both texts (removing common stopwords), compares them, and calculates a match percentage
4. The score and any missing keywords are sent back as JSON and displayed with an animated circular progress indicator

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
        ├── MatchRequest.java                  # Request data model
        └── MatchResponse.java                 # Response data model
```

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

## Author

Built by Senthamizh Dhevi R — 3rd Year CSE, Manakula Vinayagar Institute of Technology
