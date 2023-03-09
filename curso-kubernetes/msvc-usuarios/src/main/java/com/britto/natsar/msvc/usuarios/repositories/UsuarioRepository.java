/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.repositories;

import com.britto.natsar.msvc.usuarios.models.entity.Usuario;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author britto
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    
    Optional<Usuario> findByEmail(String email);
    
}
