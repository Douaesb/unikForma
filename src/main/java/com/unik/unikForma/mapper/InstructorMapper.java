package com.unik.unikForma.mapper;

import com.unik.unikForma.dto.InstructorDTO;
import com.unik.unikForma.entity.Instructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface InstructorMapper {
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "classe.id", source = "classeId")
    Instructor toEntity(InstructorDTO instructorDTO);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "classeId", source = "classe.id")
    InstructorDTO toDTO(Instructor instructor);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(InstructorDTO dto, @MappingTarget Instructor entity);
}
