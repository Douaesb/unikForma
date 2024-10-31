package com.unik.unikForma.service;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.entity.Classe;
import com.unik.unikForma.exception.CourseNotFoundException;
import com.unik.unikForma.mapper.ClasseMapper; // Import the ClasseMapper
import com.unik.unikForma.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;

    @Autowired
    public ClasseService(ClasseRepository classeRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    public ClasseDTO saveClasse(ClasseDTO classeDTO) {
        Classe classe = classeMapper.toEntity(classeDTO);
        Classe savedClasse = classeRepository.save(classe);
        return classeMapper.toDTO(savedClasse);
    }

    public Page<ClasseDTO> findAll(int page, int size) {
        return classeRepository.findAll(PageRequest.of(page, size))
                .map(classeMapper::toDTO);
    }

    public Optional<ClasseDTO> getClasseById(Long id) {
        return classeRepository.findById(id)
                .map(classeMapper::toDTO);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

    public ClasseDTO updateClasse(Long id, ClasseDTO updatedClasseDTO) {
        Classe existingClasse = classeRepository.findById(id).orElse(null);

        classeMapper.updateEntityFromDTO(updatedClasseDTO, existingClasse);
        Classe updatedClasse = classeRepository.save(existingClasse);
        return classeMapper.toDTO(updatedClasse);
    }

}
