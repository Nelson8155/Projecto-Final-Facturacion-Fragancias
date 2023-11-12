package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.Categoria;

import java.util.List;

public interface CategoriaDAO extends GenericoDAO<Categoria>{
    List<Categoria> econtrarPorNombre(String param1);
    Categoria findByName(String categoria);
}
