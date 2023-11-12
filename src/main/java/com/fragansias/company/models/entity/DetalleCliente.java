package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "detalles_clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetalleCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "direccion_residensia",nullable = false)
    private String direccion;
    @Column(name = "departamento")
    private String departamento;
    private String municipio;
    @Column(name = "sexo")
    private String sexo;
    public DetalleCliente(String direccion, String departamento, String municipio, String sexo) {
        this.direccion = direccion;
        this.departamento = departamento;
        this.municipio = municipio;
        this.sexo = sexo;
    }
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
