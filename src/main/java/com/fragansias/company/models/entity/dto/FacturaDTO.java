package com.fragansias.company.models.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fragansias.company.models.entity.Cliente;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacturaDTO {

    private Long id_factura;
    @NotEmpty
    @NotNull
    @Size(min = 0,max = 100)
    private String descripcion;

    private Date creada_en;

    @JsonIgnoreProperties({"hibernateLazyInitializer","factura"})
    private Cliente clientes;
}
