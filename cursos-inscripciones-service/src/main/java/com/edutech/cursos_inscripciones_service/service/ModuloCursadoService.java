package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.ModuloCursado;
import com.edutech.cursos_inscripciones_service.repository.ModuloCursadoRepository;

@Service
public class ModuloCursadoService {

    private final ModuloCursadoRepository moduloCursadoRepository;

    public ModuloCursadoService(ModuloCursadoRepository moduloCursadoRepository) {
        this.moduloCursadoRepository = moduloCursadoRepository;
    }

    public List<ModuloCursado> getAllModulosCursados() {
        return moduloCursadoRepository.findAll();
    }

    public Optional<ModuloCursado> getModuloCursadoById(Long id) {
        return moduloCursadoRepository.findById(id);
    }

    public ModuloCursado saveModuloCursado(ModuloCursado moduloCursado) {
        return moduloCursadoRepository.save(moduloCursado);
    }

    public void deleteModuloCursado(Long id) {
        moduloCursadoRepository.deleteById(id);
    }
}
