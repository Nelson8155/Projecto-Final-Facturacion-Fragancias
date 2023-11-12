package com.fragansias.company.service.implementaciones;

import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.repository.ClienteRepository;
import com.fragansias.company.service.contrato.CategoriaDAO;
import com.fragansias.company.service.contrato.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteImpl extends GenericoDAOImpl<Cliente, ClienteRepository>implements ClienteDAO {
    @Autowired
    public ClienteImpl(ClienteRepository repository) {
        super(repository);
    }

    @Override
    public Optional<Cliente> buscarPorNombreYApellido(String nombre, String apellido) {
        return repository.buscarPorNombreYApellido(nombre,apellido);
    }

    @Override
    public Iterable<Cliente> buscarPorNit(String nit) {
        return repository.buscarPorNit(nit);
    }

    @Override
    public List<Cliente> buscarPorCorreo(String email) {
        return (List<Cliente>)repository.buscarPorCorreo(email);
    }

    @Override
    public Iterable<Cliente> mostrarDetallesCliente(String name) {
        return repository.mostrarDetallesCliente(name);
    }
}
