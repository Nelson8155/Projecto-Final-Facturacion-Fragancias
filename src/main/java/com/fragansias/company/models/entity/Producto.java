package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "productos")
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;
    @Column(name = "codigo_producto", nullable = false)
    private String codigoProducto;
    @Column(name = "precio", nullable = false)
    private Double precio;
    @Column(name = "presentacion", nullable = false)
    private String presentacion;
    @Embedded
    private Auditoria audit = new Auditoria();

    @ManyToOne(
            optional = true,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","productos"})//utilizamos estaanotacion para mostrar la relacion q existe entre entidades

    private Categoria categoria;
    public Producto(String nombreProducto, String codigoProducto, Double precio, String presentacion) {
        this.nombreProducto = nombreProducto;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.presentacion = presentacion;
    }
}
