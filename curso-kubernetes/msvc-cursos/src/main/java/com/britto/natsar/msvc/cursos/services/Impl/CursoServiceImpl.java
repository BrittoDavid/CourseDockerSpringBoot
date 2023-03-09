/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.services.Impl;

import com.britto.natsar.msvc.cursos.clientsHttp.UsuarioClientRest;
import com.britto.natsar.msvc.cursos.models.dto.UsuarioDto;
import com.britto.natsar.msvc.cursos.models.entity.Curso;
import com.britto.natsar.msvc.cursos.models.entity.CursoUsuario;
import com.britto.natsar.msvc.cursos.repositories.CursoRepository;
import com.britto.natsar.msvc.cursos.services.CursoService;
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
public class CursoServiceImpl implements CursoService{

    private String messageCursoNoFound = "Curso no encontrado";
    private String messageUsuarioNoFound = "Usuario no encontrado";
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private UsuarioClientRest usuarioRest;
    
    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        List<Curso> listCurso = new ArrayList<>();
        Iterable<Curso> curso =  cursoRepository.findAll();
        curso.forEach(listCurso::add);
        return listCurso;     
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porID(Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        
        if (curso.isPresent())                    
            return curso;
        
        throw new Error(messageCursoNoFound);
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
         return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public Curso editar(Curso curso, Long id) {
     Boolean exists = cursoRepository.existsById(id);
        
        curso.setId(id);
        
        if(exists)
            return cursoRepository.save(curso);
        
        throw new Error(messageUsuarioNoFound);
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
         Boolean exists = cursoRepository.existsById(id);
        
        if(exists)
            cursoRepository.deleteById(id);
        else
        throw new Error(messageCursoNoFound);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> asignarUsuario(UsuarioDto usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        
        if (cursoOptional.isPresent()) {
            
            try {
                
                UsuarioDto usuarioMsvc = usuarioRest.detalle(usuario.getId());            
                Curso curso = cursoOptional.get();
                CursoUsuario cursoUsuario = new CursoUsuario();
                cursoUsuario.setUsuarioId(usuarioMsvc.getId());
                curso.addCursoUsuario(cursoUsuario);
                cursoRepository.save(curso);
                
                return Optional.of(usuarioMsvc); 
                
            } catch (Exception e) {                
                throw new Error("{[]} Error: asignarUsuario , message {}" + e);                
            }                       
        }
        
        throw new Error(messageCursoNoFound);
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> crearUsuario(UsuarioDto usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        
        if (cursoOptional.isPresent()) {
            
            try {
                
                UsuarioDto usuarioNuevoMsvc = usuarioRest.crear(usuario);

                Curso curso = cursoOptional.get();
                CursoUsuario cursoUsuario = new CursoUsuario();
                cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

                curso.addCursoUsuario(cursoUsuario);
                cursoRepository.save(curso);
                return Optional.of(usuarioNuevoMsvc);            
                
            } catch (Exception e) {
                throw new Error("{[]} Error: crearUsuario , message {}" + e);
            }
            
                
        }
        
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<UsuarioDto> eliminarUsuarioCurso(UsuarioDto usuario, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        
        if (cursoOptional.isPresent()) {
            
            try {
                
                UsuarioDto usuarioMsvc = usuarioRest.detalle(usuario.getId());
            
                Curso curso = cursoOptional.get();
                CursoUsuario cursoUsuario = new CursoUsuario();
                cursoUsuario.setUsuarioId(usuarioMsvc.getId());

                curso.removeCursoUsuario(cursoUsuario);
                cursoRepository.save(curso);
                return Optional.of(usuarioMsvc);
                
            } catch (Exception e) {
                throw new Error("{[]} Error: eliminarUsuarioCurso , message {}" + e);
            }          
        }
        
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> cursoOptional =  cursoRepository.findById(id);
        
        try {
        
            if (cursoOptional.isPresent()) {
                Curso curso = cursoOptional.get();
                if (curso.getCursoUsuarios().hashCode() > 0) {
                    List<Long> ids = curso.getCursoUsuarios()
                            .stream()
                            .map(data -> data.getUsuarioId())
                            .toList();

                    List<UsuarioDto> usuarios = usuarioRest.obtenerAlumnosPorCurso(ids);
                    curso.setUsuarios(usuarios);            
                }
                
                return Optional.of(curso);
            }            
        } catch (Exception e) {
            throw new Error("{[]} Error: porIdConUsuarios , message {}" + e);
        }  
        
        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        cursoRepository.eliminarCursoUsuarioPorId(id);
    }
}