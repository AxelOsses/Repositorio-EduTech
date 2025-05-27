package com.edutech.usuarios_service.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.edutech.usuarios_service.model.Usuario;

@Repository
public class UsuarioRepository {
    
    private List<Usuario> listaUsuarios = new ArrayList<>();
    public List<Usuario> obtenerUsuarios() {
        return listaUsuarios;
    }
    public Usuario buscarPorId(UUID id) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }
    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }
    public Usuario guardar(Usuario usuario) {
        listaUsuarios.add(usuario);
        return usuario;
    }
    public Usuario actualizar(Usuario usuario) {
        UUID id = null;
        int idPosicion = 0;

        for (int i = 0; i < listaUsuarios.size(); i++) {
            if (listaUsuarios.get(i).getId().equals(usuario.getId())) {
                id = usuario.getId();
                idPosicion = i;
            }
        }

        Usuario usuario1 = new Usuario();
        usuario1.setId(id);
        usuario1.setNombre(usuario.getNombre());
        usuario1.setApellido(usuario.getApellido());
        usuario1.setEmail(usuario.getEmail());
        usuario1.setUsername(usuario.getUsername());
        usuario1.setPassword(usuario.getPassword());
        usuario1.setFechaCreacion(usuario.getFechaCreacion());
        usuario1.setEstaActivo(usuario.getEstaActivo());

        listaUsuarios.set(idPosicion, usuario1);
        return usuario1;
    }
    public void eliminar(UUID id) {
        listaUsuarios.removeIf(usuario -> usuario.getId().equals(id));
    }

}



