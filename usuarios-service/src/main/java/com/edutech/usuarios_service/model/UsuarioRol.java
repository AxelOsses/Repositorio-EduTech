package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_rol")
@Data
@NoArgsConstructor 
@AllArgsConstructor
public class UsuarioRol {

    @Id
    @Column(name = "id_usuario_rol", length = 36, nullable = false, updatable = false)
    private UUID id = UUID.randomUUID(); 

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    public UsuarioRol(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.fechaAsignacion = LocalDateTime.now();
    }

}
