package org.main.sitescrapingtest.controller;

import lombok.RequiredArgsConstructor;
import org.main.sitescrapingtest.model.JobPosting;
import org.main.sitescrapingtest.service.JobsScraper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobsScraperController {

    private final JobsScraper jobsScraper;

    @GetMapping("/scrape")
    public ResponseEntity<List<JobPosting>> scrapeJobs(@RequestParam String jobFunction) {
        List<JobPosting> jobPostings = jobsScraper.scrapeJobs(jobFunction);

        return ResponseEntity.ok(jobPostings);
    }

}
