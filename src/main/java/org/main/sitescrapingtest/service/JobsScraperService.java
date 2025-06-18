package org.main.sitescrapingtest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.main.sitescrapingtest.exception.JobScrapingException;
import org.main.sitescrapingtest.model.JobPosting;
import org.main.sitescrapingtest.repository.JobPostingRepository;
import org.main.sitescrapingtest.util.JobsScraperUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobsScraperService implements JobsScraper {

    private final JobPostingRepository jobPostingRepository;

    private final JobsScraperUtils jobsScraperUtils;

    @Override
    public List<JobPosting> scrapeJobs(String jobFunction) {
        try {
            Document document = fetchDocument(jobFunction);
            List<JobPosting> jobPostings = extractJobs(document);

            return jobPostingRepository.saveAll(jobPostings);

        } catch (IOException e) {
            throw new JobScrapingException(e.getMessage());
        }
    }

    private Document fetchDocument(String jobFunction) throws IOException {
        String urlWithEncodedFunction = jobsScraperUtils.getUrlWithEncodedFunction(jobFunction);
        return Jsoup.connect(urlWithEncodedFunction).get();
    }

    private List<JobPosting> extractJobs(Document document) throws JsonProcessingException {
        Elements scriptTags = document.getElementsByTag("script");

        for (Element script : scriptTags) {
            if (script.html().contains("jobDiscardIds")) {
                JsonNode jobs = jobsScraperUtils.extractJobsFromHtml(script.html());

                List<JobPosting> jobPostings = new ArrayList<>();

                for (JsonNode jobNode : jobs) {
                    JobPosting job = parseJob(jobNode);
                    jobPostings.add(job);
                }

                return jobPostings;
            }
        }

        return List.of();
    }

    private JobPosting parseJob(JsonNode node) {
        return JobPosting.builder()
                .jobId(node.path("id").asLong())
                .title(node.path("title").asText())
                .jobUrl(node.path("url").asText())
                .location(node.path("locations").toString())
                .organizationTitle(node.path("organization").path("name").asText())
                .organizationLogo(node.path("organization").path("logoUrl").asText())
                .workMode(node.path("workMode").asText())
                .postedAt(node.path("createdAt").asLong())
                .descriptionAvailable(node.path("hasDescription").asBoolean())
                .build();
    }
}
