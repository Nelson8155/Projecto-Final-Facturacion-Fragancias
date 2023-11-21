package com.fragansias.company.service.contrato;

import com.fragansias.company.models.entity.dto.DetalleCLienteDTO;

public interface GenericoDAO <E> {

    DetalleCLienteDTO findById(Long id);
    E save(E entidad);
    Iterable<E> findAll();
    void deleteById(Long id);
}
