package com.unik.unikForma.mapper;

import com.unik.unikForma.dto.ClasseDTO;
import com.unik.unikForma.entity.Classe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ClasseMapper {
    Classe toEntity(ClasseDTO classeDTO);

    ClasseDTO toDTO(Classe classe);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ClasseDTO dto, @MappingTarget Classe entity);
}
