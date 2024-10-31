package com.unik.unikForma.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Min(value = 1, message = "Room number must be at least 1")
    @Max(value = 999, message = "Room number cannot exceed 999")
    private int roomNum;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private Set<Learner> learners = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

}
