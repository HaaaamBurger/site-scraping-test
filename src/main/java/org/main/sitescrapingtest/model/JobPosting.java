package org.main.sitescrapingtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionName;
    private String jobUrl;
    private String organizationTitle;
    private String organizationUrl;
    private String logoUrl;
    private String laborFunction;
    private Long postedTimestamp;

    @ElementCollection
    private List<String> locations;

    @ElementCollection
    private List<String> tags;

    @Column(columnDefinition = "TEXT")
    private String description;
}
