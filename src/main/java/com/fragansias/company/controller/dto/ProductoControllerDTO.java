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
        Optional<Producto> producto = service.findByName(nombre);

        if (producto==null){
            response.put("success",Boolean.FALSE);
            response.put("message", String.format("No existe %s con nombre %s", nombre_entidad, nombre));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ProductoDTO dto = mapper.mapProducto(producto.get());
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
    public ResponseEntity<?> saveProducto(@Valid @RequestBody ProductoDTO producto, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Optional<Producto> productoLocal = service.findByName(producto.getNombreProducto());
        Optional<Categoria> categoriaLocal = categoriaDAO.findByName(producto.getCategoria().getNombreCategoria());

        if (result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (productoLocal.isPresent()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", producto.getNombreProducto()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (categoriaLocal.isEmpty()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("No puedes gardar este %s por que la categoria no existe!", producto.getCategoria().getNombreCategoria()));
            return ResponseEntity.badRequest().body(response);
        }
        producto.setCategoria(categoriaLocal.get());
        Producto productoSave = super.altaEntidad(mapper.mapDTOProducto(producto));
        ProductoDTO dto = mapper.mapProducto(productoSave);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Producto producto,
                                           BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Producto> productoLocal = super.obtenerPorId(id);
        Optional<Categoria> categoriaLocal = categoriaDAO.findByName(producto.getCategoria().getNombreCategoria());
        Producto productoUpdate = null;
        if (result.hasErrors()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (categoriaLocal.isEmpty()) {
            response.put("mensaje", String.format("La categoria que desea guardar con el %s no existe!", nombre_entidad));
            return ResponseEntity.badRequest().body(response);
        }
        if (productoLocal.isEmpty()){
            response.put("mensaje", String.format("El ID de %s que desea guardar con el %d no existe!", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);
        }
        if (productoLocal.get().getNombreProducto().equals(producto.getNombreProducto())) {
            productoUpdate = productoLocal.get();
            productoUpdate.setNombreProducto(producto.getNombreProducto());
            productoUpdate.setCodigoProducto(producto.getCodigoProducto());
            productoUpdate.setPrecio(producto.getPrecio());
            productoUpdate.setPresentacion(producto.getPresentacion());
            productoUpdate.getDetalleProducto().setDescripcion(producto.getDetalleProducto().getDescripcion());
            productoUpdate.getDetalleProducto().setTipoFrasco(producto.getDetalleProducto().getTipoFrasco());

        } else {Optional<Producto> buscarProducto = service.findByName(producto.getNombreProducto());
            if (buscarProducto.isPresent()) {
                response.put("mensaje", String.format("El %s que se desea editar ya existe %d", nombre_entidad, id));
                return ResponseEntity.badRequest().body(response);
            }}

        Producto productoSave = super.altaEntidad(productoUpdate);
        ProductoDTO dto = mapper.mapProducto(productoSave);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
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
