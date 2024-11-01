package com.unik.unikForma.service;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.entity.Instructor;
import com.unik.unikForma.exception.InstructorNotFoundException;
import com.unik.unikForma.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.unik.unikForma.mapper.InstructorMapper;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    public InstructorDTO saveInstructor(@Valid InstructorDTO instructorDTO) {
        Instructor instructor = instructorMapper.toEntity(instructorDTO);
        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.toDTO(savedInstructor);
    }

    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public InstructorDTO getInstructorById(Long id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorNotFoundException(id));
        return instructorMapper.toDTO(instructor);
    }

    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new InstructorNotFoundException(id);
        }
        instructorRepository.deleteById(id);
    }

    public InstructorDTO updateInstructor(Long id, @Valid InstructorDTO updatedInstructorDTO) {
        Instructor existingInstructor = instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorNotFoundException(id));
        instructorMapper.updateEntityFromDTO(updatedInstructorDTO, existingInstructor);
        Instructor updatedInstructor = instructorRepository.save(existingInstructor);
        return instructorMapper.toDTO(updatedInstructor);
    }
}
