package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.GrantResponseDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.mappers.GrantMapper;
import org.charlesngolanye.ngo.services.GrantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grants")
public class GrantController {
    private final GrantService grantService;
    private final GrantMapper grantMapper;

//    @PostMapping
//    public Grant addGrant(@RequestBody Grant grant) {
//       return grantService.addGrant(grant);
//    }

    @PostMapping
    public ResponseEntity<GrantResponseDto> addGrant(@RequestBody GrantRequestDto requestDto) {
        Grant grant = grantMapper.toEntity(requestDto);
        Grant savedGrant = grantService.addGrant(grant);
        GrantResponseDto responseDto = grantMapper.toDto(savedGrant);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public List<GrantResponseDto> getAllGrants() {
        return grantService.getAllGrants()
                .stream()
                .map(grantMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantResponseDto> getGrantById(@PathVariable Long id) {
        // Fetch domain entity from service layer using id
        Grant grant = grantService.getGrantById(id);

        // Convert entity into DTO format
        GrantResponseDto grantResponseDto = grantMapper.toDto(grant);

        // Return it wrapped in a 200 Ok HTTP response
        return ResponseEntity.ok(grantResponseDto);
    }

}

/*
 *  The Controller acts as the API translator.
 *  Intercepts incoming DTOs, leverages the Mapper to turn them into domain Entities, calls the Service layer
 *  , and maps the output back into an outgoing DTO
 */
