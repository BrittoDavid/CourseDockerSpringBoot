/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.cursos.models.dto;

import lombok.Data;

/**
 *
 * @author britto
 */

@Data
public class UsuarioDto {
    
    private Long id;
    private String nombre;
    private String email;
    private String password;
    
}
