package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.repository.FacturaRepository;
import com.fragansias.company.service.contrato.FacturaDAO;
import com.fragansias.company.service.contrato.GenericoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FacturaImpl extends GenericoDAOImpl<Factura, FacturaRepository> implements FacturaDAO {
    @Autowired
    public FacturaImpl(FacturaRepository repository) {
        super(repository);
    }
}
