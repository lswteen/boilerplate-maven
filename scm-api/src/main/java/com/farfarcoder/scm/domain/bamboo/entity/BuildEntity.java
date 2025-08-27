package com.farfarcoder.scm.domain.bamboo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name="builds")
@Table(name="BUILD", schema = "PUBLIC")
public class BuildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BUILD_ID")
    private Long buildId;

    @Column(name="PROJECT_ID")
    private Long projectId;

    @Column(name="BUILD_TITLE")
    private String buildTitle;

    @Column(name="BUILD_TYPE")
    private String buildType;

    @Column(name="CREATED_AT")
    @Builder.Default
    private Instant createdAt= Instant.now();

    @Column(name="UPDATED_AT")
    @Builder.Default
    private Instant updatedAt= Instant.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROJECT_ID", insertable = false, updatable = false)
    private ProjectEntity projectEntity;
}
