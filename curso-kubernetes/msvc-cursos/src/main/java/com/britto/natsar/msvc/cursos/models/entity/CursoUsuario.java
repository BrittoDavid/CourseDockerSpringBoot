/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 *
 * @author britto
 */
@Entity
@Table(name = "cursos_usuarios")
@Data
public class CursoUsuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "usuario_id", unique = true)
    private Long usuarioId;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        
        if(!(obj instanceof  CursoUsuario))
            return false;
        
        CursoUsuario o = (CursoUsuario) obj;
        
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);        
    }
    
    
}
