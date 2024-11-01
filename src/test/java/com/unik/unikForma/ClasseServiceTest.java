package com.unik.unikForma;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.entity.Classe;
import com.unik.unikForma.exception.ClasseNotFoundException;
import com.unik.unikForma.mapper.ClasseMapper;
import com.unik.unikForma.repository.ClasseRepository;
import com.unik.unikForma.service.ClasseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClasseServiceTest {

    @InjectMocks
    private ClasseService classeService;

    @Mock
    private ClasseRepository classeRepository;

    @Mock
    private ClasseMapper classeMapper;

    private Classe classe;
    private ClasseDTO classeDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        classe = new Classe();
        classe.setId(1L);
        classe.setName("Math");
        classe.setRoomNum(101);
        classeDTO = new ClasseDTO(1L, "Math", 101);
    }

    @Test
    void saveClasse_ShouldReturnClasseDTO() {
        when(classeMapper.toEntity(classeDTO)).thenReturn(classe);
        when(classeRepository.save(classe)).thenReturn(classe);
        when(classeMapper.toDTO(classe)).thenReturn(classeDTO);

        ClasseDTO result = classeService.saveClasse(classeDTO);

        assertNotNull(result);
        assertEquals(classeDTO.getName(), result.getName());
        verify(classeMapper).toEntity(classeDTO);
        verify(classeRepository).save(classe);
        verify(classeMapper).toDTO(classe);
    }

    @Test
    void findAll_ShouldReturnPageOfClasseDTO() {
        Page<Classe> page = new PageImpl<>(Arrays.asList(classe));
        when(classeRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);
        when(classeMapper.toDTO(any(Classe.class))).thenReturn(classeDTO);

        Page<ClasseDTO> result = classeService.findAll(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(classeDTO.getName(), result.getContent().get(0).getName());
        verify(classeRepository).findAll(PageRequest.of(0, 10));
    }

    @Test
    void getClasseById_ShouldReturnClasseDTO() {
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));
        when(classeMapper.toDTO(classe)).thenReturn(classeDTO);

        ClasseDTO result = classeService.getClasseById(1L).orElse(null);

        assertNotNull(result);
        assertEquals(classeDTO.getName(), result.getName());
        verify(classeRepository).findById(1L);
    }

    @Test
    void getClasseById_ShouldThrowClasseNotFoundException() {
        when(classeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClasseNotFoundException.class, () -> {
            classeService.getClasseById(1L);
        });
        verify(classeRepository).findById(1L);
    }

    @Test
    void deleteClasse_ShouldDeleteClasse() {
        when(classeRepository.existsById(1L)).thenReturn(true);

        classeService.deleteClasse(1L);

        verify(classeRepository).deleteById(1L);
    }

    @Test
    void deleteClasse_ShouldThrowClasseNotFoundException() {
        when(classeRepository.existsById(1L)).thenReturn(false);

        assertThrows(ClasseNotFoundException.class, () -> {
            classeService.deleteClasse(1L);
        });
        verify(classeRepository).existsById(1L);
    }

    @Test
    void updateClasse_ShouldReturnUpdatedClasseDTO() {
        Classe updatedClasse = new Classe();
        updatedClasse.setId(1L);
        updatedClasse.setName("Advanced Math");
        updatedClasse.setRoomNum(102);

        ClasseDTO updatedClasseDTO = new ClasseDTO();
        updatedClasseDTO.setId(1L);
        updatedClasseDTO.setName("Advanced Math");
        updatedClasseDTO.setRoomNum(102);

        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));
        doNothing().when(classeMapper).updateEntityFromDTO(updatedClasseDTO, classe);
        when(classeRepository.save(classe)).thenReturn(updatedClasse);
        when(classeMapper.toDTO(updatedClasse)).thenReturn(updatedClasseDTO);

        ClasseDTO result = classeService.updateClasse(1L, updatedClasseDTO);

        assertNotNull(result);
        assertEquals(updatedClasseDTO.getName(), result.getName());
        verify(classeRepository).findById(1L);
        verify(classeMapper).updateEntityFromDTO(updatedClasseDTO, classe);
        verify(classeRepository).save(classe);
        verify(classeMapper).toDTO(updatedClasse);
    }


    @Test
    void updateClasse_ShouldThrowClasseNotFoundException() {
        ClasseDTO updatedClasseDTO = new ClasseDTO(1L, "Advanced Math", 102);

        when(classeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ClasseNotFoundException.class, () -> {
            classeService.updateClasse(1L, updatedClasseDTO);
        });
        verify(classeRepository).findById(1L);
    }

    @Test
    void getClassesByName_ShouldReturnListOfClasseDTO() {
        when(classeRepository.findByName("Math")).thenReturn(Arrays.asList(classe));
        when(classeMapper.toDTO(classe)).thenReturn(classeDTO);

        List<ClasseDTO> result = classeService.getClassesByName("Math");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(classeDTO.getName(), result.get(0).getName());
        verify(classeRepository).findByName("Math");
    }
}
