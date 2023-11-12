package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.repository.ProductoRepository;
import com.fragansias.company.service.contrato.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoImpl extends GenericoDAOImpl<Producto, ProductoRepository> implements ProductoDAO {
    @Autowired
    public ProductoImpl(ProductoRepository repository) {
        super(repository);
    }
    @Override
    @Transactional(readOnly = true)
    public List<Producto> obtenerPorCodigo(String codigo) {
        return (List<Producto>) repository.busquedaPorCodigoProducto(codigo);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Producto> obtenerPorCategoria(String categoria) {
        return repository.busquedaPorCategoria(categoria);
    }

    @Override
    public List<Producto> obtenerPorNombreProducto(String name) {
        return (List<Producto>) repository.busquedaPorNombreDeProducto(name);
    }


}
