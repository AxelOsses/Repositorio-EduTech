package com.edutech.cursos_inscripciones_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.cursos_inscripciones_service.controller.InstructorCursoController;
import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class InstructorCursoModelAssembler implements RepresentationModelAssembler<InstructorCurso, EntityModel<InstructorCurso>> {
    @Override
    @NonNull
    public EntityModel<InstructorCurso> toModel(@NonNull InstructorCurso instructorCurso) {
        return EntityModel.of(instructorCurso,
                linkTo(methodOn(InstructorCursoController.class).getInstructorCursoById(instructorCurso.getId())).withSelfRel(),
                linkTo(methodOn(InstructorCursoController.class).getAllInstructoresCursos()).withRel("instructoresCursos"));
    }
} 