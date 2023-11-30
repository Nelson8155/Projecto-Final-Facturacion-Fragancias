package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.service.contrato.FacturaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itemFactura")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto", havingValue = "true")
public class ItemFacturaControllerDTO extends GenericoControllerDTO<ItemFactura, ItemFa>{

    @Autowired
    private ItemFactura mapper;

    private final FacturaDAO facturaDAO;


}
