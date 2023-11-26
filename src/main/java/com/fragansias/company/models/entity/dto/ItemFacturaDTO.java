package com.fragansias.company.models.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemFacturaDTO {

    private Long id_itemFactura;

    private Integer cantidad;
}
