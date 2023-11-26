package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.Set;
import static jakarta.persistence.FetchType.LAZY;

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
    @Column(nullable = false)
    private Double precio;
    @Column(nullable = false)
    private String presentacion;

    @Embedded
    private DetalleProducto detalleProducto;

    @Embedded
    private Auditoria audit = new Auditoria();

    @OneToMany(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","productos"})
    @JoinColumn(name = "producto_id")
    private Set<ItemFactura> itemFacturas;

    @ManyToOne(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST, //si utilizamos cascade all, eliminamos la categoria relacionada a producto
                    CascadeType.MERGE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","productos"})//utilizamos estaanotacion para mostrar la relacion q existe entre entidades
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "FK_CATEGORIA_ID"))
    private Categoria categoria;
    public Producto(String nombreProducto, String codigoProducto, Double precio, String presentacion) {
        this.nombreProducto = nombreProducto;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.presentacion = presentacion;
    }
}
