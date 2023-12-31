package com.fragansias.company.models.entity.mapper.mapstruct;

import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.models.entity.dto.ItemFacturaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring")
public interface ItemFacturaMapper {

        @Mappings({
                @Mapping(source = "id", target = "id_Item_Factura"),

        })
        ItemFacturaDTO mapItemFactura(ItemFactura itemFactura);

        @Mappings({
                @Mapping(source = "id_Item_Factura", target = "id"),

        })
        ItemFactura mapItemFactura(ItemFacturaDTO itemFacturaDTO);
}
