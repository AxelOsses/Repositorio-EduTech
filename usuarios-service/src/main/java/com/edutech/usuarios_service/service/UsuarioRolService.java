package com.edutech.usuarios_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.usuarios_service.model.UsuarioRol;
import com.edutech.usuarios_service.repository.UsuarioRolRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioRolService {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    public List<UsuarioRol> findAll() {
        return usuarioRolRepository.findAll();
    }
    
    public UsuarioRol findById(Long id) {
        return usuarioRolRepository.findById(id).get();
    }

    public UsuarioRol save(UsuarioRol usuarioRol) {
        return usuarioRolRepository.save(usuarioRol);
    }

    public void delete(Long id) {
        usuarioRolRepository.deleteById(id);
    }
}
