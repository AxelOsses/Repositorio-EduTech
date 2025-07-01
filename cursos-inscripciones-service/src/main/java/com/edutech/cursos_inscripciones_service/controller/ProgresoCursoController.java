package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.ProgresoCurso;
import com.edutech.cursos_inscripciones_service.service.ProgresoCursoService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import com.edutech.cursos_inscripciones_service.assemblers.ProgresoCursoModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import java.util.stream.Collectors;

import java.util.List;

@Tag(name = "ProgresoCurso", description = "Operaciones relacionadas con el progreso de los cursos")
@RestController
@RequestMapping("/api/v1/progresos-curso")
public class ProgresoCursoController {

    private final ProgresoCursoService progresoCursoService;
    private final ProgresoCursoModelAssembler assembler;

    public ProgresoCursoController(ProgresoCursoService progresoCursoService, ProgresoCursoModelAssembler assembler) {
        this.progresoCursoService = progresoCursoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los progresos de cursos", description = "Devuelve una lista de todos los progresos de cursos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de progresos obtenida correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProgresoCurso.class)),
                examples = @ExampleObject(value = "{\n  \"_embedded\": {\n    \"progresoCursoList\": [\n      {\n        \"id\": 1,\n        \"inscripcion\": {\n          \"id\": 10\n        },\n        \"porcentajeAvance\": 80,\n        \"tiempoTotalEstudio\": 1200,\n        \"_links\": {\n          \"self\": {\n            \"href\": \"/api/v1/progresos-curso/1\"\n          },\n          \"progresosCurso\": {\n            \"href\": \"/api/v1/progresos-curso\"\n          }\n        }\n      }\n    ]\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/progresos-curso\"\n    }\n  }\n}")))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProgresoCurso>>> getAllProgresosCursos() {
        List<EntityModel<ProgresoCurso>> progresos = progresoCursoService.getAllProgresoCursos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(progresos));
    }

    @Operation(summary = "Obtener un progreso de curso por ID", description = "Devuelve un progreso de curso específico según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progreso de curso encontrado",
            content = @Content(schema = @Schema(implementation = ProgresoCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"inscripcion\": {\n    \"id\": 10\n  },\n  \"porcentajeAvance\": 80,\n  \"tiempoTotalEstudio\": 1200,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/progresos-curso/1\"\n    },\n    \"progresosCurso\": {\n      \"href\": \"/api/v1/progresos-curso\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Progreso de curso no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProgresoCurso>> getProgresoCursoById(
            @Parameter(description = "ID del progreso de curso", example = "1") @PathVariable("id") Long id) {
        return progresoCursoService.getProgresoCursoById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id));
    }

    @Operation(summary = "Crear un nuevo progreso de curso", description = "Crea un nuevo progreso de curso en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Progreso de curso creado correctamente",
            content = @Content(schema = @Schema(implementation = ProgresoCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"inscripcion\": {\n    \"id\": 11\n  },\n  \"porcentajeAvance\": 100,\n  \"tiempoTotalEstudio\": 1500,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/progresos-curso/2\"\n    },\n    \"progresosCurso\": {\n      \"href\": \"/api/v1/progresos-curso\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto: ya existe un progreso con ese ID", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<ProgresoCurso>> createProgresoCurso(
            @Valid @RequestBody ProgresoCurso progresoCurso) {
        if (progresoCurso.getId() != null && progresoCursoService.getProgresoCursoById(progresoCurso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un ProgresoCurso con id: " + progresoCurso.getId());
        }
        ProgresoCurso created = progresoCursoService.saveProgresoCurso(progresoCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Operation(summary = "Actualizar un progreso de curso", description = "Actualiza los datos de un progreso de curso existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progreso de curso actualizado correctamente",
            content = @Content(schema = @Schema(implementation = ProgresoCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"inscripcion\": {\n    \"id\": 10\n  },\n  \"porcentajeAvance\": 90,\n  \"tiempoTotalEstudio\": 1600,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/progresos-curso/1\"\n    },\n    \"progresosCurso\": {\n      \"href\": \"/api/v1/progresos-curso\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Progreso de curso no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProgresoCurso>> updateProgresoCurso(
            @Parameter(description = "ID del progreso de curso a actualizar", example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody ProgresoCurso updatedProgresoCurso) {
        if (!progresoCursoService.getProgresoCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id);
        }
        updatedProgresoCurso.setId(id);
        ProgresoCurso updated = progresoCursoService.saveProgresoCurso(updatedProgresoCurso);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Eliminar un progreso de curso", description = "Elimina un progreso de curso existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Progreso de curso eliminado correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Progreso de curso no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgresoCurso(
            @Parameter(description = "ID del progreso de curso a eliminar", example = "1") @PathVariable("id") Long id) {
        if (!progresoCursoService.getProgresoCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("ProgresoCurso no encontrado con id: " + id);
        }
        progresoCursoService.deleteProgresoCurso(id);
        return ResponseEntity.noContent().build();
    }
}

