package org.main.sitescrapingtest.controller;

import lombok.RequiredArgsConstructor;
import org.main.sitescrapingtest.service.JobsScraperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobsScraperController {

    private final JobsScraperService jobsScraperService;

    @GetMapping("/scrape")
    public ResponseEntity<String> scrapeJobs(@RequestParam String jobFunction) {
        jobsScraperService.scrapeJobs(jobFunction);

        return ResponseEntity.ok("Scraping completed!");
    }

}
