package com.fragansias.company.repository;

import com.fragansias.company.models.entity.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria,Long> {
    @Query("select c from Categoria c where upper(c.nombreCategoria) = upper(?1)")
    Iterable<Categoria> encontrarPorNombreCategoria(String categoria);

    Categoria findByName(String categoria);
}
