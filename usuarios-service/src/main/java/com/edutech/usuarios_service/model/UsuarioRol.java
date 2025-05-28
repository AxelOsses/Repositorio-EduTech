package com.edutech.usuarios_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario_rol")
@Data
@NoArgsConstructor 
@AllArgsConstructor
public class UsuarioRol {

    /**
     * Identificador único del UsuarioRol.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental en MySQL
    @Column(name = "id_usuario_rol", updatable = false, nullable = false)
    private Long id;

    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDateTime fechaAsignacion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    /**
     * Constructor para crear una asignación con generación automática de ID.
     * @param usuario usuario al que se le otorga el rol
     * @param rol rol que se le otorga al usuario
     * @param fechaAsignacion fecha actual en que se asigna el rol
     */
    public UsuarioRol(Usuario usuario, Rol rol) {
        this.usuario = usuario;
        this.rol = rol;
        this.fechaAsignacion = LocalDateTime.now();
    }

}
