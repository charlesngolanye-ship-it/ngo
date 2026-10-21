package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.GenerateReportRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GeneratedReportResponseDto;
import org.charlesngolanye.ngo.services.ReportGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
@Tag(name = "reports")
public class ReportGenerationController {
    private final ReportGenerationService reportGenerationService;

    @PostMapping("/generate")
    @Operation(summary = "Generates a report.")
    public ResponseEntity<GeneratedReportResponseDto> generate
            (@Valid @RequestBody GenerateReportRequestDto requestDto){

        GeneratedReportResponseDto response = reportGenerationService.generate(
                requestDto.getTemplateId(),
                requestDto.getReportingPeriodId());

        return ResponseEntity.ok(response);
    }
}
