/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.controllers;

import com.britto.natsar.msvc.cursos.models.dto.UsuarioDto;
import com.britto.natsar.msvc.cursos.models.entity.Curso;
import com.britto.natsar.msvc.cursos.services.CursoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author britt
 */

@Controller
public class CursoController {
    
    @Autowired
    private CursoService cursoService;
    
    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok().body(cursoService.listar());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        return ResponseEntity.ok().body(cursoService.porIdConUsuarios(id));
    }
    
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso,BindingResult result){
        if (result.hasErrors())            
            return validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.guardar(curso));
    };
    
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors())            
            return validar(result);
        return ResponseEntity.ok().body(cursoService.editar(curso, id));
    }
    
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId){
       return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.asignarUsuario(usuario, cursoId));
    }
    
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId){
       return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.crearUsuario(usuario, cursoId));
    }
    
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody UsuarioDto usuario, @PathVariable Long cursoId){
       return ResponseEntity.ok().body(cursoService.eliminarUsuarioCurso(usuario, cursoId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        cursoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        cursoService.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
    
    private ResponseEntity<?> validar(BindingResult result) {
        
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + ": " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errores);
    }
    
}
