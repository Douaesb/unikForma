package com.unik.unikForma.controller;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<InstructorDTO> createInstructor(@RequestBody InstructorDTO instructorDTO) {
        InstructorDTO savedInstructorDTO = instructorService.saveInstructor(instructorDTO);
        return ResponseEntity.ok(savedInstructorDTO);
    }

    @GetMapping
    public List<InstructorDTO> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> getInstructorById(@PathVariable Long id) {
        return instructorService.getInstructorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable Long id) {
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> updateInstructor(
            @PathVariable Long id, @Valid @RequestBody InstructorDTO updatedInstructorDTO) {
        InstructorDTO updatedInstructor = instructorService.updateInstructor(id, updatedInstructorDTO);
        return ResponseEntity.ok(updatedInstructor);
    }
}
