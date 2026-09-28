package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportSectionRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportSectionRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportSectionResponseDto;
import org.charlesngolanye.ngo.services.ReportSectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report_sections")
@Tag(name = "report_sections")
public class ReportSectionController {
    private final ReportSectionService reportSectionService;

    @PostMapping
    @Operation(summary = "Creates a new Report section.")
    public ResponseEntity<ReportSectionResponseDto> create(
            @Valid @RequestBody ReportSectionRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        ReportSectionResponseDto response = reportSectionService.create(requestDto);
        var uri = uriBuilder.path("/report_sections/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Report section by ID.")
    public ResponseEntity<ReportSectionResponseDto> getById(
            @Parameter(description = "The ID of the Report section.")
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(reportSectionService.getById(id));
    }

    @GetMapping("/template/{templateId}")
    @Operation(summary = "Gets Report sections by template ID ordered by display order.")
    public ResponseEntity<List<ReportSectionResponseDto>> getByTemplateId(
            @Parameter(description = "The ID of the Report template.")
            @PathVariable("templateId") Long templateId) {

        return ResponseEntity.ok(reportSectionService.getByTemplateId(templateId));
    }

    @GetMapping
    @Operation(summary = "Gets all Report sections.")
    public ResponseEntity<List<ReportSectionResponseDto>> getAll() {
        return ResponseEntity.ok(reportSectionService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing Report section.")
    public ResponseEntity<ReportSectionResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReportSectionRequest request) {

        return ResponseEntity.ok(reportSectionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Report section by ID.")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reportSectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
