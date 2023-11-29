package com.fragansias.company.models.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.models.entity.Producto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FacturaDTO {
    private Long id_factura;
    @NotEmpty
    @NotNull
    @Size(min = 0,max = 100)
    private String descripcion;

    private Date creada_en;

    /*@JsonIgnoreProperties({"hibernateLazyInitializer","id","factura","audit"})
    private Cliente clientes;*/
    @JsonIgnoreProperties({"hibernateLazyInitializer","factura"})
    private Cliente cliente;
    /*@JsonIgnoreProperties({"hibernateLazyInitializer","categoria","facturaDTO","fecha_creacion"})
    private ItemFacturaDTO itemsDTO;*/
    @JsonIgnoreProperties({"hibernateLazyInitializer", "itemFacturas"})
    private Set<ItemFactura> itemFacturas = new HashSet<>();

}
