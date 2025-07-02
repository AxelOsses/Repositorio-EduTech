package com.edutech.cursos_inscripciones_service.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.edutech.cursos_inscripciones_service.controller.EvaluacionController;
import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {
    @Override
    @NonNull
    public EntityModel<Evaluacion> toModel(@NonNull Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionController.class).getEvaluacionById(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionController.class).getAllEvaluaciones()).withRel("evaluaciones"));
    }
} 