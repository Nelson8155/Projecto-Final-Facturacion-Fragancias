package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoDAO extends GenericoDAO<Producto> {
    Optional <Producto> findByName(String nombre);
    List<Producto> obtenerPorCodigo(String codigo);
    Iterable<Producto> obtenerPorCategoria(String categoria);
    List<Producto> obtenerPorNombreSimilar(String name);

}
