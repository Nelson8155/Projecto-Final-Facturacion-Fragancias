package com.fragansias.company.models.entity.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetalleProductoDTO {
    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion ;

    private String tipoFrasco;
}
