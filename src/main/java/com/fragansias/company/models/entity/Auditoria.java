package com.fragansias.company.models.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Embeddable
public class Auditoria {

    @Column(name = "creado_en")
    private Date creadoEn;
    @Column(name = "editado_en")
    private Date editadoEn;
    @PrePersist
    public void prePersist(){
       creadoEn = new Date();
    }
    @PreUpdate
    public void preUpdate(){
        creadoEn = new Date();
    }


}
