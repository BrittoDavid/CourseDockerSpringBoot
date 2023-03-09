/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.clientsHttp;

import com.britto.natsar.msvc.cursos.models.dto.UsuarioDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author britto
 */
@FeignClient(name="msvc-usuarios", url= "${msvc.usuarios.url}")
public interface UsuarioClientRest {
    
    @GetMapping("/{id}")
    public UsuarioDto detalle(@PathVariable Long id);
    
    @PostMapping("/")
    public UsuarioDto crear(@RequestBody UsuarioDto usuario);
    
    @GetMapping("/usuarios-por-curso")
    List<UsuarioDto> obtenerAlumnosPorCurso(@RequestParam  Iterable<Long> ids);
}
