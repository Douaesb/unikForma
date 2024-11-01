package com.unik.unikForma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.service.ClasseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("development")
public class ClasseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mock the ClasseService bean
    private ClasseService classeService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void createClasse_ShouldReturnCreatedClasseDTO() throws Exception {
        ClasseDTO classeDTO = new ClasseDTO(null, "Mathematics", 101);
        ClasseDTO savedClasseDTO = new ClasseDTO(1L, "Mathematics", 101);

        when(classeService.saveClasse(any(ClasseDTO.class))).thenReturn(savedClasseDTO);

        mockMvc.perform(post("/classes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(classeDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.roomNum").value(101));

        verify(classeService, times(1)).saveClasse(any(ClasseDTO.class));
    }

    @Test
    void getClasseById_ShouldReturnClasseDTO() throws Exception {
        ClasseDTO classeDTO = new ClasseDTO(1L, "Mathematics", 101);

        when(classeService.getClasseById(1L)).thenReturn(java.util.Optional.of(classeDTO));

        mockMvc.perform(get("/classes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Mathematics"))
                .andExpect(jsonPath("$.roomNum").value(101));

        verify(classeService, times(1)).getClasseById(1L);
    }

    @Test
    void deleteClasse_ShouldReturnNoContent() throws Exception {
        doNothing().when(classeService).deleteClasse(1L);

        mockMvc.perform(delete("/classes/1"))
                .andExpect(status().isNoContent());

        verify(classeService, times(1)).deleteClasse(1L);
    }

    @Test
    void updateClasse_ShouldReturnUpdatedClasseDTO() throws Exception {
        ClasseDTO updatedClasseDTO = new ClasseDTO(1L, "Advanced Mathematics", 102);
        ClasseDTO savedClasseDTO = new ClasseDTO(1L, "Advanced Mathematics", 102);

        when(classeService.updateClasse(1L, updatedClasseDTO)).thenReturn(savedClasseDTO);

        mockMvc.perform(put("/classes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedClasseDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Advanced Mathematics"))
                .andExpect(jsonPath("$.roomNum").value(102));

        verify(classeService, times(1)).updateClasse(1L, updatedClasseDTO);
    }

    @Test
    void getClassesByName_ShouldReturnListOfClasses() throws Exception {

        mockMvc.perform(get("/classes/search/name?name=Mathematics"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(classeService, times(1)).getClassesByName("Mathematics");
    }
}
