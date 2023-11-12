package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.DetalleProducto;
import com.fragansias.company.repository.DetalleProductoRepository;
import com.fragansias.company.service.contrato.DetalleProductoDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DetalleProductoImpl extends GenericoDAOImpl<DetalleProducto, DetalleProductoRepository>implements DetalleProductoDAO {
    public DetalleProductoImpl(DetalleProductoRepository repository) {
        super(repository);
    }
}
