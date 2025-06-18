package org.main.sitescrapingtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long jobId;

    private String title;

    private String jobUrl;

    private String organizationTitle;

    private String organizationLogo;

    private String workMode;

    private String location;

    private boolean descriptionAvailable;

    private Long postedAt;
}
