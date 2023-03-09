/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.services;

import com.britto.natsar.msvc.usuarios.models.entity.Usuario;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author britto
 */
public interface UsuarioService {
    
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario editar(Usuario usuario, Long id);
    Optional<Usuario> porEmail(String email);
    List<Usuario> listaPorId(Iterable ids);
}
