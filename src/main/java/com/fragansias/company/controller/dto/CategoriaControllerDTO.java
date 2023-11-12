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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categoria")
@ConditionalOnProperty(prefix = "app",name = "controller.enable-dto",havingValue = "true")//PARA PODER ACTIVAR LA CONFIG DEL DTO
public class CategoriaControllerDTO extends GenericoControllerDTO<Categoria, CategoriaDAO> {
    @Autowired
    private CategoriaMapper mapper;
    public CategoriaControllerDTO(CategoriaDAO sevice) {
        super(sevice,"categoria");
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
        response.put("!success!",Boolean.TRUE);
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
        response.put("message",String.format("no existe %s con ID %d",nombre_entidad));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        categoria=oCategorias.get();
        dto=mapper.mapCategoria(categoria);
        response.put("succes",Boolean.TRUE);
        response.put("data",dto);
        return ResponseEntity.ok().body(response);
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

    @PostMapping("/")
    public ResponseEntity<?> saveCategoria(@Valid @RequestBody Categoria categoria, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        CategoriaDTO dto = null;
        Categoria categoriaLocal = sevice.findByName(categoria.getNombreCategoria());

        if (result.hasErrors()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (categoriaLocal != null){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", nombre_entidad));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Categoria oCategoria = super.altaEntidad((Categoria) categoria);
        dto = mapper.mapCategoria(oCategoria);
        response.put("succes", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/id")
    public ResponseEntity<?> editarCategoria(@Valid @RequestBody Categoria categoria, BindingResult result, @PathVariable Long id){
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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
