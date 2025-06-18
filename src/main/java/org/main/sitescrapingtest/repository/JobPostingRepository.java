package org.main.sitescrapingtest.repository;

import org.main.sitescrapingtest.model.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}
