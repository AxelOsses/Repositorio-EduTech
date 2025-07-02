package com.edutech.cursos_inscripciones_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.cursos_inscripciones_service.controller.CursoController;
import com.edutech.cursos_inscripciones_service.model.Curso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class CursoModelAssembler implements RepresentationModelAssembler<Curso, EntityModel<Curso>> {
    @Override
    @NonNull
    public EntityModel<Curso> toModel(@NonNull Curso curso) {
        return EntityModel.of(curso,
                linkTo(methodOn(CursoController.class).getCursoById(curso.getId())).withSelfRel(),
                linkTo(methodOn(CursoController.class).getAllCursos()).withRel("cursos"));
    }
} 