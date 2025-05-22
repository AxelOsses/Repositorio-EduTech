package com.edutech.usuarios_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String username;
    private String password;
    private String fechaCreacion;
    private Boolean estaActivo;
}
