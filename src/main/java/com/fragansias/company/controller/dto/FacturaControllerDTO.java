package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.models.entity.Factura;
import com.fragansias.company.models.entity.ItemFactura;
import com.fragansias.company.models.entity.Producto;
import com.fragansias.company.models.entity.dto.ClienteDTO;
import com.fragansias.company.models.entity.dto.FacturaDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.FacturaMapper;
import com.fragansias.company.service.contrato.ClienteDAO;
import com.fragansias.company.service.contrato.FacturaDAO;
import com.fragansias.company.service.contrato.ProductoDAO;
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
@RequestMapping("/factura")
@ConditionalOnProperty(prefix = "app",name = "controller.enable-dto",havingValue = "true")
public class FacturaControllerDTO extends GenericoControllerDTO<Factura, FacturaDAO>{

    @Autowired
    private FacturaMapper mapper;
    private final ClienteDAO clienteDAO;

    public FacturaControllerDTO(FacturaDAO service, ProductoDAO productoDAO, ClienteDAO clienteDAO) {
        super(service, "factura");


        this.clienteDAO = clienteDAO;
    }
    @GetMapping("/")
    public ResponseEntity<?> listarFactura(){
        Map<String, Object> response = new HashMap<>();
        List<Factura> facturas = super.obtenerTodos();

        if(facturas.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", "No existe ninguna factura");
            return ResponseEntity.badRequest().body(response);
        }
        List<FacturaDTO> dtos = facturas .stream()
                .map(mapper::mapFactura)
                .collect(Collectors.toList());
        response.put("message",Boolean.TRUE);
        response.put("data",dtos);

        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/saveFactura/clienteId/{clienteId}/")
    public ResponseEntity<?> saveFactura(@Valid @RequestBody FacturaDTO factura, BindingResult result, @PathVariable Long clienteId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Cliente> cliente= clienteDAO.findById(clienteId);

        if (result.hasErrors()) {
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        }

        if(cliente.isEmpty()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", "El cliente que deseas agregar a la factura no existe");
            return ResponseEntity.badRequest().body(response);
        }


        factura.setClientes(cliente.get());
        Factura oFactura = super.altaEntidad(mapper.mapDTOFactura(factura));
        FacturaDTO dto = mapper.mapFactura(oFactura);
        response.put("success", Boolean.TRUE);
        response.put("data",dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/editFactura/{id}")
    public ResponseEntity<?> editarFactura(@Valid @RequestBody FacturaDTO factura,
                                             BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        FacturaDTO dto = null;
        Optional<Factura> oFactura = super.obtenerPorId(id);
        Factura facturaUpdate;

        if (result.hasErrors()) {
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);

        }
        if (oFactura.isEmpty()){
            response.put("succes", Boolean.FALSE);
            response.put("mensaje", String.format("La %s que se desea editar con ID  %d no existe", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);

        }

        facturaUpdate = oFactura.get();
        facturaUpdate.setDescripcion(factura.getDescripcion());
        Factura save = super.altaEntidad(facturaUpdate);
        dto = mapper.mapFactura(save);
        response.put("succes", Boolean.TRUE);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
