package org.main.sitescrapingtest.controller;

import lombok.RequiredArgsConstructor;
import org.main.sitescrapingtest.model.JobPosting;
import org.main.sitescrapingtest.service.JobsScraper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobsScraperController {

    private final JobsScraper jobsScraper;

    @GetMapping("/scrape")
    public Page<JobPosting> scrapeJobs(
            @RequestParam String jobFunction,
            @RequestParam(required = false) String location,
            @PageableDefault(sort = "postedAt", direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return jobsScraper.scrapeJobs(jobFunction, location, pageable);

    }

}
