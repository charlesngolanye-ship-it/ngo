package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateReportingCodeRequest;
import org.charlesngolanye.ngo.dtos.requestDtos.ReportingCodeRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.ReportingCodeResponseDto;
import org.charlesngolanye.ngo.entities.Framework;
import org.charlesngolanye.ngo.services.ReportingCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reporting_codes")
@Tag(name = "reporting_codes")
public class ReportingCodeController {
    private final ReportingCodeService reportingCodeService;

    @PostMapping
    @Operation(summary = "Creates a new Reporting code.")
    public ResponseEntity<ReportingCodeResponseDto> create
            (@Valid @RequestBody ReportingCodeRequestDto requestDto,
             UriComponentsBuilder uriBuilder) {
        ReportingCodeResponseDto response = reportingCodeService.create(requestDto);

        var uri = uriBuilder.path("/reporting_codes/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets Reporting code by ID.")
    public ResponseEntity<ReportingCodeResponseDto> getById (
            @Parameter(description = "The ID of Reporting code.")
            @PathVariable("id") Long id) {
        ReportingCodeResponseDto responseDto = reportingCodeService.getById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/framework/{framework}")
    @Operation(summary = "Gets Reporting code by framework.")
    public ResponseEntity<List<ReportingCodeResponseDto>> getByFramework (
            @Parameter(description = "Framework enum value.")
            @PathVariable("framework") Framework framework) {
        List<ReportingCodeResponseDto>  responseDto = reportingCodeService.getByFramework(framework);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ReportingCodeResponseDto>> getAll() {
        return ResponseEntity.ok(reportingCodeService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportingCodeResponseDto> update(
            @PathVariable("id") Long id,
           @Valid @RequestBody UpdateReportingCodeRequest request) {

        ReportingCodeResponseDto updated = reportingCodeService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        reportingCodeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
