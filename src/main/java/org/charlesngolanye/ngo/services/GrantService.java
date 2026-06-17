package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor// only entities with all args + no args
public class GrantService{

    private final GrantRepository grantRepository;

    public Grant addGrant(Grant grant){

        if (grant.getEndDate().isBefore(grant.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        if(grant.getGrantNumber() == null) {
            throw new IllegalArgumentException("Grant Number is required");
        }
        return grantRepository.save(grant);
    }

    public List<Grant> getAllGrants() {
        return grantRepository.findAll();
    }

    public Grant getGrantById(Long id) {
        return grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));
    }

}
/*
 *  The Service layer only accepts and returns Entities. It focuses 100% on business domain rules and DB transactions
 *  Stays completely decoupled from HTTP representations
 */
