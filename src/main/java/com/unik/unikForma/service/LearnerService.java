package com.unik.unikForma.service;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.entity.Learner;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.mapper.LearnerMapper;
import com.unik.unikForma.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class LearnerService {

    private final LearnerRepository learnerRepository;
    private final LearnerMapper learnerMapper;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository, LearnerMapper learnerMapper) {
        this.learnerRepository = learnerRepository;
        this.learnerMapper = learnerMapper;
    }

    public LearnerDTO saveLearner(@Valid LearnerDTO learnerDTO) {
        Learner learner = learnerMapper.toEntity(learnerDTO);
        Learner savedLearner = learnerRepository.save(learner);
        return learnerMapper.toDTO(savedLearner);
    }

    public List<LearnerDTO> getAllLearners() {
        return learnerRepository.findAll().stream()
                .map(learnerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LearnerDTO getLearnerById(Long id) {
        Learner learner = learnerRepository.findById(id)
                .orElseThrow(() -> new LearnerNotFoundException(id));
        return learnerMapper.toDTO(learner);
    }

    public void deleteLearner(Long id) {
        if (!learnerRepository.existsById(id)) {
            throw new LearnerNotFoundException(id);
        }
        learnerRepository.deleteById(id);
    }

    public LearnerDTO updateLearner(Long id, @Valid LearnerDTO updatedLearnerDTO) {
        Learner existingLearner = learnerRepository.findById(id)
                .orElseThrow(() -> new LearnerNotFoundException(id));
        learnerMapper.updateEntityFromDTO(updatedLearnerDTO, existingLearner);
        Learner updatedLearner = learnerRepository.save(existingLearner);
        return learnerMapper.toDTO(updatedLearner);
    }
}
