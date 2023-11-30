package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

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
            @Column(name = "fecha_creacion")
    private Date createAt;



    @ManyToOne(
            fetch = LAZY,
    cascade = {
            CascadeType.ALL,
    CascadeType.MERGE,
    CascadeType.REMOVE})
    @JoinColumn(name ="cliende_id", foreignKey = @ForeignKey(name = "FK_CLIENTE_ID") )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "facturas"})
    private Cliente cliente;


    @OneToMany( //fetch = EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
            })
            //CascadeType.REMOVE})
    //@JsonIgnoreProperties({"hibernateLazyInitializer","id","fechaCreacion","factura"})
    @JsonIgnoreProperties({"facturas"})
    @JoinColumn(name = "factura_id")
    private Set<ItemFactura> itemFacturas = new HashSet<>();

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

}
