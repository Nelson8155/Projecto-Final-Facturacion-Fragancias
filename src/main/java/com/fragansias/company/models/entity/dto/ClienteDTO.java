package com.fragansias.company.models.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fragansias.company.models.entity.Auditoria;
import com.fragansias.company.models.entity.DetalleCliente;
import com.fragansias.company.models.entity.Factura;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ClienteDTO {
    /*@NotEmpty
    @NotNull
    @NotBlank*/
    private Long id_cliente;

    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
   @Size(min = 17,max = 17)
    private String num_nit;

    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    private String nombre;

    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    private String apellido;

    @NotEmpty(message = "Este campo no puede estar vacio!")
    @NotNull
    private String telefono;

    private Auditoria audit = new Auditoria();

    @Email(message = "Este campo no puede estar vacio!")
    @NotNull
    private String email;
    @JsonIgnoreProperties({"hibernateLazyInitializer","clientes","sexo"})
    private DetalleCliente detalleCliente;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "manejador"})
    private Set<Factura> factura = new HashSet<>();
}
