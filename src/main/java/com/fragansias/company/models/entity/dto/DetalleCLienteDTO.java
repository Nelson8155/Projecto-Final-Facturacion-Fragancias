package com.fragansias.company.models.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCLienteDTO {
    @NotEmpty
    @NotNull
    private Long codigo;
   @NotEmpty
    private String departamento;
   @NotEmpty
    private String municipio;
   @NotEmpty
   @NotNull
    private String sexo;
}
