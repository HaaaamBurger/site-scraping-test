# Techstars Job Scraper

##  Description

This project is a Spring Boot application that scrapes job listings from [jobs.techstars.com](https://jobs.techstars.com/jobs) based on a selected **Job Function**.  
The collected job data is stored in an SQL database, including fields such as position title, location, organization, posting date, description, and more.

---

## ️ Technologies

- **Java 17+**
- **Spring Boot**
- **Jsoup** (for HTML parsing)
- **Jackson** (for JSON handling)
- **JPA / Hibernate** (ORM)
- **PostgreSQL**
- **Maven**

---

## Features

- Accept a specific **Job Function** as input
- Scrape all jobs filtered by that function
- Store results in an SQL database
- (Optional) Export to Google Sheets via API
- (Optional) Docker support for containerized deployment

---

## Job Fields Collected

- Position title
- Job page URL
- Organization name
- Organization logo (image link)
- Work mode (e.g., Remote, On-site)
- Location(s)
- Posted date (Unix timestamp)
- Description (HTML format)
- Tags

---

##  How to Run

1. Make sure you have:
    - Java 17+
    - Maven
    - PostgreSQL or another SQL database

