package com.unik.unikForma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearnerDTO extends UserDTO {

    @NotBlank(message = "Level is required")
    private String level;

    private Long classeId; 
}
