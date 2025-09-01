package com.farfarcoder.scm.domain.bamboo.service;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.repository.ProjMgmtRepository;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjMgmtDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NullProjMgmtService {
    private final ProjMgmtRepository projMgmtRepository;

    /**
     * bambookey가 'NULL' 문자열인 ProjMgmt 데이터를 DTO로 조회
     * N+1 문제 완전 해결된 버전
     */
    public List<ProjMgmtDto> findNullBambooKeyProjMgmtAsDto() {
        log.debug("Finding projmgmt data where bambookey = 'NULL' as DTO (N+1 해결)");

        List<Object[]> results = projMgmtRepository.findNullBambooKeyAsDto();

        List<ProjMgmtDto> dtos = results.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        log.info("Found {} projmgmt DTOs with bambookey = 'NULL'", dtos.size());
        return dtos;
    }

    /**
     * bambookey가 'NULL' 문자열인 ProjMgmt 데이터만 조회 (Entity 버전 - N+1 문제 있음)
     * Native Query 사용으로 N+1 문제 해결 시도했으나 여전히 발생
     */
    public List<ProjMgmtEntity> findNullBambooKeyProjMgmt() {
        log.debug("Finding projmgmt data where bambookey = 'NULL' using Native Query");

        List<ProjMgmtEntity> entities = projMgmtRepository.findByBambooKeyIsNullString();
        log.info("Found {} projmgmt entities with bambookey = 'NULL'", entities.size());

        return entities;
    }

    /**
     * Object[] 배열을 ProjMgmtDto로 변환
     */
    private ProjMgmtDto mapToDto(Object[] row) {
        return ProjMgmtDto.builder()
                .id(row[0] != null ? ((Number) row[0]).longValue() : null)
                .bizDiv((String) row[1])
                .bizMgr((String) row[2])
                .config((String) row[3])
                .dev((String) row[4])
                .oper((String) row[5])
                .status((String) row[6])
                .bitbucketName((String) row[7])
                .bitbucketKey((String) row[8])
                .bitbucketDesc((String) row[9])
                .bambooName((String) row[10])
                .bambooKey((String) row[11])
                .bambooDesc((String) row[12])
                .deployMgr((String) row[13])
                .createdAt(convertToInstant(row[14]))
                .updatedAt(convertToInstant(row[15]))
                .build();
    }

    /**
     * Timestamp를 Instant로 안전하게 변환
     */
    private Instant convertToInstant(Object timestamp) {
        if (timestamp == null) {
            return null;
        }

        if (timestamp instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) timestamp).toInstant();
        } else if (timestamp instanceof Instant) {
            return (Instant) timestamp;
        } else {
            log.warn("Unexpected timestamp type: {}, value: {}", timestamp.getClass(), timestamp);
            return null;
        }
    }

}
