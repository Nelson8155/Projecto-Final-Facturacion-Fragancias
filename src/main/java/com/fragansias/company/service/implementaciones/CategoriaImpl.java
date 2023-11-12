package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.repository.CategoriaRepository;
import com.fragansias.company.service.contrato.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaImpl extends GenericoDAOImpl<Categoria, CategoriaRepository>implements CategoriaDAO {
    @Autowired
    public CategoriaImpl(CategoriaRepository repository) {
        super(repository);
    }

    @Override
    public List<Categoria> econtrarPorNombre(String param1) {
        return (List<Categoria>) repository.encontrarPorNombreCategoria(param1);
    }

    @Override
    public Categoria findByName(String categoria) {
        return repository.findByName(categoria);
    }


}
