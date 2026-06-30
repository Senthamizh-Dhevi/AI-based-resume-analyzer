# AI Resume Matcher

An AI-powered web application that analyzes how well a resume matches a job description, similar to how Applicant Tracking Systems (ATS) screen resumes. Built as a full-stack project for the MVIT Best Resume Competition (2028 Batch).

## What it does

- Upload your resume as a **PDF or DOCX file** (or paste text directly)
- Paste in a job description
- Get an instant match score powered by **TF-IDF and Cosine Similarity**, a real Information Retrieval technique
- See exactly which keywords are missing from your resume
- Receive **personalized, categorized suggestions** on what to improve

## Tech Stack

- **Frontend:** HTML, CSS, JavaScript (Fetch API, FormData for file uploads)
- **Backend:** Java, Spring Boot (REST API)
- **File Parsing:** Apache PDFBox (PDF), Apache POI (DOCX)
- **Build Tool:** Maven

## The AI/NLP Engine

The core of this project is a **TF-IDF (Term Frequency-Inverse Document Frequency) + Cosine Similarity** scoring system — the same family of mathematical technique used by early search engines and many real-world resume screening tools:

1. **Tokenization** — both texts are broken into individual words, with common filler words removed
2. **Synonym Normalization** — equivalent terms are unified (e.g., "JS" → "JavaScript", "K8s" → "Kubernetes") so valid skills aren't penalized for naming differences
3. **TF (Term Frequency)** — measures how often each word appears in a document, normalized by length
4. **IDF (Inverse Document Frequency)** — gives more weight to distinctive, specific words and less weight to generic ones
5. **Vectorization** — both documents are converted into TF-IDF weighted vectors
6. **Cosine Similarity** — the angle between the two vectors is calculated to produce a 0-100% match score

This is a genuine NLP/Information Retrieval technique, not a simple keyword counter.

## Project Structure

```
├── index.html
├── style.css
├── script.js
└── backend/
    ├── pom.xml
    └── src/main/
        ├── resources/
        │   └── application.properties
        └── java/com/resumematcher/app/
            ├── ResumeMatcherApplication.java   # Spring Boot entry point
            ├── MatchController.java             # REST API endpoints
            ├── MatchService.java                  # Orchestrates matching logic
            ├── TfIdfService.java                   # Core AI scoring engine (TF-IDF + Cosine Similarity)
            ├── SynonymService.java                 # Synonym normalization
            ├── SuggestionService.java              # Generates personalized resume tips
            ├── FileExtractionService.java          # Parses PDF/DOCX uploads
            ├── MatchRequest.java                   # Request data model
            └── MatchResponse.java                  # Response data model
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

## API Endpoints

| Endpoint | Method | Description |
|---|---|---|
| `/api/match` | POST | Accepts resume + JD text, returns score, missing keywords, and suggestions |
| `/api/extract-text` | POST | Accepts an uploaded PDF/DOCX file, returns extracted text |

## Future Improvements

- Resume formatting/structure analysis (not just content)
- Support for more file types (e.g., .doc, .txt)
- Persisting match history with a database

## Author

Built by Senthamizh Dhevi R — 3rd Year CSE, Manakula Vinayagar Institute of Technology