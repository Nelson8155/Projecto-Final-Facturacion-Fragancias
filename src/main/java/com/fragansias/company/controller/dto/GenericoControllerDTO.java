package com.fragansias.company.controller.dto;

import com.fragansias.company.service.contrato.GenericoDAO;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@AllArgsConstructor
public class GenericoControllerDTO<E,S extends GenericoDAO<E>> {
    protected final S sevice;
    protected final String nombre_entidad;

    public List<E> obtenerTodos() {
        return (List<E>) sevice.findAll();
    }
    public Optional<E> obtenerPorId(Long id){
        return (Optional<E>) sevice.findById(id);
    }

    /*public Optional<E> eliminarPorId(Long id){
      return sevice.deleteById(id);
    }*/
    void eliminarPorId(Long id){
        sevice.deleteById(id);
    }

    public E altaEntidad(E entidad){
        return sevice.save(entidad);
    }
    protected Map<String,Object> obtenerValidaciones(BindingResult result){
        Map<String,Object> validaciones = new HashMap<>();
        result.getFieldErrors()
                .forEach(error -> validaciones.put(error.getField(),error.getDefaultMessage()));
        return validaciones;
    }
}
