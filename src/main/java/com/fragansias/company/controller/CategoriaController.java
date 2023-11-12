package com.fragansias.company.controller;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.repository.CategoriaRepository;
import com.fragansias.company.service.contrato.CategoriaDAO;
import com.fragansias.company.service.implementaciones.GenericoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Deprecated
@RestController
@RequestMapping("/api/categoria")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
public class CategoriaController extends GenericController<Categoria, CategoriaDAO> {
    @Autowired
    public CategoriaController(CategoriaDAO service) {
        super(service);
    }
    @GetMapping("/buscarPorNombreDeCategoria/{param1}")
    public ResponseEntity<?> response (@PathVariable String param1){
        List<Categoria> categorias = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            categorias = (List<Categoria>) service.findByName(param1);
            if(categorias.isEmpty()){
                response.put("message","No se encontro Categoria");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(categorias,HttpStatus.OK);
    }



}
