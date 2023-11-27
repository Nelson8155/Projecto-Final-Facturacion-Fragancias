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

import java.util.List;
import java.util.Set;

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

    /*@OneToMany(
            fetch = LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer","clientes"})
    @JoinColumn(name = "cliente_id")
    private List<Factura> factura;*/

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
    private Auditoria audit = new Auditoria();

    public Cliente(String nit, String nombre, String apellido, String telefono, String email) {
        this.nit = nit;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
    }
}
