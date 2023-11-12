package com.fragansias.company.models.entity.dto;

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
public class CategoriaDTO {
    private Integer id_categoria;
    @NotEmpty
    @NotNull
    @Size(min = 0 ,max = 15)
    private String nombreCategoria;
    @NotEmpty
    @NotNull
    @Size(min = 0,max = 10)
    private String genero;
    @NotEmpty
    @NotNull
    @Size(min = 0,max = 100)
    private String descripcion;


}
