package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "num_nit",nullable = false)
    private String nit;
    @Column(name = "nombres",nullable = false)
    private String nombre;
    @Column(name = "apellidos")
    private String apellido;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo_email",nullable = false)
    private String email;
    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<DetalleCliente> detalleClientes;

    @Embedded
    private Auditoria audit = new Auditoria();

    public Cliente(String nit, String nombre, String apellido, String telefono, String email) {
        this.nit = nit;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
}
