package com.fragansias.company.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Embeddable
public class Auditoria {
    @Column(name = "creado_en")
    private LocalDateTime creadoEn;
    @Column(name = "editado_en")
    private LocalDateTime editadoEn;
    @PrePersist
    public void prePersist(){
        System.out.println("Inicializar algo justo antes del persist");
        this.editadoEn = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        System.out.println("Inicializar algo justo antes del update");
        this.editadoEn = LocalDateTime.now();
    }


}
