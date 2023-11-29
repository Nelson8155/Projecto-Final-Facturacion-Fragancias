package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "items_facturas")
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

    @ManyToOne(//fetch = EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JsonIgnoreProperties({"hibernateLazyInitializer","itemFactura"})
    @JoinColumn(name = "factura_id")
    //HIBERNATE PARA HACER UN FECH INTERNO
    private Factura factura;

    @ManyToOne(//fetch = EAGER,
            cascade = {CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REMOVE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","id","itemFactura"})
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public ItemFactura(Long id, Integer cantidad, Date fechaCreacion) {
        this.id = id;
        this.cantidad = cantidad;
        this.fechaCreacion = fechaCreacion;
    }
}
