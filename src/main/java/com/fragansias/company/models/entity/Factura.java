package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripcion",nullable = false)
    private String descripcion;
    @Column(name = "fecha_creacion",nullable = false)
    private Date createAt;

    public Factura(String descripcion, Date createAt) {
        this.descripcion = descripcion;
        this.createAt = createAt;
    }
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
