package com.edutech.usuarios_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.usuarios_service.model.Usuario;
import com.edutech.usuarios_service.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
   
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
    
    public Usuario findById(UUID id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void delete(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
