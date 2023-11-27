package com.fragansias.company.models.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fragansias.company.models.entity.DetalleCliente;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    @Email(message = "Este campo no puede estar vacio!")
    @NotNull
    private String email;
    @JsonIgnoreProperties({"hibernateLazyInitializer","clientes","sexo"})
    private DetalleCliente detalleCliente;
}
