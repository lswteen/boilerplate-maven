package com.farfarcoder.scm.domain.bamboo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity(name="projmgmt")
@Table(name="PROJMGMT", schema = "PUBLIC")
public class ProjMgmtEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="BIZDIV")
    private String bizDiv;

    @Column(name="BIZMGR")
    private String bizMgr;

    @Column(name="CONFIG")
    private String config;

    @Column(name="DEV")
    private String dev;

    @Column(name="OPER")
    private String oper;

    @Column(name="STATUS")
    private String status;

    @Column(name="BITBUCKETNAME")
    private String bitbucketName;

    @Column(name="BITBUCKETKEY")
    private String bitbucketKey;

    @Column(name="BITBUCKETDESC")
    private String bitbucketDesc;

    @Column(name="BAMBOONAME")
    private String bambooName;

    @Column(name="BAMBOOKEY")
    private String bambooKey;

    @Column(name="BAMBOODESC")
    private String bambooDesc;

    @Column(name="DEPLOYMGR")
    private String deployMgr;

    @Column(name="CREATEDAT")
    @Builder.Default
    private Instant createdAt = Instant.now();

    @Column(name="UPDATEDAT")
    @Builder.Default
    private Instant updatedAt = Instant.now();

    // PROJECT와 OneToOne 관계
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BAMBOOKEY", referencedColumnName = "PROJECT_KEY", insertable = false, updatable = false)
    private ProjectEntity projectEntity;
}
