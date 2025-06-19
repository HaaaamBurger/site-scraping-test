# INSTALL.md

## Installation & Setup Guide

This guide explains how to set up and run the **Techstars Job Scraper** application.

---

### Requirements

- Java 17+
- Maven 3.8+
- PostgreSQL
- Git
- Docker

---

### 1. Clone the Repository

```bash
    git clone https://github.com/HaaaamBurger/site-scraping-test.git
    cd techstars-job-scraper
```

### 2. Run docker compose file
```bash
    docker-compose up --build
```

### 3. Make request to REST endpoint
GET http://localhost:8080/scrape?jobFunction=Software%Engineering

