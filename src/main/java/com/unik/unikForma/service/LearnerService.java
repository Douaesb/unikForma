package com.unik.unikForma.service;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.entity.Learner;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    public LearnerDTO saveLearner(LearnerDTO learnerDTO) {
        Learner learner = new Learner();
        // Map properties from DTO to Entity
        learner.setFirstName(learnerDTO.getFirstName());
        learner.setLastName(learnerDTO.getLastName());
        learner.setEmail(learnerDTO.getEmail());
        learner.setLevel(learnerDTO.getLevel());
        // Save and return the created LearnerDTO
        Learner savedLearner = learnerRepository.save(learner);
        return mapToDTO(savedLearner);
    }

    public List<LearnerDTO> getAllLearners() {
        return learnerRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LearnerDTO> getLearnerById(Long id) {
        return learnerRepository.findById(id)
                .map(this::mapToDTO);
    }

    public void deleteLearner(Long id) {
        if (!learnerRepository.existsById(id)) {
            throw new LearnerNotFoundException(id);
        }
        learnerRepository.deleteById(id);
    }

    private LearnerDTO mapToDTO(Learner learner) {
        LearnerDTO dto = new LearnerDTO();
        dto.setId(learner.getId());
        dto.setFirstName(learner.getFirstName());
        dto.setLastName(learner.getLastName());
        dto.setEmail(learner.getEmail());
        dto.setLevel(learner.getLevel());
        return dto;
    }
}
