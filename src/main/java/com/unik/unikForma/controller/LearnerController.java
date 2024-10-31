package com.unik.unikForma.controller;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.service.LearnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learners")
public class LearnerController {

    private final LearnerService learnerService;

    @Autowired
    public LearnerController(LearnerService learnerService) {
        this.learnerService = learnerService;
    }

    @PostMapping
    public ResponseEntity<LearnerDTO> createLearner(@RequestBody LearnerDTO learnerDTO) {
        // Use the service to save the learner and return the DTO
        LearnerDTO savedLearnerDTO = learnerService.saveLearner(learnerDTO);
        return ResponseEntity.ok(savedLearnerDTO);
    }

    @GetMapping
    public List<LearnerDTO> getAllLearners() {
        return learnerService.getAllLearners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearnerDTO> getLearnerById(@PathVariable Long id) {
        return learnerService.getLearnerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLearner(@PathVariable Long id) {
        learnerService.deleteLearner(id);
        return ResponseEntity.noContent().build();
    }
}
