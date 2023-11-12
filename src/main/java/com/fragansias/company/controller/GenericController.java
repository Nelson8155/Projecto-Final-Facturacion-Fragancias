package com.fragansias.company.controller;

import com.fragansias.company.exception.BadRequestException;
import com.fragansias.company.service.contrato.GenericoDAO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Deprecated
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
public class GenericController <E, S extends GenericoDAO<E>> {
    protected final S service;
    protected String nombreEntidad;
    public GenericController(S service) {
        this.service = service;
    }

    //encontrar todos los registros en la base de datos
    @GetMapping
    public ResponseEntity<?> buscarTodos (){
        List<E> oG = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            oG = (List<E>) service.findAll();
            if(oG.isEmpty()){
                response.put("message:","No se encontro Resultado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(oG,HttpStatus.OK);

    }

    //obtenerPorID (Id)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorID(@PathVariable(required = false) Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<E> oE;
        try {
             oE = service.findById(id);
            if (!oE.isPresent()) {
               response.put("message:","No se encontro Resultado");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(oE,HttpStatus.OK);
    }


    //borrarEntidadPorId (Id)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarEntidadPorId(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<E> obj = null;
        try {
            obj = service.findById(id);
            if (!obj.isPresent()){
                response.put("message:","No se econtraron Resultados");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
            service.deleteById(id);
            response.put("message","!Delete Succefully!");
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
