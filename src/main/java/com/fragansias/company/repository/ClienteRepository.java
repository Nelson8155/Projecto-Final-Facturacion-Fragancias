package com.fragansias.company.repository;

import com.fragansias.company.models.entity.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends CrudRepository<Cliente,Long> {
    @Query("select c from Cliente c where c.nombre = ?1 and c.apellido = ?2")
    Optional<Cliente> buscarPorNombreYApellido(String nombre, String apellido);
    @Query("select c from Cliente c where upper(c.nit) = upper(?1)")
    Iterable<Cliente> buscarPorNit(String param1);
    @Query("select c From Cliente c where upper(c.email) = upper(?1)")
    Iterable<Cliente> buscarPorCorreo(String email);
   @Query("select c from Cliente c  join fetch c.detalleClientes d where upper(c.nombre) like upper(?1)")
   List<Cliente> mostrarDetallesCliente(String nombre);

}
