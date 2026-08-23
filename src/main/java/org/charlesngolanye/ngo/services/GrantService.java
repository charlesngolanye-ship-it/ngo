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
@Transactional
public class GrantService{
    private final GrantRepository grantRepository;
    private final GrantMapper grantMapper;

    public GrantResponseDto addGrant(GrantRequestDto grantRequestDto){
        Grant grant = grantMapper.toEntity(grantRequestDto);

        validateGrantDates(grant);

        Grant savedGrant = grantRepository.save(grant);
        return grantMapper.toDto(savedGrant);
    }

    @Transactional(readOnly = true)
    public List<GrantResponseDto> getAllGrants() {
        return grantRepository.findAll()
                .stream()
                .map(grantMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
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
