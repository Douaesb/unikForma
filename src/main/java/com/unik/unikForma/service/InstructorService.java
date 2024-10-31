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

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;
    private final InstructorMapper instructorMapper;

    @Autowired
    public InstructorService(InstructorRepository instructorRepository, InstructorMapper instructorMapper) {
        this.instructorRepository = instructorRepository;
        this.instructorMapper = instructorMapper;
    }

    public InstructorDTO saveInstructor(InstructorDTO instructorDTO) {
        Instructor instructor = instructorMapper.toEntity(instructorDTO);

        Instructor savedInstructor = instructorRepository.save(instructor);
        return instructorMapper.toDTO(savedInstructor);
    }

    public List<InstructorDTO> getAllInstructors() {
        return instructorRepository.findAll().stream()
                .map(instructorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<InstructorDTO> getInstructorById(Long id) {
        return instructorRepository.findById(id)
                .map(instructorMapper::toDTO);
    }

    public void deleteInstructor(Long id) {
        if (!instructorRepository.existsById(id)) {
            throw new InstructorNotFoundException(id);
        }
        instructorRepository.deleteById(id);
    }
}
