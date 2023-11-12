package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.models.entity.dto.CategoriaDTO;
import com.fragansias.company.models.entity.dto.ProductoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mappings({
            @Mapping(source = "id",target = "id_producto"), //mapear la entidad con la clase dto
    })
    ProductoDTO mapCategoria(Producto producto);
}
