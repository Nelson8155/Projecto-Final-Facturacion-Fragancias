package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.dto.FacturaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    /*@Mappings({
            @Mapping(source = "id",target = "id_factura"),
            @Mapping(source = "createAt",target = "fecha_creacion")
    })
    FacturaDTO mapFactura(Factura factura);*/
}
