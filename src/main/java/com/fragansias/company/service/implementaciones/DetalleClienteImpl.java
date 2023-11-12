package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.DetalleCliente;
import com.fragansias.company.repository.DetalleClienteRepository;
import com.fragansias.company.service.contrato.DetalleClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DetalleClienteImpl extends GenericoDAOImpl<DetalleCliente, DetalleClienteRepository>implements DetalleClienteDAO {
    @Autowired
    public DetalleClienteImpl(DetalleClienteRepository repository) {
        super(repository);
    }

}
