package com.unik.unikForma;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.entity.Learner;
import com.unik.unikForma.exception.LearnerNotFoundException;
import com.unik.unikForma.mapper.LearnerMapper;
import com.unik.unikForma.repository.LearnerRepository;
import com.unik.unikForma.service.LearnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class LearnerServiceTest {

    @InjectMocks
    private LearnerService learnerService;

    @Mock
    private LearnerRepository learnerRepository;

    @Mock
    private LearnerMapper learnerMapper;

    private LearnerDTO learnerDTO;
    private Learner learner;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        learnerDTO = new LearnerDTO();
        learnerDTO.setId(1L);
        learnerDTO.setFirstName("John");
        learnerDTO.setLastName("Doe");
        learnerDTO.setEmail("john.doe@example.com");
        learnerDTO.setLevel("Beginner");
        learnerDTO.setClasseId(null);

        learner = new Learner();
        learner.setId(1L);
        learner.setFirstName("John");
        learner.setLastName("Doe");
        learner.setEmail("john.doe@example.com");
        learner.setCourse(null);
    }

    @Test
    void saveLearner_ShouldReturnSavedLearnerDTO() {
        when(learnerMapper.toEntity(any(LearnerDTO.class))).thenReturn(learner);
        when(learnerRepository.save(any(Learner.class))).thenReturn(learner);
        when(learnerMapper.toDTO(any(Learner.class))).thenReturn(learnerDTO);

        LearnerDTO savedLearnerDTO = learnerService.saveLearner(learnerDTO);

        assertNotNull(savedLearnerDTO);
        assertEquals(learnerDTO.getFirstName(), savedLearnerDTO.getFirstName());
        verify(learnerRepository, times(1)).save(any(Learner.class));
    }

    @Test
    void getAllLearners_ShouldReturnListOfLearnerDTOs() {
        when(learnerRepository.findAll()).thenReturn(Arrays.asList(learner));
        when(learnerMapper.toDTO(any(Learner.class))).thenReturn(learnerDTO);

        List<LearnerDTO> learnerDTOs = learnerService.getAllLearners();

        assertNotNull(learnerDTOs);
        assertEquals(1, learnerDTOs.size());
        assertEquals(learnerDTO.getFirstName(), learnerDTOs.get(0).getFirstName());
        verify(learnerRepository, times(1)).findAll();
    }

    @Test
    void getLearnerById_ShouldReturnLearnerDTO() {
        when(learnerRepository.findById(anyLong())).thenReturn(Optional.of(learner));
        when(learnerMapper.toDTO(any(Learner.class))).thenReturn(learnerDTO);

        LearnerDTO foundLearnerDTO = learnerService.getLearnerById(1L);

        assertNotNull(foundLearnerDTO);
        assertEquals(learnerDTO.getFirstName(), foundLearnerDTO.getFirstName());
        verify(learnerRepository, times(1)).findById(anyLong());
    }

    @Test
    void getLearnerById_ShouldThrowLearnerNotFoundException() {
        when(learnerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearnerNotFoundException.class, () -> learnerService.getLearnerById(1L));
        verify(learnerRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteLearner_ShouldDeleteLearner() {
        when(learnerRepository.existsById(anyLong())).thenReturn(true);

        learnerService.deleteLearner(1L);

        verify(learnerRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteLearner_ShouldThrowLearnerNotFoundException() {
        when(learnerRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(LearnerNotFoundException.class, () -> learnerService.deleteLearner(1L));
        verify(learnerRepository, never()).deleteById(anyLong());
    }


    @Test
    void updateLearner_ShouldReturnUpdatedLearnerDTO() {
        // Arrange
        Learner updatedLearner = new Learner();
        updatedLearner.setId(1L);
        updatedLearner.setFirstName("Alice");
        updatedLearner.setLastName("Smith");
        updatedLearner.setEmail("alice.smith@example.com");
        updatedLearner.setLevel("Advanced");

        LearnerDTO updatedLearnerDTO = new LearnerDTO();
        updatedLearnerDTO.setId(1L);
        updatedLearnerDTO.setFirstName("Alice");
        updatedLearnerDTO.setLastName("Smith");
        updatedLearnerDTO.setEmail("alice.smith@example.com");
        updatedLearnerDTO.setLevel("Advanced");

        when(learnerRepository.findById(1L)).thenReturn(Optional.of(learner));
        doNothing().when(learnerMapper).updateEntityFromDTO(updatedLearnerDTO, learner);
        when(learnerRepository.save(learner)).thenReturn(updatedLearner);
        when(learnerMapper.toDTO(updatedLearner)).thenReturn(updatedLearnerDTO);

        // Act
        LearnerDTO result = learnerService.updateLearner(1L, updatedLearnerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedLearnerDTO.getLevel(), result.getLevel());
        verify(learnerRepository).findById(1L);
        verify(learnerMapper).updateEntityFromDTO(updatedLearnerDTO, learner);
        verify(learnerRepository).save(learner);
        verify(learnerMapper).toDTO(updatedLearner);
    }

    @Test
    void updateLearner_ShouldThrowLearnerNotFoundException() {
        when(learnerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(LearnerNotFoundException.class, () -> learnerService.updateLearner(1L, learnerDTO));
        verify(learnerRepository, never()).save(any(Learner.class));
    }
}
