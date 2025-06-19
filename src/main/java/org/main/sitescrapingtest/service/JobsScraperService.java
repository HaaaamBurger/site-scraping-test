package org.main.sitescrapingtest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.main.sitescrapingtest.exception.JobScrapingException;
import org.main.sitescrapingtest.model.JobPosting;
import org.main.sitescrapingtest.repository.JobPostingRepository;
import org.main.sitescrapingtest.util.JobsScraperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobsScraperService implements JobsScraper {

    private final JobPostingRepository jobPostingRepository;
    private final JobsScraperUtils jobsScraperUtils;

    @Transactional
    @Override
    public Page<JobPosting> scrapeJobs(String jobFunction, String location, Pageable pageable) {
        try {
            Document document = fetchDocument(jobFunction);
            List<JobPosting> jobPostings = extractJobs(document);

            saveNotExistedJobs(jobPostings);

            List<Long> jobIds = jobPostings.stream()
                    .map(JobPosting::getJobId)
                    .toList();

            Specification<JobPosting> spec = jobIdIn(jobIds).and(locationLike(location));

            return jobPostingRepository.findAll(spec, pageable);

        } catch (IOException e) {
            log.error("[JobsScraperService]: {}", e.getMessage());
            throw new JobScrapingException("Failed to fetch jobs for job function: " + jobFunction);
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

        throw new JobScrapingException("Job data script not found in page.");
    }

    private void saveNotExistedJobs(List<JobPosting> jobPostings) {
        List<Long> jobIds = jobPostings.stream()
                .map(JobPosting::getJobId)
                .toList();

        List<JobPosting> existingJobs = jobPostingRepository.findAllByJobIdIn(jobIds);
        Set<Long> existingIds = existingJobs.stream()
                .map(JobPosting::getJobId)
                .collect(Collectors.toSet());

        List<JobPosting> newJobs = jobPostings.stream()
                .filter(job -> !existingIds.contains(job.getJobId()))
                .toList();

        jobPostingRepository.saveAll(newJobs);
    }

    private Specification<JobPosting> jobIdIn(List<Long> jobIds) {
        return (root, query, cb) -> root.get("jobId").in(jobIds);
    }

    private Specification<JobPosting> locationLike(String location) {
        return (root, query, cb) -> {
            if (location == null || location.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
        };
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