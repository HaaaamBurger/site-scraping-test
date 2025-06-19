package org.main.sitescrapingtest.service;

import org.main.sitescrapingtest.model.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobsScraper {

    Page<JobPosting> scrapeJobs(String jobFunction, String location, Pageable pageable);

}
