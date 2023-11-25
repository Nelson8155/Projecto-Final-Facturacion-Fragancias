package com.fragansias.company.controller.dto;

import com.fragansias.company.models.entity.Cliente;
import com.fragansias.company.models.entity.dto.ClienteDTO;
import com.fragansias.company.models.entity.mapper.mapstruct.ClienteMapper;
import com.fragansias.company.service.contrato.ClienteDAO;
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
@RequestMapping("/clientes")
@ConditionalOnProperty(prefix = "app", name = "controller.enable-dto",havingValue = "true")
public class ClienteControllerDTO extends GenericoControllerDTO<Cliente, ClienteDAO>{
    @Autowired
    private ClienteMapper mapper;


    public ClienteControllerDTO(ClienteDAO sevice, ClienteMapper mapper, ClienteDAO service) {
        super(sevice, "Cliente");

    }
    @GetMapping("/{id}")
        public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Cliente> clientes = super.obtenerPorId(id);
        Map<String, Object> response = new HashMap<>();
        Cliente cliente;
        ClienteDTO dto = null;

        if (clientes.isEmpty()){
            response.put("success",Boolean.FALSE);
            response.put("messagge",String.format("no existe %s con ID %d",nombre_entidad,id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        cliente = clientes.get();
        dto=mapper.mapCliente(cliente);
        response.put("message",Boolean.TRUE);
        response.put("data",dto);
        return ResponseEntity.ok().body(response);

    }
    @GetMapping("/findAll/")
    public ResponseEntity<?> findAll(){
        List<Cliente> clientes = super.obtenerTodos();
        Map<String,Object> response = new HashMap<>();

        if (clientes.isEmpty()){
            response.put("message",Boolean.FALSE);
            response.put("success",String.format("no se encontro %ss cargadas",nombre_entidad));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ClienteDTO> clienteDTOS = clientes
                .stream()
                .map(mapper::mapCliente)
                .collect(Collectors.toList());
        response.put("message",Boolean.TRUE);
        response.put("data",clienteDTOS);
        return ResponseEntity.ok().body(response);

    }

    @GetMapping("/findByName/{name}/lastName/{apellido}")
    public ResponseEntity<?> searchByNameAndLastName(@PathVariable String name,@PathVariable String apellido){
        Map<String,Object> response = new HashMap<>();
        Optional<Cliente> clientes = service.buscarPorNombreYApellido(name, apellido);

        if(!clientes.isPresent()){
            response.put("success",Boolean.FALSE);
            response.put("validaciones",String.format("No se encontro Cliente con nombre %s y apellido %s",name,apellido));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ClienteDTO clienteDTOS = mapper.mapCliente(clientes.get());
        response.put("success",Boolean.FALSE);
        response.put("data",clienteDTOS);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/findByNit/{nit}")
    public ResponseEntity<?> findByNit(@PathVariable String nit){
        Optional<Cliente> clientes = service.buscarPorNit(nit);
        Map<String,Object> response = new HashMap<>();
        Cliente cliente;
        ClienteDTO clienteDTO;

        if(clientes.isEmpty()){
            response.put("success",Boolean.FALSE);
            response.put("message",String.format("No se encontro Cliente con Nit %s ",nit));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        cliente = clientes.get();
        clienteDTO = mapper.mapCliente(cliente);
        response.put("message",Boolean.TRUE);
        response.put("data",clienteDTO);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email){
        List<Cliente> clientes = service.buscarPorCorreo(email);
        Map<String,Object> response = new HashMap<>();

        if (clientes.isEmpty()){
            response.put("message",Boolean.FALSE);
            response.put("success",String.format("No se encontro %s con Email %s",nombre_entidad));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        List<ClienteDTO> clienteDTOS= clientes
                .stream()
                .map(mapper::mapCliente)
                .collect(Collectors.toList());
        response.put("message",Boolean.TRUE);
        response.put("Data",clienteDTOS);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/saveProducto")
    public ResponseEntity<?> saveCliente (@Valid @RequestBody Cliente cliente, BindingResult result){
        Map<String,Object> response = new HashMap<>();
        ClienteDTO dto = null;
       Optional<Cliente> clienteLocal = service.findByName(cliente.getNombre());

       if(result.hasErrors()){
           response.put("success",Boolean.FALSE);
           response.put("validaciones",super.obtenerValidaciones(result));
           return ResponseEntity.badRequest().body(response);
       } else if (clienteLocal.isPresent()) {
           response.put("success",Boolean.FALSE);
           response.put("validaciones",String.format("La %s que se desea crear ya existe",nombre_entidad));
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
       }
       Cliente clientes = super.altaEntidad(cliente);
       dto = mapper.mapCliente(clientes);
       response.put("success",Boolean.TRUE);
       response.put("data",dto);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody ClienteDTO clienteDTO,
                                           BindingResult result,@PathVariable Long id){
        Map<String,Object> response = new HashMap<>();
        Cliente cliente=null;
        Optional<Cliente> oCliente = super.obtenerPorId(id);
        Cliente clienteUpdate;

        if(result.hasErrors()){
            response.put("success",Boolean.FALSE);
            response.put("validaciones",super.obtenerValidaciones(result));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(!oCliente.isPresent()
        ){
            response.put("mensaje",String.format("La %s que se desea editar ya existe",nombre_entidad,id));
            return ResponseEntity.badRequest().body(response);
        }
        clienteUpdate = oCliente.get();
        clienteUpdate.setNombre(clienteUpdate.getNombre());
        clienteUpdate.setApellido(clienteUpdate.getApellido());
        clienteUpdate.setTelefono(clienteUpdate.getTelefono());
        clienteUpdate.setEmail(clienteUpdate.getEmail());
        clienteUpdate.setEmail(cliente.getEmail());

        response.put("datos",service.save(clienteUpdate));
        response.put("success",Boolean.TRUE);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
