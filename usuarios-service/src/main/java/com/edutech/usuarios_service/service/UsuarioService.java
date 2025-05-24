package com.edutech.usuarios_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.obtenerUsuarios();
    }
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.guardar(usuario);
    }
    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.buscarPorId(id);
    }
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }
    public Usuario actualizar(Usuario usuario) {
        return usuarioRepository.actualizar(usuario);
    }
    public void eliminar(UUID id) {
        usuarioRepository.eliminar(id);
    }

}
