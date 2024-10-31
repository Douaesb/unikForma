package com.unik.unikForma.controller;

import com.unik.unikForma.entity.Classe;
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
    public ResponseEntity<Classe> create(@RequestBody Classe entity) {
        Classe createdEntity = classeService.saveClasse(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getById(@PathVariable Long id) {
        Optional<Classe> entity = classeService.getClasseById(id);
        return entity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<Classe>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Classe> entities = classeService.findAll(page, size);
        return ResponseEntity.ok(entities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> update(
            @PathVariable Long id, @RequestBody Classe updatedEntity) {
        Optional<Classe> updated = classeService.update(id, updatedEntity);
        return updated.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
}
