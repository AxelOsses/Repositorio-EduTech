package com.edutech.cursos_inscripciones_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.cursos_inscripciones_service.controller.ProgresoCursoController;
import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class ProgresoCursoModelAssembler implements RepresentationModelAssembler<ProgresoCurso, EntityModel<ProgresoCurso>> {
    @Override
    @NonNull
    public EntityModel<ProgresoCurso> toModel(@NonNull ProgresoCurso progresoCurso) {
        return EntityModel.of(progresoCurso,
                linkTo(methodOn(ProgresoCursoController.class).getProgresoCursoById(progresoCurso.getId())).withSelfRel(),
                linkTo(methodOn(ProgresoCursoController.class).getAllProgresosCursos()).withRel("progresosCurso"));
    }
} 