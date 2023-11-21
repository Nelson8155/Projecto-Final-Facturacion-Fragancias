package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO extends GenericoDAO<Cliente>{
    Cliente buscarPorNombreYApellido(String nombre, String apellido);
    Optional<Cliente> buscarPorNit(String nit);
    List<Cliente> buscarPorCorreo(String email);
    Iterable<Cliente> mostrarDetallesCliente(String name);
}
