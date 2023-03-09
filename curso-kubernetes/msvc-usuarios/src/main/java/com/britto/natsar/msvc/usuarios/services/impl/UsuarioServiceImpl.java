/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.services.impl;

import com.britto.natsar.msvc.usuarios.clientsHttp.CursoClientRest;
import com.britto.natsar.msvc.usuarios.models.entity.Usuario;
import com.britto.natsar.msvc.usuarios.repositories.UsuarioRepository;
import com.britto.natsar.msvc.usuarios.services.UsuarioService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author britto
 */

@Service
public class UsuarioServiceImpl implements UsuarioService{
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CursoClientRest clientRest;
    
    private String messageErrorEmail = "Ya existe un usuario con ese correo";
    private String messageErrorUsuarioNoFound = "Usuario no encontrado";
    
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        List<Usuario> listUsuario = new ArrayList<>();
        Iterable<Usuario> usuario =  usuarioRepository.findAll();
        usuario.forEach(listUsuario::add);
        return listUsuario;        
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        
        if (usuario.isPresent())                    
            return usuario;
        
        throw new Error(messageErrorUsuarioNoFound);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        
        if (porEmail(usuario.getEmail()).isPresent())
            throw new Error(messageErrorEmail);
        
        return usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public Usuario editar(Usuario usuario, Long id) {
        
        Optional<Usuario> usuarioDb = usuarioRepository.findById(id);
        
        usuario.setId(id);
        
        if(usuarioDb.isPresent()){
            if (!usuario.getEmail().equalsIgnoreCase(usuarioDb.get().getEmail()) && porEmail(usuario.getEmail()).isPresent()) {
                throw new Error(messageErrorEmail);
            }
            return usuarioRepository.save(usuario);
        }
        
        throw new Error(messageErrorUsuarioNoFound);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        
        Boolean exists = usuarioRepository.existsById(id);
        
        if(exists){            
            usuarioRepository.deleteById(id);
            clientRest.eliminarCursoUsuarioPorId(id);
        }else{
            throw new Error(messageErrorUsuarioNoFound);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porEmail(String email) {
       return  usuarioRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listaPorId(Iterable ids) {
        return (List<Usuario>) usuarioRepository.findAllById(ids);
    }

}
