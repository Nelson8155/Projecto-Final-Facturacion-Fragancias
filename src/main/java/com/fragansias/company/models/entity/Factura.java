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
            @Column(name = "fecha_creacion",nullable = false)
    private Date createAt;

    public Factura(String descripcion, Date createAt) {
        this.descripcion = descripcion;
        this.createAt = createAt;
    }
<<<<<<< HEAD
    @ManyToOne(fetch = EAGER,
=======
    @ManyToOne(fetch = LAZY,
    optional = true,
>>>>>>> 56bf701e8b648206008f057fab5536d600a5e9bc
    cascade = {
            CascadeType.ALL,
    CascadeType.MERGE,
    CascadeType.REMOVE})
<<<<<<< HEAD
    //@JoinColumn(name = "cliente_id",foreignKey = @ForeignKey(name = "FK_FACTURA_ID"))
    @JoinColumn(name ="cliende_id" )
    @JsonIgnoreProperties({"hibernateLazyInitializer","id","email"})
=======
   // @JoinColumn(name = "cliente_id",foreignKey = @ForeignKey(name = "FK_FACTURA_ID"))
   // @JsonIgnoreProperties({"hibernateLazyInitializer","id","email"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "factura"})
>>>>>>> 56bf701e8b648206008f057fab5536d600a5e9bc
    private Cliente cliente;


    @OneToMany( //fetch = EAGER,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
            })
            //CascadeType.REMOVE})
    //@JsonIgnoreProperties({"hibernateLazyInitializer","id","fechaCreacion","factura"})
    @JsonIgnoreProperties({"hibernateLazyInitializer", "itemFacturas"})
    @JoinColumn(name = "factura_id")
    private Set<ItemFactura> itemFacturas = new HashSet<>();

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }
}
