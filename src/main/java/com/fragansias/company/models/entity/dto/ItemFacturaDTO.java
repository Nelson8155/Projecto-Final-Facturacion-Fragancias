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
@Data
@ToString
public class ItemFacturaDTO {

    private Long id_Item_Factura;
    @NotNull(message = "Este campo no puede ir vacio")
    @Min(value = 1,message = "Al menos debe ser mayor a 1")
    private Integer cantidad;

    private Date fecha_creacion;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "item_Facturas"})
    private Producto producto;

    @JsonIgnoreProperties({"hibernateLazyInitializer","item_Facturas"})
    private Factura factura;

    @NotNull
    private Double precioTotal;

    public ItemFacturaDTO(Long id_Item_Factura, Integer cantidad, Date fecha_creacion, Producto producto, Factura factura, Double precioTotal) {
        this.id_Item_Factura = id_Item_Factura;
        this.cantidad = cantidad;
        this.fecha_creacion = fecha_creacion;
        this.producto = producto;
        this.factura = factura;
        this.precioTotal = precioTotal;
    }
}
