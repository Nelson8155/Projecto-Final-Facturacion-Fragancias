package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "item_Facturas")
@Data
@NoArgsConstructor
public class ItemFactura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @NotNull
    private Double precioTotal;

    @ManyToOne(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
            CascadeType.REMOVE})
    @JoinColumn(
            name = "factura_id",
            foreignKey = @ForeignKey(name = "FK_FACTURA_ID")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","item_Facturas"})
    private Factura factura;

    @ManyToOne(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE}
    )
    @JoinColumn(
            name = "producto_id",
            foreignKey = @ForeignKey(name = "FK_PRODUCTO_ID")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "item_Facturas"})
    private Producto producto;


}
