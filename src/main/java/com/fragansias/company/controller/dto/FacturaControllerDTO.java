package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Categoria;
import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.dto.FacturaDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.CategoriaMapper;
import com.fragansias.company.models.entity.mapper.mapstruct.FacturaMapper;
import com.fragansias.company.service.contrato.CategoriaDAO;
import com.fragansias.company.service.contrato.FacturaDAO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/factura")
@ConditionalOnProperty(prefix = "app",name = "controller.enable-dto",havingValue = "true")
public class FacturaControllerDTO extends GenericoControllerDTO<Factura, FacturaDAO>{

    @Autowired
    private FacturaMapper mapper;

    public FacturaControllerDTO(FacturaDAO service) {
        super(service, "factura");

    }

    @PostMapping("/saveFactura")
    public ResponseEntity<?> saveFactura(@Valid @RequestBody FacturaDTO factura, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        FacturaDTO dto = null;
        Factura facturaLocal = super.altaEntidad(mapper.mapDTOFactura(factura));

        if (result.hasErrors()) {
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);

        } else if (facturaLocal != null) {
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", nombre_entidad));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Factura oFactura = super.altaEntidad(mapper.mapDTOFactura(factura));
        dto = mapper.mapFactura(oFactura);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/editFactura/{id}")
    public ResponseEntity<?> editarFactura(@Valid @RequestBody Factura factura,
                                             BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        FacturaDTO dto = null;
        Optional<Factura> oFactura = super.obtenerPorId(id);
        Factura facturaUpdate;

        if (result.hasErrors()) {
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);

    }if (oFactura.isEmpty()){
            response.put("succes", Boolean.FALSE);
            response.put("mensaje", String.format("La %s que se desea editar con ID  %d ya existe", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);

        }
        facturaUpdate = oFactura.get();
        facturaUpdate.setItemFacturas(factura.getItemFacturas());
        facturaUpdate.setDescripcion(factura.getDescripcion());
        facturaUpdate.setCliente(factura.getCliente());
        factura.setCreateAt(factura.getCreateAt());
        Factura save = super.altaEntidad(facturaUpdate);
        dto = mapper.mapFactura(save);
        response.put("succes", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
