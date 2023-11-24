package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO extends GenericoDAO<Cliente>{
    Optional<Cliente> findByName(String name);
    Optional<Cliente> buscarPorNombreYApellido(String nombre, String apellido);
    Optional<Cliente> buscarPorNit(String nit);
    List<Cliente> buscarPorCorreo(String email);

}
