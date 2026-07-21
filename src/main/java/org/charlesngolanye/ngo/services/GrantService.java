package org.charlesngolanye.ngo.services;

import lombok.RequiredArgsConstructor;
import org.charlesngolanye.ngo.dtos.requestDtos.GrantRequestDto;
import org.charlesngolanye.ngo.dtos.responseDtos.GrantResponseDto;
import org.charlesngolanye.ngo.dtos.requestDtos.UpdateGrantRequest;
import org.charlesngolanye.ngo.entities.Grant;
import org.charlesngolanye.ngo.exceptions.GrantNotFoundException;
import org.charlesngolanye.ngo.mappers.GrantMapper;
import org.charlesngolanye.ngo.repositories.GrantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor// only entities with all args + no args
public class GrantService{
    private final GrantRepository grantRepository;
    private final GrantMapper grantMapper;

    public GrantResponseDto addGrant(GrantRequestDto grantRequestDto){
        Grant grant = grantMapper.toEntity(grantRequestDto);

        validateGrantDates(grant);

        Grant savedGrant = grantRepository.save(grant);
        return grantMapper.toDto(savedGrant);
    }

    public List<GrantResponseDto> getAllGrants() {
        return grantRepository.findAll()
                .stream()
                .map(grantMapper::toDto)
                .toList();
    }

    public GrantResponseDto getGrantById(Long id) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

        return grantMapper.toDto(grant);
    }

    @Transactional
    public GrantResponseDto updateGrant(Long id, UpdateGrantRequest request) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));
        grantMapper.update(request, grant);
        validateGrantDates(grant); // Ensure dates are still valid after client updates them

        return grantMapper.toDto(grantRepository.save(grant));
    }

    public void deleteGrant(Long id) {
        Grant grant = grantRepository.findById(id)
                .orElseThrow(() -> new GrantNotFoundException("Grant not found"));

       grantRepository.delete(grant);
    }

    private void validateGrantDates(Grant grant) {
        if (grant.getEndDate() != null && grant.getStartDate() != null
            && grant.getEndDate().isBefore(grant.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }
}
/*
 *  The Service layer only accepts and returns Entities. It focuses 100% on business domain rules and DB transactions
 *  Stays completely decoupled from HTTP representations
 */

/*
public GrantResponseDto addGrant(GrantRequestDto grantRequestDto) {

    Map the request to an entity
        Grant grant = new Grant();
        grant.setGrantNumber(grantRequestDto.getGrantNumber());
        grant.setGrantName(grantRequestDto.getGrantName());
        grant.setStartDate(grantRequestDto.getStartDate());
        grant.setEndDate(grantRequestDto.getEndDate());
        grant.setDonorName(grantRequestDto.getDonorName());





     map the grant/ entity to a responseDto

        GrantResponseDto response = new GrantResponseDto();
        response.setGrantName(grant.getGrantName());
        response.setGrantNumber(grant.getGrantNumber());
        response.setStartDate(grant.getStartDate());
        response.setEndDate(grant.getEndDate());
        response.setDonorName(grant.getDonorName());


     return the responseDto
}

 */
