/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.clientsHttp;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author britto
 */
@FeignClient(name="msvc-cursos", url= "${msvc.cursos.url}")
public interface CursoClientRest {
    
    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public void eliminarCursoUsuarioPorId(@PathVariable Long id);
    
}
