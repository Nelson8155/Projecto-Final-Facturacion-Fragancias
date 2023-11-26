package com.fragansias.company.models.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.Producto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ItemFacturaDTO {

    private Long id_itemFactura;

    @NotNull(message = "Este campo no puede ir vacio")
    @Min(value = 1,message = "Al menos debe ser mayor a 1")
    private Integer cantidad;

    private Date fecha_creacion;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "item_facturas"})
    private Producto product;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "item_factura"})
    private Factura factura;


    //@Positive(message = "Este campo tiene que ser positivo")
    private Double precioTotal;
}
