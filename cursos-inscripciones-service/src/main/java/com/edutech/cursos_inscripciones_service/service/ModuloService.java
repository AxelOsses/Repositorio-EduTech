package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.Modulo;
import com.edutech.cursos_inscripciones_service.repository.ModuloRepository;

@Service
public class ModuloService {

    private final ModuloRepository moduloRepository;

    public ModuloService(ModuloRepository moduloRepository) {
        this.moduloRepository = moduloRepository;
    }

    public List<Modulo> getAllModulos() {
        return moduloRepository.findAll();
    }

    public Optional<Modulo> getModuloById(Long id) {
        return moduloRepository.findById(id);
    }

    public Modulo saveModulo(Modulo modulo) {
        return moduloRepository.save(modulo);
    }

    public void deleteModulo(Long id) {
        moduloRepository.deleteById(id);
    }
}

