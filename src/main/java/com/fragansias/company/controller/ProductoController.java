package com.fragansias.company.controller;

import com.fragansias.company.exception.BadRequestException;
import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.repository.ProductoRepository;
import com.fragansias.company.service.contrato.ProductoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;
@Deprecated
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "false")
@RestController
@RequestMapping(path = "/api/producto")
public class ProductoController extends GenericController<Producto, ProductoDAO> {

    private BadRequestException exception;
    public ProductoController(ProductoDAO service) {
        super(service);
    }

    @GetMapping("/buscarPorCodigo/{codigo}")
    public ResponseEntity <?> obtenerPorCodigo(@PathVariable String codigo){
        List<Producto> oProductos = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();

        try {
            oProductos = service.obtenerPorCodigo(codigo);
            if (oProductos.isEmpty()) {
                response.put("message:","Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(oProductos,HttpStatus.OK);
    }
    @GetMapping("/buscarPorCategoria/{categoria}")
    public ResponseEntity<?> obtenerPorCategoria(@PathVariable String categoria){
        List<Producto> productos = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            productos = (List<Producto>) service.obtenerPorCategoria(categoria);
            if(productos.isEmpty()){
                response.put("message:","Esta Categoria no existe");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productos,HttpStatus.OK);
    }
    @GetMapping("/buscarPorNombreDeProducto/{name}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String name){
        List<Producto> productos = new ArrayList<>();
        Map<String,Object> response = new HashMap<>();
        try {
            productos = service.obtenerPorNombreProducto(name);
            if(productos.isEmpty()){
                response.put("message:","Producto no encontrado");
                return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("message:",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(productos,HttpStatus.OK);
    }

    /*public ResponseEntity <?> guardarProducto(@RequestBody Producto producto){
        Optional<Producto> oProducto = null;
        Map<String,Object> response = new HashMap<>();
        try {
            oProductos = service.obtenerPorCodigo(codigo);
            if (oProductos.isEmpty()) {
                response.put("message:", "Producto no encontrado");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        }catch (DataAccessException e){
            response.put("error",e.getMessage());
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(oProductos,HttpStatus.OK);
    }*/

}
