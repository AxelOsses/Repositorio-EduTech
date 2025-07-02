package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Inscripcion;
import com.edutech.cursos_inscripciones_service.service.InscripcionService;
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
import com.edutech.cursos_inscripciones_service.assemblers.InscripcionModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import java.util.stream.Collectors;

import java.util.List;

@Tag(name = "Inscripciones", description = "Operaciones relacionadas con inscripciones")
@RestController
@RequestMapping("/api/v1/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;
    private final InscripcionModelAssembler assembler;

    public InscripcionController(InscripcionService inscripcionService, InscripcionModelAssembler assembler) {
        this.inscripcionService = inscripcionService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todas las inscripciones", description = "Devuelve una lista de todas las inscripciones.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de inscripciones obtenida correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Inscripcion.class)),
                examples = @ExampleObject(value = "{\n  \"_embedded\": {\n    \"inscripcionList\": [\n      {\n        \"id\": 1,\n        \"curso\": {\n          \"id\": 1\n        },\n        \"estudianteId\": 10,\n        \"fechaInscripcion\": \"2023-05-01\",\n        \"estaAprobado\": true,\n        \"_links\": {\n          \"self\": {\n            \"href\": \"/api/v1/inscripciones/1\"\n          },\n          \"inscripciones\": {\n            \"href\": \"/api/v1/inscripciones\"\n          }\n        }\n      }\n    ]\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/inscripciones\"\n    }\n  }\n}")))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Inscripcion>>> getAllInscripciones() {
        List<EntityModel<Inscripcion>> inscripciones = inscripcionService.getAllInscripciones().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(inscripciones));
    }

    @Operation(summary = "Obtener una inscripción por ID", description = "Devuelve una inscripción específica según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscripción encontrada",
            content = @Content(schema = @Schema(implementation = Inscripcion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"curso\": {\n    \"id\": 1\n  },\n  \"estudianteId\": 10,\n  \"fechaInscripcion\": \"2023-05-01\",\n  \"estaAprobado\": true,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/inscripciones/1\"\n    },\n    \"inscripciones\": {\n      \"href\": \"/api/v1/inscripciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Inscripcion>> getInscripcionById(
            @Parameter(description = "ID de la inscripción", example = "1") @PathVariable("id") Long id) {
        return inscripcionService.getInscripcionById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
    }

    @Operation(summary = "Crear una nueva inscripción", description = "Crea una nueva inscripción en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Inscripción creada correctamente",
            content = @Content(schema = @Schema(implementation = Inscripcion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"curso\": {\n    \"id\": 2\n  },\n  \"estudianteId\": 11,\n  \"fechaInscripcion\": \"2023-06-01\",\n  \"estaAprobado\": true,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/inscripciones/2\"\n    },\n    \"inscripciones\": {\n      \"href\": \"/api/v1/inscripciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto: ya existe una inscripción con ese ID", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Inscripcion>> createInscripcion(
            @Valid @RequestBody Inscripcion inscripcion) {
        if (inscripcion.getId() != null && inscripcionService.getInscripcionById(inscripcion.getId()).isPresent()) {
            throw new ConflictException("Ya existe una inscripción con id: " + inscripcion.getId());
        }
        Inscripcion created = inscripcionService.saveInscripcion(inscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Operation(summary = "Actualizar una inscripción", description = "Actualiza los datos de una inscripción existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscripción actualizada correctamente",
            content = @Content(schema = @Schema(implementation = Inscripcion.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"curso\": {\n    \"id\": 1\n  },\n  \"estudianteId\": 10,\n  \"fechaInscripcion\": \"2023-05-01\",\n  \"estaAprobado\": false,\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/inscripciones/1\"\n    },\n    \"inscripciones\": {\n      \"href\": \"/api/v1/inscripciones\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Inscripcion>> updateInscripcion(
            @Parameter(description = "ID de la inscripción a actualizar", example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody Inscripcion updatedInscripcion) {
        if (!inscripcionService.getInscripcionById(id).isPresent()) {
            throw new ResourceNotFoundException("Inscripción no encontrada con id: " + id);
        }
        updatedInscripcion.setId(id);
        Inscripcion updated = inscripcionService.saveInscripcion(updatedInscripcion);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Eliminar una inscripción", description = "Elimina una inscripción existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Inscripción eliminada correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInscripcion(
            @Parameter(description = "ID de la inscripción a eliminar", example = "1") @PathVariable("id") Long id) {
        if (!inscripcionService.getInscripcionById(id).isPresent()) {
            throw new ResourceNotFoundException("Inscripción no encontrada con id: " + id);
        }
        inscripcionService.deleteInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
