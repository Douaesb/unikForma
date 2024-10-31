package com.unik.unikForma.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("LEARNER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Learner extends User {

    @NotBlank(message = "Level is required")
    private String level;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;
}
