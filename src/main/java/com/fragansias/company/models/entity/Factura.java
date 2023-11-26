package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion",nullable = false)
    private String descripcion;

    @Temporal(TemporalType.DATE)
            @Column(name = "fecha_creacion",nullable = false)
    private Date createAt;

    public Factura(String descripcion, Date createAt) {
        this.descripcion = descripcion;
        this.createAt = createAt;
    }
    @ManyToOne(fetch = FetchType.EAGER,
    cascade = {CascadeType.PERSIST,
    CascadeType.MERGE})
    @JoinColumn(name = "cliente_id",foreignKey = @ForeignKey(name = "FK_FACTURA_ID"))
    @JsonIgnoreProperties({"hibernateLazyInitializer","facturas"})
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE})
    @JsonIgnoreProperties({"hibernateLazyInitializer","facturas"})
    @JoinColumn(name = "factura_id")
    private Set<ItemFactura> itemFacturas = new HashSet<>();

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }



}
