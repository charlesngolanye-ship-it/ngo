package org.charlesngolanye.ngo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.GrantResponseDto;
import org.charlesngolanye.ngo.dtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.services.GrantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grants")
public class GrantController {
    private final GrantService grantService;

    @PostMapping
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
    public ResponseEntity<GrantResponseDto> getGrantById(@PathVariable Long id) {
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

/*
 *  The Controller acts as the API translator.
 *  Intercepts incoming DTOs, leverages the Mapper to turn them into domain Entities, calls the Service layer
 *  , and maps the output back into an outgoing DTO
 */

/*
    @PostMapping
    public ResponseEntity<GrantResponseDto> addGrant(
            @RequestBody GrantRequestDto requestDto,
            UriComponentsBuilder uriBuilder) {
        Grant grant = grantMapper.toEntity(requestDto);
        Grant savedGrant = grantService.addGrant(grant);
        GrantResponseDto responseDto = grantMapper.toDto(savedGrant);
        var uri = uriBuilder.path("/grants/{id}").buildAndExpand(responseDto.getId()).toUri();

        //return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        return ResponseEntity.created(uri).body(responseDto); // to get a 201 Created response...the location of the new created resource eg http://localhost:8080/grants/5
    }

 */