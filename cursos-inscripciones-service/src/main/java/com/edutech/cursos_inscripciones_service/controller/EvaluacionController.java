package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Evaluacion;
import com.edutech.cursos_inscripciones_service.service.EvaluacionService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import com.edutech.cursos_inscripciones_service.assemblers.EvaluacionModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import java.util.stream.Collectors;

import java.util.List;

@Tag(name = "Evaluaciones", description = "Operaciones relacionadas con evaluaciones")
@RestController
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    private final EvaluacionService evaluacionService;
    private final EvaluacionModelAssembler assembler;

    public EvaluacionController(EvaluacionService evaluacionService, EvaluacionModelAssembler assembler) {
        this.evaluacionService = evaluacionService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todas las evaluaciones", description = "Devuelve una lista de todas las evaluaciones.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de evaluaciones obtenida correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Evaluacion.class)),
                examples = @ExampleObject(value = "{\n  \"_embedded\": {\n    \"evaluacionList\": [\n      {\n        \"id\": 1,\n        \"nombre\": \"Parcial 1\",\n        \"descripcion\": \"Primer parcial\",\n        \"modulo\": null,\n        \"curso\": {\n          \"id\": 1\n        },\n        \"tipoEvaluacion\": {\n          \"id\": 1,\n          \"nombre\": \"EXAMEN\"\n        },\n        \"_links\": {\n          \"self\": {\n            \"href\": \"/api/v1/evaluaciones/1\"\n          },\n          \"evaluaciones\": {\n            \"href\": \"/api/v1/evaluaciones\"\n          }\n        }\n      }\n    ]\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/evaluaciones\"\n    }\n  }\n}")))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Evaluacion>>> getAllEvaluaciones() {
        List<EntityModel<Evaluacion>> evaluaciones = evaluacionService.getAllEvaluaciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(evaluaciones));
    }

    @Operation(summary = "Obtener una evaluación por ID", description = "Devuelve una evaluación específica según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación encontrada",
            content = @Content(schema = @Schema(implementation = Evaluacion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"nombre\": \"Parcial 1\",\n  \"descripcion\": \"Primer parcial\",\n  \"modulo\": null,\n  \"curso\": {\n    \"id\": 1\n  },\n  \"tipoEvaluacion\": {\n    \"id\": 1,\n    \"nombre\": \"EXAMEN\"\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/evaluaciones/1\"\n    },\n    \"evaluaciones\": {\n      \"href\": \"/api/v1/evaluaciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Evaluacion>> getEvaluacionById(
            @Parameter(description = "ID de la evaluación", example = "1") @PathVariable("id") Long id) {
        return evaluacionService.getEvaluacionById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Evaluación no encontrada con id: " + id));
    }

    @Operation(summary = "Crear una nueva evaluación", description = "Crea una nueva evaluación en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluación creada correctamente",
            content = @Content(schema = @Schema(implementation = Evaluacion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"nombre\": \"Final\",\n  \"descripcion\": \"Examen final\",\n  \"modulo\": null,\n  \"curso\": {\n    \"id\": 1\n  },\n  \"tipoEvaluacion\": {\n    \"id\": 1,\n    \"nombre\": \"EXAMEN\"\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/evaluaciones/2\"\n    },\n    \"evaluaciones\": {\n      \"href\": \"/api/v1/evaluaciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto: ya existe una evaluación con ese ID", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Evaluacion>> createEvaluacion(
            @Valid @RequestBody Evaluacion evaluacion) {
        if (evaluacion.getId() != null && evaluacionService.getEvaluacionById(evaluacion.getId()).isPresent()) {
            throw new ConflictException("Ya existe una evaluación con id: " + evaluacion.getId());
        }
        Evaluacion created = evaluacionService.saveEvaluacion(evaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Operation(summary = "Actualizar una evaluación", description = "Actualiza los datos de una evaluación existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Evaluación actualizada correctamente",
            content = @Content(schema = @Schema(implementation = Evaluacion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"nombre\": \"Parcial 2\",\n  \"descripcion\": \"Segundo parcial\",\n  \"modulo\": null,\n  \"curso\": {\n    \"id\": 1\n  },\n  \"tipoEvaluacion\": {\n    \"id\": 1,\n    \"nombre\": \"EXAMEN\"\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/evaluaciones/1\"\n    },\n    \"evaluaciones\": {\n      \"href\": \"/api/v1/evaluaciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Evaluacion>> updateEvaluacion(
            @Parameter(description = "ID de la evaluación a actualizar", example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody Evaluacion updatedEvaluacion) {
        if (!evaluacionService.getEvaluacionById(id).isPresent()) {
            throw new ResourceNotFoundException("Evaluación no encontrada con id: " + id);
        }
        updatedEvaluacion.setId(id);
        Evaluacion updated = evaluacionService.saveEvaluacion(updatedEvaluacion);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Eliminar una evaluación", description = "Elimina una evaluación existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Evaluación eliminada correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvaluacion(
            @Parameter(description = "ID de la evaluación a eliminar", example = "1") @PathVariable("id") Long id) {
        if (!evaluacionService.getEvaluacionById(id).isPresent()) {
            throw new ResourceNotFoundException("Evaluación no encontrada con id: " + id);
        }
        evaluacionService.deleteEvaluacion(id);
        return ResponseEntity.noContent().build();
    }
}
