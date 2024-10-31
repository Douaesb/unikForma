package com.unik.unikForma.controller;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/classes")
public class ClasseController {

    private final ClasseService classeService;

    @Autowired
    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    public ResponseEntity<ClasseDTO> create(@RequestBody ClasseDTO dto) {
        ClasseDTO createdEntity = classeService.saveClasse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDTO> getById(@PathVariable Long id) {
        return classeService.getClasseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<ClasseDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ClasseDTO> dtos = classeService.findAll(page, size);
        return ResponseEntity.ok(dtos);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ClasseDTO> update(
//            @PathVariable Long id, @RequestBody ClasseDTO updatedDTO) {
//        try {
//            ClasseDTO updated = classeService.update(id, updatedDTO);
//            return ResponseEntity.ok(updated);
//        } catch (ClasseNotFoundException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
