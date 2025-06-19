
---

### `README.md`

```md
# Techstars Job Scraper

Java Spring Boot application to scrape jobs from [jobs.techstars.com](https://jobs.techstars.com) by selected **Job Function** (e.g., Software Engineering, Marketing, Product Management, etc).

---

## Features

- Scrapes jobs using **Jsoup**
- Supports filtering by **Job Function**
- Stores data in a **SQL database (PostgreSQL)**
- Follows **clean code** and **OOP principles**
- Dumps results into SQL file in root

---

## Collected Data

Each job includes:

- Position name
- Job page URL
- Organization name
- Organization logo (image link)
- Labor function
- Tags
- Location(s)
- Posted date (Unix timestamp)
- Job description (HTML format)

---

## Tech Stack

- Java 17
- Spring Boot
- Jsoup + OkHttp
- JPA (Hibernate)
- PostgreSQL
- Maven
- Docker 
- Google Sheets API
