package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Producto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoDAO extends GenericoDAO<Producto> {

    List<Producto> obtenerPorCodigo(String codigo);
    Iterable<Producto> obtenerPorCategoria(String categoria);
    List<Producto> obtenerPorNombreProducto(String name);

}
