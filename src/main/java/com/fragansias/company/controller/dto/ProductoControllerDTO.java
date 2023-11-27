package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.models.entity.dto.ProductoDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.ProductoMapper;
import com.fragansias.company.service.contrato.CategoriaDAO;
import com.fragansias.company.service.contrato.ProductoDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/producto")
@ConditionalOnProperty(prefix = "app",name = "controller.enable-dto",havingValue = "true")//PARA PODER ACTIVAR LA CONFIG DEL DTO
public class ProductoControllerDTO extends GenericoControllerDTO<Producto, ProductoDAO> {
    @Autowired
    private ProductoMapper mapper;

    private final CategoriaDAO categoriaDAO;

    public ProductoControllerDTO(ProductoDAO service, CategoriaDAO categoriaDAO) {
        super(service, "producto");
        this.categoriaDAO = categoriaDAO;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos = super.obtenerTodos();

        if (productos.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("messaje", String.format("no se encontro %ss cargadas", nombre_entidad));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ProductoDTO> productoDTOS = productos
                .stream()
                .map(mapper::mapProducto)
                .collect(Collectors.toList());
        response.put("success", Boolean.TRUE);
        response.put("data", productoDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Producto> oProductos = super.obtenerPorId(id);
        Producto producto;
        ProductoDTO dto = null;

        if (oProductos.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("no existe %s con ID %d", nombre_entidad, id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        producto = oProductos.get();
        dto = mapper.mapProducto(producto);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/codeSearch/{codigo}")
    public ResponseEntity<?> searchByCode(@PathVariable String codigo) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos = service.obtenerPorCodigo(codigo);

        if (productos.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("no existe %s con Codigo %s ", nombre_entidad, codigo));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ProductoDTO> productoDTOS = productos
                .stream()
                .map(mapper::mapProducto)
                .collect(Collectors.toList());
        response.put("success", Boolean.TRUE);
        response.put("data", productoDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categorySearch/{categoria}")
    public ResponseEntity<?> searchByCategory(@PathVariable String categoria) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos = (List<Producto>) service.obtenerPorCategoria(categoria);

        if (productos.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("No existen %s en la categoría %s", nombre_entidad, categoria));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ProductoDTO> productoDTOS = productos
                .stream()
                .map(mapper::mapProducto)
                .collect(Collectors.toList());

        response.put("success", Boolean.TRUE);
        response.put("data", productoDTOS);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/findByName/{nombre}")
    public ResponseEntity<?> findByName(@PathVariable String nombre){
        Map<String,Object> response = new HashMap<>();
        Producto producto = service.findByName(nombre);

        if (producto==null){
            response.put("success",Boolean.FALSE);
            response.put("message", String.format("No existe %s con nombre %s", nombre_entidad, nombre));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ProductoDTO dto = mapper.mapProducto(producto);
        response.put("success",Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/findSimilarName/{nombre}")
    public ResponseEntity<?> searchBySimilarName(@PathVariable String nombre) {
        Map<String, Object> response = new HashMap<>();
        List<Producto> productos = service.obtenerPorNombreSimilar(nombre);

        if (productos.isEmpty()) {
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("No existen %s con el nombre %s", nombre_entidad, nombre));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ProductoDTO> productoDTOS = productos
                .stream()
                .map(mapper::mapProducto)
                .collect(Collectors.toList());
        response.put("success", Boolean.TRUE);
        response.put("data", productoDTOS);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/")
    public ResponseEntity<?> saveProducto(@Valid @RequestBody Producto producto, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        ProductoDTO dto = null;
        Producto productoLocal = service.findByName(producto.getNombreProducto());
        Categoria categoriaLocal = categoriaDAO.findByName(producto.getCategoria().getNombreCategoria());

        if (result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (productoLocal != null){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", nombre_entidad));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (categoriaLocal == null){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("No puedes gardar este %s por que la categoria no existe!", nombre_entidad));
            return ResponseEntity.badRequest().body(response);
        }
        producto.setCategoria(categoriaLocal);
        Producto oProducto = super.altaEntidad(producto);
        dto = mapper.mapProducto(oProducto);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Producto producto,
                                           BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        ProductoDTO dto = null;
        Optional<Producto> oProducto = super.obtenerPorId(id);
        Producto productoUpdate;
        if (result.hasErrors()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        }
        if (oProducto.isEmpty()){
            response.put("mensaje", String.format("La %s que se desea editar ya existe", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);
        }
        productoUpdate = oProducto.get();
        productoUpdate.setNombreProducto(producto.getNombreProducto());
        productoUpdate.setCodigoProducto(producto.getCodigoProducto());
        productoUpdate.setCategoria(producto.getCategoria());
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteById(@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<Producto> producto = super.obtenerPorId(id);
        if (producto.isEmpty()){
            response.put("message",Boolean.FALSE);
            response.put("message",String.format("No se encontro %s con ID %d",nombre_entidad,id ));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Producto proEliminado = producto.get();
        super.eliminarPorId(proEliminado.getId());
        ProductoDTO dto = mapper.mapProducto(proEliminado);
        response.put("success",Boolean.TRUE);
        response.put("message",String.format("%s eliminada satisfactoriamente",nombre_entidad));
        response.put("data",dto);
        return ResponseEntity.ok(response);
    }
}
