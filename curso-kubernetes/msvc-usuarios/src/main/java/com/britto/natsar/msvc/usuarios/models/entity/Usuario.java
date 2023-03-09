/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.britto.natsar.msvc.usuarios.models.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

/**
 *
 * @author britto
 */

@Entity
@Table(name="usuarios")
@Data
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    @NotBlank
    private String nombre;
    
    @NotEmpty
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    
    @NotEmpty
    @NotBlank    
    private String password;
        
}
