package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.dto.FacturaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    @Mappings({
            @Mapping(source = "id",target = "id_factura"),
    })
    FacturaDTO mapFactura(Factura factura);
    @Mappings({
            @Mapping(source = "id_factura",target = "id")
    })
    Factura mapDTOFactura(FacturaDTO factura);

}
