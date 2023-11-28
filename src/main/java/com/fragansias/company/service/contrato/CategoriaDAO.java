package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaDAO extends GenericoDAO<Categoria>{
    //List<Categoria> econtrarPorNombre(String param1);
    Optional <Categoria> findByName(String nombre);

    Iterable <Categoria> buscarPorNombreSimilar (String nombre);
}
