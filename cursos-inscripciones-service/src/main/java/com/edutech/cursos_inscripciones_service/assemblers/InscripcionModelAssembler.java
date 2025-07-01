package com.edutech.cursos_inscripciones_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.cursos_inscripciones_service.controller.InscripcionController;
import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class InscripcionModelAssembler implements RepresentationModelAssembler<Inscripcion, EntityModel<Inscripcion>> {
    @Override
    @NonNull
    public EntityModel<Inscripcion> toModel(@NonNull Inscripcion inscripcion) {
        return EntityModel.of(inscripcion,
                linkTo(methodOn(InscripcionController.class).getInscripcionById(inscripcion.getId())).withSelfRel(),
                linkTo(methodOn(InscripcionController.class).getAllInscripciones()).withRel("inscripciones"));
    }
} 