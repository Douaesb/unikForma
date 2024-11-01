package com.unik.unikForma.controller;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.service.LearnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @Operation(summary = "Create a new learner")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Learner created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid learner data")
    })
    public ResponseEntity<LearnerDTO> createLearner(
            @Parameter(description = "Learner details for creation", required = true) @Valid @RequestBody LearnerDTO learnerDTO) {
        LearnerDTO savedLearnerDTO = learnerService.saveLearner(learnerDTO);
        return ResponseEntity.ok(savedLearnerDTO);
    }

    @GetMapping
    @Operation(summary = "Retrieve all learners")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Learners retrieved successfully")
    })
    public ResponseEntity<List<LearnerDTO>> getAllLearners() {
        List<LearnerDTO> learners = learnerService.getAllLearners();
        return ResponseEntity.ok(learners);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get learner by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Learner retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Learner not found")
    })
    public ResponseEntity<LearnerDTO> getLearnerById(
            @Parameter(description = "ID of the learner to retrieve", required = true) @PathVariable Long id) {
        LearnerDTO learnerDTO = learnerService.getLearnerById(id);
        return ResponseEntity.ok(learnerDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a learner by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Learner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Learner not found")
    })
    public ResponseEntity<Void> deleteLearner(
            @Parameter(description = "ID of the learner to delete", required = true) @PathVariable Long id) {
        try {
            learnerService.deleteLearner(id);
            return ResponseEntity.noContent().build();
        } catch (LearnerNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a learner by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Learner updated successfully"),
            @ApiResponse(responseCode = "404", description = "Learner not found"),
            @ApiResponse(responseCode = "400", description = "Invalid learner data")
    })
    public ResponseEntity<LearnerDTO> updateLearner(
            @Parameter(description = "ID of the learner to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated learner details", required = true) @Valid @RequestBody LearnerDTO updatedLearnerDTO) {
        LearnerDTO updatedLearner = learnerService.updateLearner(id, updatedLearnerDTO);
        return ResponseEntity.ok(updatedLearner);
    }
}
