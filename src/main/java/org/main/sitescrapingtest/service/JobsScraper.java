package org.main.sitescrapingtest.service;

import org.main.sitescrapingtest.model.JobPosting;

import java.util.List;

public interface JobsScraper {

    List<JobPosting> scrapeJobs(String jobFunction);

}
