package com.fragansias.company.models.entity.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoDTO {

    private Integer id_producto;
    @NotEmpty
    @NotNull
    @Size(min = 0 ,max = 15)
    private String nombreProducto;
    @NotEmpty
    @NotNull
    @Size(min = 0, max = 10)
    private String codigoProducto;
    @NotEmpty
    @NotNull
    @Size(min = 0, max = 100)
    private Double precio;

    @NotEmpty
    @NotNull
    @Size(min = 0, max = 100)
    private String presentacion;
}
