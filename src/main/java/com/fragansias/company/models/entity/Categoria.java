package com.fragansias.company.models.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "categorias")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Categoria implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre_categoria")
    private String nombreCategoria;
    @Column(name = "genero")
    private String genero;
    @Column(name = "descripcion_categoria",nullable = false)
    private String descripcion;

    @OneToMany(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","id","productos"})
    @JoinColumn(name = "categoria_id")
    private Set<Producto> productos = new HashSet<>();

}
