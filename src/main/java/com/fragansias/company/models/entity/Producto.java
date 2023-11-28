package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.HashSet;
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

    @NotNull
    private Double precio;
    private String presentacion;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "fechaCreacion", column = @Column(name = "fecha_creacion")),
                    @AttributeOverride(name = "tipoFrasco", column = @Column(name = "tipo_de_frasco"))
            }
    )
    private DetalleProducto detalleProducto;

    /*@OneToMany(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","id","fechaCreacion","factura","itemFactura"})
    @JoinColumn(name = "producto_id")
    private Set<ItemFactura> itemFactura ;*/

    @ManyToOne(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST, //si utilizamos cascade all, eliminamos la categoria relacionada a producto
                    CascadeType.MERGE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","id","productos"})//utilizamos estaanotacion para mostrar la relacion q existe entre entidades
    @JoinColumn(name = "categoria_id", foreignKey = @ForeignKey(name = "FK_CATEGORIA_ID"))
    private Categoria categoria;
    public Producto(String nombreProducto, String codigoProducto, Double precio, String presentacion) {
        this.nombreProducto = nombreProducto;
        this.codigoProducto = codigoProducto;
        this.precio = precio;
        this.presentacion = presentacion;
    }
}
