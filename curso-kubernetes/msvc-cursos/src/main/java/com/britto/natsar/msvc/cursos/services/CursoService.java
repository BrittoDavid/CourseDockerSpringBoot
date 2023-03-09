/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.services;

import com.britto.natsar.msvc.cursos.models.dto.UsuarioDto;
import com.britto.natsar.msvc.cursos.models.entity.Curso;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author britt
 */
public interface CursoService {
    
    List<Curso> listar();
    Optional<Curso> porID(Long id);
    Optional<Curso> porIdConUsuarios(Long id);
    Curso guardar(Curso curso);
    Curso editar(Curso curso,Long id);
    void eliminar(Long id);
    void eliminarCursoUsuarioPorId(Long id);
    Optional<UsuarioDto> asignarUsuario(UsuarioDto usuario, Long cursoId);
    Optional<UsuarioDto> crearUsuario(UsuarioDto usuario, Long cursoId);
    Optional<UsuarioDto> eliminarUsuarioCurso(UsuarioDto usuario, Long cursoId);
    
}
