package com.unik.unikForma.service;

import com.unik.unikForma.entity.Learner;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearnerService {

    private final LearnerRepository learnerRepository;

    @Autowired
    public LearnerService(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    public Learner saveLearner(Learner learner) {
        return learnerRepository.save(learner);
    }

    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    public Optional<Learner> getLearnerById(Long id) {
        return learnerRepository.findById(id);
    }

    public void deleteLearner(Long id) {
        if (!learnerRepository.existsById(id)) {
            throw new LearnerNotFoundException(id);
        }
        learnerRepository.deleteById(id);
    }
}
