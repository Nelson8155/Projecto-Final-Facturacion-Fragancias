package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.models.entity.Producto;

import com.fragansias.company.service.contrato.FacturaDAO;
import com.fragansias.company.service.contrato.ItemFacturaDAO;

import com.fragansias.company.models.entity.dto.ItemFacturaDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.ItemFacturaMapper;
import com.fragansias.company.service.contrato.ProductoDAO;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itemFactura")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class ItemFacturaControllerDTO extends GenericoControllerDTO<ItemFactura,ItemFacturaDAO>{

    @Autowired
    private ItemFacturaMapper mapper;

    private final FacturaDAO facturaDAO;
    private final ProductoDAO productoDAO;


    public ItemFacturaControllerDTO(ItemFacturaDAO service, String nombre_entidad, FacturaDAO facturaDAO, FacturaDAO facturaDAO1, ProductoDAO productoDAO) {
        super(service, nombre_entidad);
        this.facturaDAO = facturaDAO1;

        this.productoDAO = productoDAO;
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProductos (@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<ItemFactura> items = super.obtenerPorId(id);

        if(items.isEmpty()){
            response.put("message",Boolean.FALSE);
            response.put("message",String.format("El item con id #%d no se encontro",nombre_entidad,id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ItemFactura itemEliminado = items.get();
        super.eliminarPorId(itemEliminado.getId());
        ItemFacturaDTO dto = mapper.mapItemFactura(itemEliminado);
        response.put("success",Boolean.TRUE);
        response.put("message",String.format("%s eliminados con exito",nombre_entidad));
        response.put("data",dto);
        return ResponseEntity.ok(response);

    }



    @GetMapping("/")
    public ResponseEntity <?> getAllItems(){
        Map<String, Object> response = new HashMap<>();
        List<ItemFactura> itemFacturas = super.obtenerTodos();
        if (itemFacturas.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("message", String.format("No se encontraron %ss cargados", nombre_entidad));
            return ResponseEntity.badRequest().body(response);
        }
        List<ItemFacturaDTO> dtos = itemFacturas.stream().map(mapper::mapItemFactura).collect(Collectors.toList());
        response.put("success", Boolean.TRUE);
        response.put("data", dtos);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/facturaid/{idFactura}/crearItemFactura/")
    public ResponseEntity<?> guardarFactra(@Valid @RequestBody ItemFacturaDTO facturaDTO, BindingResult result, @PathVariable Long idFactura){
        Map<String,Object> response=new HashMap<>();
        Optional<Factura> oFactura = facturaDAO.findById(idFactura);
        Optional<Producto> oProducto = productoDAO.findById(facturaDTO.getProducto().getId());
        if(oFactura.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", String.format("La factura con el id #%d no existe"));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if(result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        }
        if(oProducto.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validacion", "El producto que deseas agrega no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        facturaDTO.setFactura(oFactura.get());
        facturaDTO.setProducto(oProducto.get());
        facturaDTO.setPrecioTotal(oProducto.get().getPrecio() * facturaDTO.getCantidad());
        ItemFactura save = super.altaEntidad(mapper.mapItemFactura(facturaDTO));
        ItemFacturaDTO dto = mapper.mapItemFactura(save);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/facturaid/{idFactura}/editItemFacturaId/{idItemFActura}")
    public ResponseEntity<?> editarFactra(@Valid @RequestBody ItemFacturaDTO facturaDTO, BindingResult result, @PathVariable Long idFactura, @PathVariable long idItemFactura){
        Map<String,Object> response=new HashMap<>();
        Optional<Factura> oFactura = facturaDAO.findById(idFactura);
        Optional<Producto> oProducto = productoDAO.findById(facturaDTO.getProducto().getId());
        Optional<ItemFactura> oItemFActura = super.obtenerPorId(idItemFactura);
        if(oItemFActura.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", String.format("El item factura con el id #%d no existe", idItemFactura));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if(oFactura.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", String.format("La factura con el id #%d no existe", idFactura));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        if(result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        }
        if(oProducto.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validacion", "El producto que deseas agrega no existe");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ItemFactura facturaUpdate = oItemFActura.get();
        facturaUpdate.setFactura(oFactura.get());
        facturaUpdate.setProducto(oProducto.get());
        facturaUpdate.setCantidad(facturaDTO.getCantidad());
        facturaUpdate.setPrecioTotal(oProducto.get().getPrecio() * facturaDTO.getCantidad());
        super.altaEntidad(facturaUpdate);
        ItemFacturaDTO dto = mapper.mapItemFactura(facturaUpdate);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }




}
