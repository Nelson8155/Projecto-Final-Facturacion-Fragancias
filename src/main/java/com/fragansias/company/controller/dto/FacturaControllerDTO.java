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

    /*public FacturaControllerDTO(FacturaDAO service, ProductoDAO productoDAO, ClienteDAO clienteDAO) {
        super(service, "factura");*/
    public FacturaControllerDTO(FacturaDAO service, ClienteDAO clienteDAO) {
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


        factura.setCliente(cliente.get());
        Factura oFactura = super.altaEntidad(mapper.mapDTOFactura(factura));
        FacturaDTO dto = mapper.mapFactura(oFactura);
        response.put("success", Boolean.TRUE);
        response.put("data",dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    //ESTE ES EL METODO DE GUARDAR QUE AGREGE YO

    @PostMapping("/")
    public ResponseEntity <?> saveFactura(@Valid @RequestBody FacturaDTO factura, BindingResult result){
        Map<String, Object> response = new HashMap<>();
        Optional<Factura> facturaLocal = service.findById(factura.getId_factura());
        Optional<Cliente> clienteLocal = clienteDAO.findById(factura.getCliente().getId());

        if (result.hasErrors()){
            response.put("success", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (facturaLocal.isPresent()) {
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("La %s que se desea crear ya existe", factura.getId_factura()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if (clienteLocal.isEmpty()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", String.format("No puedes guardar esta %s por que el ciente no existe!", factura.getCliente().getId()));
            return ResponseEntity.badRequest().body(response);
        }
        factura.setCliente(clienteLocal.get());
        Factura facturaSave = super.altaEntidad(mapper.mapDTOFactura(factura));
        FacturaDTO dto = mapper.mapFactura(facturaSave);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
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

    //ESTE ES EL METODO DE EDITAR QUE AGREGE YO

    @PutMapping("/{id}")
    public ResponseEntity updateProducto(@Valid @RequestBody Factura factura,
                                         BindingResult result, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        Optional<Factura> facturaLocal = super.obtenerPorId(id);
        Optional<Cliente> clienteLocal = clienteDAO.findById(factura.getCliente().getId());
        Factura facturaUpdate = null;
        if (result.hasErrors()){
            response.put("succes", Boolean.FALSE);
            response.put("validaciones", super.obtenerValidaciones(result));
            return ResponseEntity.badRequest().body(response);
        } else if (clienteLocal.isEmpty()){
            response.put("mensaje", String.format("El ID de %s que desea guardar con el %d no existe!", nombre_entidad, id));
            return ResponseEntity.badRequest().body(response);
        }
        if (facturaLocal.get().getId().equals(factura.getId())){
            facturaUpdate = facturaLocal.get();
            facturaUpdate.setDescripcion(factura.getDescripcion());
        } else { Optional<Factura> buscarFactura = service.findById(factura.getId());
            if (buscarFactura.isPresent()) {
                response.put("mensaje", String.format("El %s que se desea editar ya existe %d", nombre_entidad, id));
                return ResponseEntity.badRequest().body(response);
            }}
        Factura facturaSave = super.altaEntidad(facturaUpdate);
        FacturaDTO dto = mapper.mapFactura(facturaSave);
        response.put("success", Boolean.TRUE);
        response.put("data", dto);
        return ResponseEntity.ok(response);
    }
}
