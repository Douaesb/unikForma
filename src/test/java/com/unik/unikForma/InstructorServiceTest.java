package com.unik.unikForma;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.entity.Instructor;
import com.unik.unikForma.exception.InstructorNotFoundException;
import com.unik.unikForma.mapper.InstructorMapper;
import com.unik.unikForma.repository.InstructorRepository;
import com.unik.unikForma.service.InstructorService;
import lombok.var;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private InstructorMapper instructorMapper;

    @InjectMocks
    private InstructorService instructorService;

    private Instructor instructor;
    private InstructorDTO instructorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstName("John");
        instructor.setLastName("Doe");
        instructor.setEmail("john.doe@example.com");
        instructor.setSpeciality("Mathematics");

        instructorDTO = new InstructorDTO();
        instructorDTO.setId(1L);
        instructorDTO.setFirstName("John");
        instructorDTO.setLastName("Doe");
        instructorDTO.setEmail("john.doe@example.com");
        instructorDTO.setSpeciality("Mathematics");
    }

    @Test
    void saveInstructor_ShouldReturnInstructorDTO() {
        when(instructorMapper.toEntity(instructorDTO)).thenReturn(instructor);
        when(instructorRepository.save(instructor)).thenReturn(instructor);
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        InstructorDTO result = instructorService.saveInstructor(instructorDTO);

        assertNotNull(result);
        assertEquals(instructorDTO.getEmail(), result.getEmail());
        verify(instructorMapper).toEntity(instructorDTO);
        verify(instructorRepository).save(instructor);
        verify(instructorMapper).toDTO(instructor);
    }

    @Test
    void getAllInstructors_ShouldReturnListOfInstructorDTOs() {
        when(instructorRepository.findAll()).thenReturn(Collections.singletonList(instructor));
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        var result = instructorService.getAllInstructors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(instructorDTO.getEmail(), result.get(0).getEmail());
        verify(instructorRepository).findAll();
    }

    @Test
    void getInstructorById_ShouldReturnInstructorDTO() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(instructorMapper.toDTO(instructor)).thenReturn(instructorDTO);

        InstructorDTO result = instructorService.getInstructorById(1L);

        assertNotNull(result);
        assertEquals(instructorDTO.getEmail(), result.getEmail());
        verify(instructorRepository).findById(1L);
    }

    @Test
    void getInstructorById_ShouldThrowInstructorNotFoundException() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InstructorNotFoundException.class, () -> instructorService.getInstructorById(1L));
        verify(instructorRepository).findById(1L);
    }

    @Test
    void deleteInstructor_ShouldDeleteInstructor() {
        when(instructorRepository.existsById(1L)).thenReturn(true);

        instructorService.deleteInstructor(1L);

        verify(instructorRepository).deleteById(1L);
    }

    @Test
    void deleteInstructor_ShouldThrowInstructorNotFoundException() {
        when(instructorRepository.existsById(1L)).thenReturn(false);

        assertThrows(InstructorNotFoundException.class, () -> instructorService.deleteInstructor(1L));
        verify(instructorRepository, never()).deleteById(any());
    }

    @Test
    void updateInstructor_ShouldReturnUpdatedInstructorDTO() {
        Instructor updatedInstructor = new Instructor();
        updatedInstructor.setId(1L);
        updatedInstructor.setFirstName("John");
        updatedInstructor.setLastName("Doe");
        updatedInstructor.setEmail("john.doe@example.com");
        updatedInstructor.setSpeciality("Advanced Mathematics");

        InstructorDTO updatedInstructorDTO = new InstructorDTO();
        updatedInstructorDTO.setId(1L);
        updatedInstructorDTO.setFirstName("John");
        updatedInstructorDTO.setLastName("Doe");
        updatedInstructorDTO.setEmail("john.doe@example.com");
        updatedInstructorDTO.setSpeciality("Advanced Mathematics");

        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        doNothing().when(instructorMapper).updateEntityFromDTO(updatedInstructorDTO, instructor);
        when(instructorRepository.save(instructor)).thenReturn(updatedInstructor);
        when(instructorMapper.toDTO(updatedInstructor)).thenReturn(updatedInstructorDTO);

        InstructorDTO result = instructorService.updateInstructor(1L, updatedInstructorDTO);

        assertNotNull(result);
        assertEquals(updatedInstructorDTO.getSpeciality(), result.getSpeciality());
        verify(instructorRepository).findById(1L);
        verify(instructorMapper).updateEntityFromDTO(updatedInstructorDTO, instructor);
        verify(instructorRepository).save(instructor);
        verify(instructorMapper).toDTO(updatedInstructor);
    }

    @Test
    void updateInstructor_ShouldThrowInstructorNotFoundException() {
        when(instructorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InstructorNotFoundException.class, () -> instructorService.updateInstructor(1L, instructorDTO));
        verify(instructorRepository).findById(1L);
    }
}
