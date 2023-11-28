package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.repository.CategoriaRepository;
import com.fragansias.company.service.contrato.CategoriaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoriaImpl extends GenericoDAOImpl<Categoria, CategoriaRepository>implements CategoriaDAO {
    @Autowired
    public CategoriaImpl(CategoriaRepository repository) {
        super(repository);
    }

    /*@Override
    public List<Categoria> econtrarPorNombre(String param1) {
        return (List<Categoria>) repository.encontrarPorNombreCategoria(param1);
    }*/

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> findByName(String nombre) {
        return repository.findByName(nombre);
    }

    @Override
    public Iterable<Categoria> buscarPorNombreSimilar(String nombre) {
        return repository.buscarPorNombreSimilar(nombre);
    }
}
