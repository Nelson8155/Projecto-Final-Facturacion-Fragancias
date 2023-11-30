package com.fragansias.company.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    @Column(name = "num_nit")
    private String nit;

    @Column(name = "nombres")
    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull

    private String nombre;

    @Column(name = "apellidos")
    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    private String apellido;

    @Column(name = "telefono")
    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    private String telefono;

    @Column(name = "correo_email")
    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    @Email
    private String email;



    @OneToMany(
            fetch = EAGER,
            mappedBy = "cliente",
            cascade = {CascadeType.ALL}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "manejador"})
    private Set<Factura> factura = new HashSet<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "direccion", column = @Column(name = "direccion")),
            @AttributeOverride(name = "departamento", column = @Column(name = "departamento")),
            @AttributeOverride(name = "municipio", column = @Column(name = "municipio")),
            @AttributeOverride(name = "sexo", column = @Column(name = "sexo"))
    })
    @JsonIgnoreProperties({"hibernateLazyInitializer","sexo"})
    private DetalleCliente detalleCliente;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creadoEn", column = @Column(name = "creado_en")),
            @AttributeOverride(name = "editadoEn", column = @Column(name = "editado_en"))
    })
    private Auditoria audit = new Auditoria();


}
