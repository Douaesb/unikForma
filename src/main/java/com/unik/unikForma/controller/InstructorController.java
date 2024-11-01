package com.unik.unikForma.controller;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.service.InstructorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorController {

    private final InstructorService instructorService;

    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @PostMapping
    @Operation(summary = "Create a new instructor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instructor created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid instructor data")
    })
    public ResponseEntity<InstructorDTO> createInstructor(
            @Parameter(description = "Instructor details for creation", required = true) @Valid @RequestBody InstructorDTO instructorDTO) {
        InstructorDTO savedInstructorDTO = instructorService.saveInstructor(instructorDTO);
        return ResponseEntity.ok(savedInstructorDTO);
    }

    @GetMapping
    @Operation(summary = "Retrieve all instructors")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instructors retrieved successfully")
    })
    public ResponseEntity<List<InstructorDTO>> getAllInstructors() {
        List<InstructorDTO> instructors = instructorService.getAllInstructors();
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get instructor by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instructor retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Instructor not found")
    })
    public ResponseEntity<InstructorDTO> getInstructorById(
            @Parameter(description = "ID of the instructor to retrieve", required = true) @PathVariable Long id) {
        InstructorDTO instructorDTO = instructorService.getInstructorById(id);
        return ResponseEntity.ok(instructorDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an instructor by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Instructor deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Instructor not found")
    })
    public ResponseEntity<Void> deleteInstructor(
            @Parameter(description = "ID of the instructor to delete", required = true) @PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an instructor by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Instructor updated successfully"),
            @ApiResponse(responseCode = "404", description = "Instructor not found"),
            @ApiResponse(responseCode = "400", description = "Invalid instructor data")
    })
    public ResponseEntity<InstructorDTO> updateInstructor(
            @Parameter(description = "ID of the instructor to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated instructor details", required = true) @Valid @RequestBody InstructorDTO updatedInstructorDTO) {
        InstructorDTO updatedInstructor = instructorService.updateInstructor(id, updatedInstructorDTO);
        return ResponseEntity.ok(updatedInstructor);
    }
}
