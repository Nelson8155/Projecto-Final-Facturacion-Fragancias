package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


@Embeddable
public class DetalleProducto implements Serializable {

    private String descripcion;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion ;

    private String tipoFrasco;

    public DetalleProducto() {
    }

    public DetalleProducto(String descripcion, Date fechaCreacion, String tipoFrasco) {
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.tipoFrasco = tipoFrasco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTipoFrasco() {
        return tipoFrasco;
    }

    public void setTipoFrasco(String tipoFrasco) {
        this.tipoFrasco = tipoFrasco;
    }

    @Override
    public String toString() {
        return "DetalleProducto{" +
                "descripcion='" + descripcion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", tipoFrasco='" + tipoFrasco + '\'' +
                '}';
    }
}
