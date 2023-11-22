package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items_facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cantidad",nullable = false)
    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE})
    @JoinColumn(name = "factura_id", foreignKey = @ForeignKey(name = "FK_FACTURA_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","items_facturas"})//HIBERNATE PARA HACER UN FECH INTERNO
    private Factura factura;
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE}
    )
    @JoinColumn(name = "producto_id", foreignKey = @ForeignKey(name = "FK_PRODUCTO_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","items_facturas"})
    private Producto producto;
}
