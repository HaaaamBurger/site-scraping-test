package org.main.sitescrapingtest.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.main.sitescrapingtest.repository.JobPostingRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class JobsScraperService {

    private static final String TECH_STARS_URI = "https://jobs.techstars.com";

    private final JobPostingRepository jobPostingRepository;

    public void scrapeJobs(String jobFunction) {

        try {
            String jobFunctionsJson = generateJobFunctionsJson(jobFunction);
            String jobFunctionsJsonEncoded = Base64.getEncoder().encodeToString(jobFunctionsJson.getBytes(StandardCharsets.UTF_8));
            String connectionUrl = TECH_STARS_URI + "/jobs?filter=" + jobFunctionsJsonEncoded;

            Document document = Jsoup.connect(connectionUrl).get();


            System.out.println(document.outerHtml());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateJobFunctionsJson(String jobFunction) {
        return "{\"job_functions\":[\"" + jobFunction + "\"]}";
    }

}
