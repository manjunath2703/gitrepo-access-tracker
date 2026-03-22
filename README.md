# GitHub Access Tracker

## 📌 Overview

This project is a Spring Boot application that connects to GitHub and generates a report showing which users have access to which repositories within a given organization.

The service retrieves repositories from a GitHub organization, identifies users with access, and exposes a REST API to return this data in a structured JSON format.

---

## 🚀 How to Run the Project

### 1. Clone the repository

bash
git clone https://github.com/<your-username>/github-access-tracker.git
cd github-access-tracker

### 2. Configure Environment Variable (IMPORTANT)

Set your GitHub Personal Access Token:

#### Windows (PowerShell)

powershell
setx GITHUB_TOKEN "your_token_here"

#### Linux/Mac

bash
export GITHUB_TOKEN=your_token_here

---

### 3. Update Configuration

Open:

src/main/resources/application.properties

Make sure it contains:

properties
spring.application.name=github-access-tracker
github.token=${GITHUB_TOKEN}
github.org=<your-github-username-or-org>

---

### 4. Run the Application

#### Using Eclipse

* Right click project → Run As → Spring Boot App

#### Using Maven

bash
mvn spring-boot:run

---

## 🔐 Authentication Configuration

The application uses a **GitHub Personal Access Token (PAT)** for authentication.

* Token is NOT stored in code
* It is injected using environment variable:

  GITHUB_TOKEN

### Required Permissions:

* Metadata → Read-only
* Members → Read-only
* Contents → Read-only (optional)

This ensures secure and minimal access.

---

## 📡 API Endpoint

### Get Access Report

http
GET /api/access-report

### Example:

bash
curl http://localhost:8080/api/access-report

### Sample Response:

json
{
  "user1": ["repo1", "repo2"],
  "user2": ["repo2"]
}

This shows which repositories each user has access to.
---

## ⚙️ Assumptions

* The GitHub organization or username is valid
* The provided token has sufficient read permissions
* Only public or accessible repositories are fetched
* API rate limits are handled implicitly (no advanced retry logic implemented)

---

## 🏗️ Design Decisions

### 1. Clean Architecture

* Controller → Handles API requests
* Service → Business logic (GitHub API calls)
* DTO → Data transfer objects

---

### 2. Caching

* Used Spring Cache (`@Cacheable`)
* Reduces repeated GitHub API calls
* Improves performance for large orgs

---

### 3. Scalability Considerations

* Designed to handle:

  * 100+ repositories
  * 1000+ users
* Avoided unnecessary sequential calls where possible

---

### 4. Security

* Token stored using environment variables
* `.gitignore` prevents sensitive data leaks

---

## 📦 Tech Stack

* Java 17
* Spring Boot 3
* Maven
* GitHub REST API

---

## ✅ Future Improvements

* Add pagination handling for large datasets
* Implement rate limit handling
* Add unit and integration tests
* Add UI dashboard

---

## 👨‍💻 Author

Manjunath B M
