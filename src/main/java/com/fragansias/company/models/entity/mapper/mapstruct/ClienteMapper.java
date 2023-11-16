package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.models.entity.dto.ClienteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    @Mappings({
            @Mapping(source = "id",target = "id_cliente"),
            @Mapping(source = "nit",target = "num_nit")
    })
    ClienteDTO mapCliente(Cliente cliente);
}
