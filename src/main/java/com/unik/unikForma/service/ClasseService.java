package com.unik.unikForma.service;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.entity.Classe;
import com.unik.unikForma.repository.ClasseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClasseService {

    private final ClasseRepository classeRepository;

    @Autowired
    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public ClasseDTO saveClasse(ClasseDTO classeDTO) {
        Classe classe = convertToEntity(classeDTO);
        Classe savedClasse = classeRepository.save(classe);
        return convertToDTO(savedClasse);
    }

    public Page<ClasseDTO> findAll(int page, int size) {
        return classeRepository.findAll(PageRequest.of(page, size))
                .map(this::convertToDTO); // Convert each entity to DTO
    }

    public Optional<ClasseDTO> getClasseById(Long id) {
        return classeRepository.findById(id)
                .map(this::convertToDTO); // Convert the entity to DTO
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }

    public Optional<ClasseDTO> update(Long id, ClasseDTO updatedClasseDTO) {
        return classeRepository.findById(id).map(existingClasse -> {
            existingClasse.setName(updatedClasseDTO.getName());
            existingClasse.setRoomNum(updatedClasseDTO.getRoomNum());
            // Update the instructor if needed
            return classeRepository.save(existingClasse);
        }).map(this::convertToDTO); // Convert updated entity to DTO
    }

    // Utility methods for conversion
    private Classe convertToEntity(ClasseDTO classeDTO) {
        Classe classe = new Classe();
        classe.setId(classeDTO.getId());
        classe.setName(classeDTO.getName());
        classe.setRoomNum(classeDTO.getRoomNum());
        // Set instructor if needed
        return classe;
    }

    private ClasseDTO convertToDTO(Classe classe) {
        ClasseDTO dto = new ClasseDTO();
        dto.setId(classe.getId());
        dto.setName(classe.getName());
        dto.setRoomNum(classe.getRoomNum());
        // Set instructor DTO if needed
        return dto;
    }
}
