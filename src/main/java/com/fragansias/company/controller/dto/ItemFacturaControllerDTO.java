package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.service.contrato.FacturaDAO;
import com.fragansias.company.service.contrato.ItemFacturaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/itemFactura")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class ItemFacturaControllerDTO extends GenericoControllerDTO<ItemFactura, ItemFacturaDAO>{

    @Autowired
    private ItemFactura mapper;

    private final FacturaDAO facturaDAO;

    public ItemFacturaControllerDTO(ItemFacturaDAO service, FacturaDAO facturaDAO) {
        super(service, "Factura item");

        this.facturaDAO = facturaDAO;
    }

}
