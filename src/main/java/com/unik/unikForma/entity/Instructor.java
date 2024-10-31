package com.unik.unikForma.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends User {

    @NotBlank(message = "Speciality is required")
    private String speciality;

    @OneToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;
}
