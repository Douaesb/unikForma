package com.unik.unikForma.service;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.entity.Learner;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.mapper.LearnerMapper; // Import the LearnerMapper
import com.unik.unikForma.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;
    private final LearnerMapper learnerMapper;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository, LearnerMapper learnerMapper) {
        this.learnerRepository = learnerRepository;
        this.learnerMapper = learnerMapper;
    }

    public LearnerDTO saveLearner(LearnerDTO learnerDTO) {

        Learner learner = learnerMapper.toEntity(learnerDTO);
        Learner savedLearner = learnerRepository.save(learner);
        return learnerMapper.toDTO(savedLearner);
    }

    public List<LearnerDTO> getAllLearners() {
        return learnerRepository.findAll().stream()
                .map(learnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<LearnerDTO> getLearnerById(Long id) {
        return learnerRepository.findById(id)
                .map(learnerMapper::toDTO);
    }

    public void deleteLearner(Long id) {
        if (!learnerRepository.existsById(id)) {
            throw new LearnerNotFoundException(id);
        }
        learnerRepository.deleteById(id);
    }
}
