package org.charlesngolanye.ngo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GrantResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.services.GrantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grants")
@Tag(name = "Grants")
public class GrantController {
    private final GrantService grantService;

    @PostMapping
    @Operation(summary = "Creates a new Grant.")
    public ResponseEntity<GrantResponseDto> createGrant
            (@Valid @RequestBody GrantRequestDto requestDto,
             UriComponentsBuilder uriBuilder) {
       GrantResponseDto response = grantService.addGrant(requestDto);

       var uri = uriBuilder.path("/grants/{id}").buildAndExpand(response.getId()).toUri();
       return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GrantResponseDto>> getAllGrants() {
        return ResponseEntity.ok(grantService.getAllGrants());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets a Grant by ID.")
    public ResponseEntity<GrantResponseDto> getGrantById(
            @Parameter(description = "The ID of the Grant.")
            @PathVariable Long id) {
        GrantResponseDto responseDto = grantService.getGrantById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrantResponseDto> updateGrant(
            @PathVariable (name= "id") Long id,
            @RequestBody UpdateGrantRequest request) {

        GrantResponseDto updated = grantService.updateGrant(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrant(@PathVariable Long id) {
        grantService.deleteGrant(id);
        return ResponseEntity.noContent().build();
    }

}