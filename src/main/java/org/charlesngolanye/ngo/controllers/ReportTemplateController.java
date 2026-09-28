package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportTemplateRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportTemplateRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportTemplateResponseDto;
import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.entities.TemplateStatus;
import org.charlesngolanye.ngo.services.ReportTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report_templates")
@Tag(name = "report_templates")
public class ReportTemplateController {
    private final ReportTemplateService reportTemplateService;

    @PostMapping
    @Operation(summary = "Creates a new Report template.")
    public ResponseEntity<ReportTemplateResponseDto> create(
            @Valid @RequestBody ReportTemplateRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        ReportTemplateResponseDto response = reportTemplateService.create(requestDto);
        var uri = uriBuilder.path("/report_templates/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Report template by ID.")
    public ResponseEntity<ReportTemplateResponseDto> getById(
            @Parameter(description = "The ID of the Report template.")
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(reportTemplateService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Gets all Report templates or filters by optional framework and status query parameters.")
    public ResponseEntity<List<ReportTemplateResponseDto>> getAll(
            @RequestParam(name = "framework", required = false) Framework framework,
            @RequestParam(name = "status", required = false) TemplateStatus status) {

        if (framework != null && status != null) {
            return ResponseEntity.ok(reportTemplateService.getByFrameworkAndStatus(framework, status));
        } else if (framework != null) {
            return ResponseEntity.ok(reportTemplateService.getByFramework(framework));
        } else if (status != null) {
            return ResponseEntity.ok(reportTemplateService.getByTemplateStatus(status));
        }

        return ResponseEntity.ok(reportTemplateService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing Report template.")
    public ResponseEntity<ReportTemplateResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReportTemplateRequest request) {

        return ResponseEntity.ok(reportTemplateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Report template by ID.")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reportTemplateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
