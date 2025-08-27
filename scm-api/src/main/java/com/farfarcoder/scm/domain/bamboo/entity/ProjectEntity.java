package com.farfarcoder.scm.domain.bamboo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name="projects")
@Table(name="PROJECT", schema = "PUBLIC")
public class ProjectEntity {

    @Id
    @Column(name="PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Column(name="PROJECT_KEY")
    private String projectKey;

    @Column(name="TITLE")
    private String title;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToOne(mappedBy = "projectEntity", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private ProjMgmtEntity projMgmt;

    @OneToMany(mappedBy = "projectEntity", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<BuildEntity> builds;
}
