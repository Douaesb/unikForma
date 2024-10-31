package com.unik.unikForma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDTO extends UserDTO {

    @NotBlank(message = "Speciality is required")
    private String speciality;

    private Long classeId;
}
