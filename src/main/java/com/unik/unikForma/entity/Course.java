package com.unik.unikForma.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Level is required")
    @Size(max = 50, message = "Level cannot exceed 50 characters")
    private String level;

    @Size(max = 255, message = "Prerequisites cannot exceed 255 characters")
    private String prerequisites;

    @Min(value = 1, message = "Minimum capacity must be at least 1")
    private int minCapacity;

    @Min(value = 1, message = "Maximum capacity must be at least 1")
    @Max(value = 100, message = "Maximum capacity cannot exceed 100")
    private int maxCapacity;

    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDate startDate;

    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private CourseStatus status;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<User> users = new HashSet<>();
}
