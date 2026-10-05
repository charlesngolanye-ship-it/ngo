package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingPeriodRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingPeriodRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingPeriodResponseDto;
import org.charlesngolanye.ngo.services.ReportingPeriodService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reporting_periods")
@Tag(name = "reporting_periods")
public class ReportingPeriodController {
    private final ReportingPeriodService reportingPeriodService;

    @PostMapping
    @Operation(summary = "Creates a new Reporting period.")
    public ResponseEntity<ReportingPeriodResponseDto> create(
            @Valid @RequestBody ReportingPeriodRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        ReportingPeriodResponseDto response = reportingPeriodService.create(requestDto);
        var uri = uriBuilder.path("/reporting_periods/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Reporting period by ID.")
    public ResponseEntity<ReportingPeriodResponseDto> getById(
            @Parameter(description = "The ID of the Reporting period.")
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(reportingPeriodService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Gets all Reporting periods.")
    public ResponseEntity<List<ReportingPeriodResponseDto>> getAll() {
        return ResponseEntity.ok(reportingPeriodService.getAll());
    }

    @GetMapping("/range")
    @Operation(summary = "Gets Reporting periods within a date range.")
    public ResponseEntity<List<ReportingPeriodResponseDto>> getByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(reportingPeriodService.getByDateRange(startDate, endDate));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing Reporting period.")
    public ResponseEntity<ReportingPeriodResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReportingPeriodRequest request) {

        return ResponseEntity.ok(reportingPeriodService.update(id, request));
    }

    @PatchMapping("/{id}/close")
    @Operation(summary = "Closes an active Reporting period.")
    public ResponseEntity<Void> close(
            @Parameter(description = "The ID of the Reporting period to close.")
            @PathVariable("id") Long id) {

        reportingPeriodService.close(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Reporting period by ID.")
    public ResponseEntity<Void> delete(
            @Parameter(description = "The ID of the Reporting period to delete.")
            @PathVariable("id") Long id) {

        reportingPeriodService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
