package com.unik.unikForma.mapper;

import com.unik.unikForma.dto.CourseDTO;
import com.unik.unikForma.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CourseMapper {
    Course toEntity(CourseDTO courseDTO);

    CourseDTO toDTO(Course course);


    @Mapping(target = "id", source = "dto.id")

    void updateEntityFromDTO(CourseDTO dto, @MappingTarget Course entity);

}
