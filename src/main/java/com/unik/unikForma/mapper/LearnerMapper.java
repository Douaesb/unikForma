package com.unik.unikForma.mapper;

import com.unik.unikForma.dto.LearnerDTO;
import com.unik.unikForma.entity.Learner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface LearnerMapper {
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "classe.id", source = "classeId")
    Learner toEntity(LearnerDTO learnerDTO);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "classeId", source = "classe.id")
    LearnerDTO toDTO(Learner learner);
}
