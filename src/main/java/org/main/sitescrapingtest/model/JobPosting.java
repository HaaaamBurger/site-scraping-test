package org.main.sitescrapingtest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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
