package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.service.InstructorCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/instructor-cursos")
public class InstructorCursoController {

    private final InstructorCursoService instructorCursoService;

    @Autowired
    public InstructorCursoController(InstructorCursoService instructorCursoService) {
        this.instructorCursoService = instructorCursoService;
    }

    @GetMapping
    public List<InstructorCurso> getAllInstructoresCursos() {
        return instructorCursoService.getAllInstructoresCursos();
    }

    @GetMapping("/{id}")
    public Optional<InstructorCurso> getInstructorCursoById(@PathVariable("id") Long id) {
        return instructorCursoService.getInstructorCursoById(id);
    }

    @PostMapping
    public InstructorCurso createInstructorCurso(@RequestBody InstructorCurso instructorCurso) {
        return instructorCursoService.saveInstructorCurso(instructorCurso);
    }

    @PutMapping("/{id}")
    public InstructorCurso updateInstructorCurso(@PathVariable("id") Long id, @RequestBody InstructorCurso updatedInstructorCurso) {
        // En este caso, como no tienes un método update en el service,
        // podemos simplemente guardar el objeto actualizado:
        updatedInstructorCurso.setId(id);  // Asegura que el ID es el mismo
        return instructorCursoService.saveInstructorCurso(updatedInstructorCurso);
    }

    @DeleteMapping("/{id}")
    public void deleteInstructorCurso(@PathVariable("id") Long id) {
        instructorCursoService.deleteInstructorCurso(id);
    }
}

