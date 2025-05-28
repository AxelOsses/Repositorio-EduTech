package com.edutech.usuarios_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.usuarios_service.model.Permiso;
import com.edutech.usuarios_service.repository.PermisoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PermisoService {

    @Autowired
    private PermisoRepository permisoRepository;

    public List<Permiso> findAll() {
        return permisoRepository.findAll();
    }   

    public Permiso findById(UUID id) {
        return permisoRepository.findById(id).orElse(null);

    }

    public Permiso save(Permiso permiso) {
        return permisoRepository.save(permiso);
    }

    public void deleteById(UUID id) {
        permisoRepository.deleteById(id);
    }
}
