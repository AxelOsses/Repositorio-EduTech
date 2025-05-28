package com.edutech.cursos_inscripciones_service.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.repository.InstructorCursoRepository;

@Service
public class InstructorCursoService {

    private final InstructorCursoRepository instructorCursoRepository;

    public InstructorCursoService(InstructorCursoRepository instructorCursoRepository) {
        this.instructorCursoRepository = instructorCursoRepository;
    }

    public List<InstructorCurso> getAllInstructoresCursos() {
        return instructorCursoRepository.findAll();
    }

    public Optional<InstructorCurso> getInstructorCursoById(Long id) {
        return instructorCursoRepository.findById(id);
    }

    public InstructorCurso saveInstructorCurso(InstructorCurso instructorCurso) {
        return instructorCursoRepository.save(instructorCurso);
    }

    public void deleteInstructorCurso(Long id) {
        instructorCursoRepository.deleteById(id);
    }
}

