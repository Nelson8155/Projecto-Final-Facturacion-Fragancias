package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.dto.CategoriaDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.CategoriaMapper;
import com.fragansias.company.service.contrato.CategoriaDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categoria")
@ConditionalOnProperty(prefix = "app",name = "controller.enable-dto",havingValue = "true")//PARA PODER ACTIVAR LA CONFIG DEL DTO
public class CategoriaControllerDTO extends GenericoControllerDTO<Categoria, CategoriaDAO> {
    @Autowired
    private CategoriaMapper mapper;
    public CategoriaControllerDTO(CategoriaDAO service) {
        super(service,"categoria");
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        Map<String,Object> response=new HashMap<>();
        List<Categoria> categorias = super.obtenerTodos(); // utilizamos la herencia para mandar a llamar un metodo del generico

        if(categorias.isEmpty()){
            response.put("success",Boolean.FALSE);
            response.put("message",String.format("no se encontro %ss cargadas",nombre_entidad));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<CategoriaDTO> categoriaDTOS = categorias
                .stream()
                .map(mapper::mapCategoria)
                .collect(Collectors.toList());
        response.put("success",Boolean.TRUE);
        response.put("data",categoriaDTOS);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>findById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<Categoria> oCategorias = super.obtenerPorId(id);
        Categoria categoria;
        CategoriaDTO dto = null;

        if (oCategorias.isEmpty()){
            response.put("success",Boolean.FALSE);
            response.put("message",String.format("no existe %s con ID %d",nombre_entidad, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        categoria=oCategorias.get();
        dto=mapper.mapCategoria(categoria);
        response.put("succes",Boolean.TRUE);
        response.put("data",dto);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/findByName/{nombre}")
    public ResponseEntity<?> findByName(@PathVariable String nombre){
        Map<String,Object> response = new HashMap<>();
        Categoria categoria = service.findByName(nombre);

        if (categoria==null){
            response.put("success",Boolean.FALSE);
            response.put("message", String.format("No existe %s con nombre %s", nombre_entidad, nombre));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        CategoriaDTO dto = mapper.mapCategoria(categoria);
        response.put("success",Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/findSimilarName/{likeNombre}")
    public ResponseEntity<?> buscarPorNombreSimilar (@PathVariable String likeNombre){
        Map<String, Object> response = new HashMap<>();
        List<Categoria> categorias = (List<Categoria>) service.buscarPorNombreSimilar(likeNombre);

        if (categorias.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("message",String.format("No existe %s con nombre %s ", nombre_entidad, likeNombre));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<CategoriaDTO> categoriaDTOS = categorias
                .stream()
                .map(mapper::mapCategoria)
                .collect(Collectors.toList());
        response.put("success",Boolean.TRUE);
        response.put("data",categoriaDTOS);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/")
    public ResponseEntity<?> saveCategoria(@Valid @RequestBody Categoria categoria, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        CategoriaDTO dto = null;
        Categoria categoriaLocal = service.findByName(categoria.getNombreCategoria());

        if (result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (categoriaLocal != null){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", nombre_entidad));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Categoria oCategoria = super.altaEntidad(categoria);
        dto = mapper.mapCategoria(oCategoria);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        response.put("message",String.format("La categoria ha sido creada con exito"));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarCategoria(@Valid @RequestBody Categoria categoria,
                                             BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        CategoriaDTO dto = null;
        Optional<Categoria> oCategoria = super.obtenerPorId(id);
        Categoria categoriaUpdate;
        if (result.hasErrors()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        }
        if (oCategoria.isEmpty()){
            response.put("succes", Boolean.FALSE);
            response.put("mensaje", String.format("La %s que se desea editar ya existe", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);

        }

        categoriaUpdate = oCategoria.get();
        categoriaUpdate.setNombreCategoria(categoria.getNombreCategoria());
        categoriaUpdate.setDescripcion(categoria.getDescripcion());
        categoriaUpdate.setGenero(categoria.getGenero());
        Categoria save = super.altaEntidad(categoriaUpdate);
        dto = mapper.mapCategoria(save);
        response.put("succes", Boolean.TRUE);
        response.put("data", dto);
        response.put("message",String.format("La categoria ha sido editada con exito"));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*@DeleteMapping("/{id}") DEBIDO A REGLA DE NEGOCIOS NO IMPLEMENTADO
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<Categoria> categoria = super.optenerPorId(id);
        if (categoria.isEmpty()){
            response.put("message",Boolean.FALSE);
            response.put("message",String.format("No se encontro %s con ID %d",nombre_entidad,id ));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Categoria catEliminada = categoria.get();
        super.eliminarPorId(catEliminada.getId());
        CategoriaDTO dto = mapper.mapCategoria(catEliminada);
        response.put("success",Boolean.TRUE);
        response.put("message",String.format("%s eliminada satisfactoriamente",nombre_entidad));
        response.put("data",dto);
        return ResponseEntity.ok(response);
    }*/

    //Depende de la busqueda asi se usa, iterable, optional o la entidad
    //cateria quiero uno
    //iterable lista
    //optional depende de la implementacion del metodo
}
