/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.models.entity;

import com.britto.natsar.msvc.cursos.models.dto.UsuarioDto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.*;
import lombok.Data;

/**
 *
 * @author britto
 */
@Entity
@Table(name= "cursos")
@Data
public class Curso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @NotBlank
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;
    
    @Transient
    private List<UsuarioDto> usuarios;
    
    public Curso(){
        usuarios = new ArrayList<>();
        cursoUsuarios = new ArrayList<>();
    }
    
    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }
    
    
    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }
    
}
