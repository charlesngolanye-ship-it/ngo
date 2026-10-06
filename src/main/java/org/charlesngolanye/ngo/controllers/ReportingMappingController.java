package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingMappingRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingMappingRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingMappingResponseDto;
import org.charlesngolanye.ngo.services.ReportingMappingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reporting_mappings")
@Tag(name = "reporting_mappings")
public class ReportingMappingController {
    private final ReportingMappingService reportingMappingService;

    @PostMapping
    @Operation(summary = "Creates a new Reporting mapping.")
    public ResponseEntity<ReportingMappingResponseDto> create
            (@Valid @RequestBody ReportingMappingRequestDto requestDto,
             UriComponentsBuilder uriBuilder) {
        ReportingMappingResponseDto response = reportingMappingService.create(requestDto);

        var uri = uriBuilder.path("/reporting_mappings/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Report mapping by ID.")
    public ResponseEntity<ReportingMappingResponseDto> getById (
            @Parameter(description = "The ID of Report mapping.")
            @PathVariable("id") Long id) {
        ReportingMappingResponseDto responseDto = reportingMappingService.getById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/template/{templateId}/category/{categoryId}")
    @Operation(summary = "Gets Report mapping by templateId and budgetId.")
    public ResponseEntity<ReportingMappingResponseDto> getByReportTemplateIdAndBudgetCategoryId (
            @PathVariable("templateId") Long reportTemplateId,
            @PathVariable("categoryId") Long budgetCategoryId) {
        ReportingMappingResponseDto responseDto = reportingMappingService.
                getByReportTemplateIdAndBudgetCategoryId(reportTemplateId, budgetCategoryId);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReportingMappingResponseDto>> getAll() {
        return ResponseEntity.ok(reportingMappingService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportingMappingResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReportingMappingRequest request) {

        ReportingMappingResponseDto updated = reportingMappingService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reportingMappingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
