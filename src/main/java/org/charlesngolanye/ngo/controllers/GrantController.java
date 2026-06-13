package org.charlesngolanye.ngo.controllers;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.GrantDto;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.mappers.GrantMapper;
import org.charlesngolanye.ngo.services.GrantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grants")
public class GrantController {
    private final GrantService grantService;
    private final GrantMapper grantMapper;

    @PostMapping
    public Grant addGrant(@RequestBody Grant grant) {
       return grantService.addGrant(grant);
    }

    @GetMapping
    public List<GrantDto> getAllGrants() {
        return grantService.getAllGrants()
                .stream()
                .map(grantMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrantDto> getGrantById(@PathVariable Long id) {
        // Fetch domain entity from service layer using id
        Grant grant = grantService.getGrantById(id);

        // Convert entity into DTO format
        GrantDto grantDto = grantMapper.toDto(grant);

        // Return it wrapped in a 200 Ok HTTP response
        return ResponseEntity.ok(grantDto);
    }

}
