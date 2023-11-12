package com.fragansias.company.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
@Entity
@Table(name = "detalles_productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DetalleProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String descripcion;
    @Column(name = "año")
    private Date year ;
    @Column(name = "tipo_de_frasco")
    private String tipoFrasco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
