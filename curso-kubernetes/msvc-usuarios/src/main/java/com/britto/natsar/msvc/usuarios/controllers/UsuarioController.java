/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.controllers;

import com.britto.natsar.msvc.usuarios.models.entity.Usuario;
import com.britto.natsar.msvc.usuarios.services.UsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author britto
 */

@RestController
public class UsuarioController {
 
    @Autowired
    private UsuarioService usuarioService;
    
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listar(){        
        return ResponseEntity.ok().body(usuarioService.listar());        
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        return ResponseEntity.ok().body(usuarioService.porId(id).get());
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){        
        if (result.hasErrors())            
            return validar(result);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result,@PathVariable Long id){
        if (result.hasErrors())            
            return validar(result);
        
        return ResponseEntity.ok().body(usuarioService.editar(usuario, id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam  List<Long> ids){
        return ResponseEntity.ok().body(usuarioService.listaPorId(ids));
    }
    
    private ResponseEntity<?> validar(BindingResult result) {
        
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + ": " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
}
