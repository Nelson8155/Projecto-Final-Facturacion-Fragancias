package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.service.contrato.FacturaDAO;
import com.fragansias.company.service.contrato.ItemFacturaDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/itemFactura")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")

public class ItemFacturaControllerDTO extends GenericoControllerDTO<ItemFactura,ItemFacturaDAO>{

    @Autowired
    private ItemFactura mapper;

    private final FacturaDAO facturaDAO;

    public ItemFacturaControllerDTO(ItemFacturaDAO service, String nombre_entidad, FacturaDAO facturaDAO) {
        super(service, nombre_entidad);
        this.facturaDAO = facturaDAO;
    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProductos (@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Optional<ItemFactura> items = super.obtenerPorId(id);
        return null;

    }


}
