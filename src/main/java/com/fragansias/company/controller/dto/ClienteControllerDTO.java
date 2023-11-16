package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.models.entity.dto.ClienteDTO;
import com.fragansias.company.service.contrato.CategoriaDAO;
import com.fragansias.company.service.contrato.ClienteDAO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
@ConditionalOnProperty(prefix = "spring", name = "controller.enable-dto",havingValue = "true")
public class ClienteControllerDTO extends GenericoControllerDTO<Cliente, ClienteDAO>{


    public ClienteControllerDTO(ClienteDAO sevice) {
        super(sevice, "Cliente");
    }

    @GetMapping("/buscarPorNombre/{name}/apellido/{apellido}")
    public ResponseEntity<?> searchByNameAndApellido(@PathVariable String name,@PathVariable String apellido){
        Map<String,Object> response = new HashMap<>();
        Optional<Cliente> clientes = sevice.buscarPorNombreYApellido(name,apellido);



        if(!clientes.isPresent()){
            response.put("success",Boolean.FALSE);
            response.put("validaciones",String.format("No se encontro Cliente con nombre %s y apellido %s",name,apellido));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }



    }
}
