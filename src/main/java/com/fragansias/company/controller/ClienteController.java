package com.fragansias.company.controller;

import com.fragansias.company.exception.BadRequestException;
import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.repository.ClienteRepository;
import com.fragansias.company.service.contrato.ClienteDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Deprecated
@RestController
@RequestMapping("/api/cliente")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
public class ClienteController extends GenericController<Cliente, ClienteDAO>  {
    @Autowired
    public ClienteController(ClienteDAO service) {
        super(service);
    }

    @GetMapping("/buscarPorNombreYApellido/{nombre}/apellido/{apellido}")
    public ResponseEntity<?> buscarPersonaPorNombreYApellido(@PathVariable String nombre,@PathVariable String apellido){
        Optional<Cliente> oCliente ;
        Map<String,Object> response = new HashMap<>();
        try {
            oCliente = service.buscarPorNombreYApellido(nombre,apellido);
            if(oCliente.isEmpty()){
                response.put("message:","!No se encontraron Resultados!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(oCliente,HttpStatus.OK);
    }
    @GetMapping("/buscarPorNit/{nit}")
    public ResponseEntity<?> buscarPersonaPorNit(@PathVariable String nit){
        List<Cliente> clientes = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            clientes = (List<Cliente>) service.buscarPorNit(nit);
            if(clientes.isEmpty()){
                response.put("message:","!No se encontraron Resultados!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(clientes,HttpStatus.OK);
    }
    @GetMapping("/buscarPorEmail/{email}")
    public ResponseEntity<?> buscarPersonaPorEmail(@PathVariable String email){
        List<Cliente> clientes = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            clientes = (List<Cliente>) service.buscarPorCorreo(email);
            if(clientes.isEmpty()){
                response.put("message:","!No se encontraron Resultados!");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(clientes,HttpStatus.OK);
    }

}
