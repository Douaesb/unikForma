package com.unik.unikForma.controller;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.service.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    @Operation(summary = "Create a new class")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Class created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid class data")
    })
    public ResponseEntity<ClasseDTO> create(
            @Parameter(description = "Class details to create", required = true) @Valid @RequestBody ClasseDTO dto) {
        ClasseDTO createdEntity = classeService.saveClasse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get class by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Class not found")
    })
    public ResponseEntity<ClasseDTO> getById(
            @Parameter(description = "ID of the class to retrieve", required = true) @PathVariable Long id) {
        return classeService.getClasseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get all classes with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes retrieved successfully")
    })
    public ResponseEntity<Page<ClasseDTO>> getAll(
            @Parameter(description = "Page number", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        Page<ClasseDTO> dtos = classeService.findAll(page, size);
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a class by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Class deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Class not found")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the class to delete", required = true) @PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a class by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Class updated successfully"),
            @ApiResponse(responseCode = "404", description = "Class not found"),
            @ApiResponse(responseCode = "400", description = "Invalid class data")
    })
    public ResponseEntity<ClasseDTO> updateClasse(
            @Parameter(description = "ID of the class to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated class details", required = true) @Valid @RequestBody ClasseDTO updatedClasseDTO) {
        ClasseDTO updatedClasse = classeService.updateClasse(id, updatedClasseDTO);
        return ResponseEntity.ok(updatedClasse);
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search classes by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Classes retrieved successfully")
    })
    public ResponseEntity<List<ClasseDTO>> getClassesByName(
            @Parameter(description = "Name of the class to search for", required = true) @RequestParam String name) {
        List<ClasseDTO> classes = classeService.getClassesByName(name);
        return ResponseEntity.ok(classes);
    }
}
