package com.farfarcoder.scm.web.dashboard.controller;

import com.farfarcoder.scm.domain.bamboo.entity.ProjMgmtEntity;
import com.farfarcoder.scm.domain.bamboo.service.NullProjMgmtService;
import com.farfarcoder.scm.web.dashboard.controller.dto.ProjMgmtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.media.Content;
import java.util.List;

@Slf4j
@Tag(name = "NULL ProjMgmt API", description = "bambookey가 'NULL'인 프로젝트 관리 데이터 조회 API")
@RestController
@RequestMapping("/api/v1/null-projmgmt")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173"}, maxAge = 3600)
public class NullProjMgmtController {
    private final NullProjMgmtService nullProjMgmtService;

    @Operation(
            summary = "bambookey가 'NULL'인 프로젝트관리 조회 (DTO - N+1 해결)",
            description = "ProjMgmt 테이블에서 bambookey가 'NULL' 문자열인 데이터를 DTO로 조회합니다. N+1 문제 완전 해결."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjMgmtDto.class)))
    })
    @GetMapping("/dto")
    public ResponseEntity<List<ProjMgmtDto>> findNullBambooKeyProjMgmtAsDto() {
        List<ProjMgmtDto> dtos = nullProjMgmtService.findNullBambooKeyProjMgmtAsDto();
        log.info("=========> findNullBambooKeyProjMgmtAsDto count : {}", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @Operation(
            summary = "bambookey가 'NULL'인 프로젝트관리 조회 (Entity - N+1 발생)",
            description = "ProjMgmt 테이블에서 bambookey가 'NULL' 문자열인 데이터를 Entity로 조회합니다. N+1 문제 발생."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjMgmtEntity.class)))
    })
    @GetMapping
    public ResponseEntity<List<ProjMgmtEntity>> findNullBambooKeyProjMgmt() {
        List<ProjMgmtEntity> entities = nullProjMgmtService.findNullBambooKeyProjMgmt();
        log.info("=========> findNullBambooKeyProjMgmt count : {}", entities.size());
        return ResponseEntity.ok(entities);
    }


}
