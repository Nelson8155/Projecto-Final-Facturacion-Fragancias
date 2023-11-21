package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.dto.DetalleCLienteDTO;
import com.fragansias.company.service.contrato.GenericoDAO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public class GenericoDAOImpl <E, R extends CrudRepository<E, Long>> implements GenericoDAO<E>{

    protected final R repository;

    public GenericoDAOImpl(R repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public DetalleCLienteDTO findById(Long id) {
        return repository.findById(id);
    }
    @Override
    @Transactional
    public E save(E entidad) {
        return repository.save(entidad);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<E> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
