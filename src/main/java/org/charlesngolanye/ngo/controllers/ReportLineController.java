package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportLineRequestDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportLineRequest;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportLineResponseDto;
import org.charlesngolanye.ngo.services.ReportLineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report_lines")
@Tag(name = "report_lines")
public class ReportLineController {
    private final ReportLineService reportLineService;

    @PostMapping
    @Operation(summary = "Creates a new Report line.")
    public ResponseEntity<ReportLineResponseDto> create(
            @Valid @RequestBody ReportLineRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {

        ReportLineResponseDto response = reportLineService.create(requestDto);
        var uri = uriBuilder.path("/report_lines/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Report line by ID.")
    public ResponseEntity<ReportLineResponseDto> getById(
            @Parameter(description = "The ID of the Report line.")
            @PathVariable("id") Long id) {

        return ResponseEntity.ok(reportLineService.getById(id));
    }

    @GetMapping("/section/{sectionId}")
    @Operation(summary = "Gets Report lines by section ID ordered by display order.")
    public ResponseEntity<List<ReportLineResponseDto>> getBySectionId(
            @Parameter(description = "The ID of the Report section.")
            @PathVariable("sectionId") Long sectionId) {

        return ResponseEntity.ok(reportLineService.getBySectionId(sectionId));
    }

    @GetMapping
    @Operation(summary = "Gets all Report lines.")
    public ResponseEntity<List<ReportLineResponseDto>> getAll() {
        return ResponseEntity.ok(reportLineService.getAll());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an existing Report line.")
    public ResponseEntity<ReportLineResponseDto> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateReportLineRequest request) {

        return ResponseEntity.ok(reportLineService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Report line by ID.")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reportLineService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
