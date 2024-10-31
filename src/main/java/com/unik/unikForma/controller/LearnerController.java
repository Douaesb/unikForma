package com.unik.unikForma.controller;

import com.unik.unikForma.entity.Learner;
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
    public ResponseEntity<Learner> createLearner(@RequestBody Learner learner) {
        Learner savedLearner = learnerService.saveLearner(learner);
        return ResponseEntity.ok(savedLearner);
    }

    @GetMapping
    public List<Learner> getAllLearners() {
        return learnerService.getAllLearners();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Learner> getLearnerById(@PathVariable Long id) {
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
