package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.SessionAttributes;

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

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "direccion", column =@Column(name = "direccion_s"))
    )
    private DetalleCliente detalleCliente;

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
