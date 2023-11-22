package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class DetalleCliente {

    private String direccion;
    private String departamento;
    private String municipio;
    private String sexo;

}
