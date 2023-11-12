package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.dto.CategoriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    @Mappings({
            @Mapping(source = "id",target = "id_categoria"), //mapear la entidad con la clase dto
    })
    CategoriaDTO mapCategoria(Categoria categoria);
}
