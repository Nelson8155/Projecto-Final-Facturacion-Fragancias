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
   @NotEmpty
   @Size(min = 0,max = 17)
    private String num_nit;
    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;
    @NotEmpty
    private String telefono;
    @Email
    private String email;
    @JsonIgnoreProperties({"hibernateLazyInitializer","clientes"})
    private DetalleCliente detalleCliente;
}
