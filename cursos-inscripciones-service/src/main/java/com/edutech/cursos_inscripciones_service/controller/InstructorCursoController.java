package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.InstructorCurso;
import com.edutech.cursos_inscripciones_service.service.InstructorCursoService;
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
import com.edutech.cursos_inscripciones_service.assemblers.InstructorCursoModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import java.util.stream.Collectors;

import java.util.List;

@Tag(name = "InstructorCurso", description = "Operaciones relacionadas con la asignación de instructores a cursos")
@RestController
@RequestMapping("/api/v1/instructor-cursos")
public class InstructorCursoController {

    private final InstructorCursoService instructorCursoService;
    private final InstructorCursoModelAssembler assembler;

    public InstructorCursoController(InstructorCursoService instructorCursoService, InstructorCursoModelAssembler assembler) {
        this.instructorCursoService = instructorCursoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todas las asignaciones de instructores a cursos", description = "Devuelve una lista de todas las asignaciones de instructores a cursos.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de asignaciones obtenida correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = InstructorCurso.class)),
                examples = @ExampleObject(value = "{\n  \"_embedded\": {\n    \"instructorCursoList\": [\n      {\n        \"id\": 1,\n        \"instructorId\": 10,\n        \"curso\": {\n          \"id\": 1,\n          \"titulo\": \"Curso de Java\"\n        },\n        \"fechaOtorgacion\": \"2023-05-01\",\n        \"_links\": {\n          \"self\": {\n            \"href\": \"/api/v1/instructor-cursos/1\"\n          },\n          \"instructoresCursos\": {\n            \"href\": \"/api/v1/instructor-cursos\"\n          }\n        }\n      }\n    ]\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/instructor-cursos\"\n    }\n  }\n}")))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<InstructorCurso>>> getAllInstructoresCursos() {
        List<EntityModel<InstructorCurso>> instructores = instructorCursoService.getAllInstructoresCursos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(instructores));
    }

    @Operation(summary = "Obtener una asignación de instructor a curso por ID", description = "Devuelve una asignación específica según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación encontrada",
            content = @Content(schema = @Schema(implementation = InstructorCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"instructorId\": 10,\n  \"curso\": {\n    \"id\": 1,\n    \"titulo\": \"Curso de Java\"\n  },\n  \"fechaOtorgacion\": \"2023-05-01\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/instructor-cursos/1\"\n    },\n    \"instructoresCursos\": {\n      \"href\": \"/api/v1/instructor-cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<InstructorCurso>> getInstructorCursoById(
            @Parameter(description = "ID de la asignación", example = "1") @PathVariable("id") Long id) {
        return instructorCursoService.getInstructorCursoById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id));
    }

    @Operation(summary = "Crear una nueva asignación de instructor a curso", description = "Crea una nueva asignación en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Asignación creada correctamente",
            content = @Content(schema = @Schema(implementation = InstructorCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"instructorId\": 11,\n  \"curso\": {\n    \"id\": 2,\n    \"titulo\": \"Curso de Python\"\n  },\n  \"fechaOtorgacion\": \"2023-06-01\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/instructor-cursos/2\"\n    },\n    \"instructoresCursos\": {\n      \"href\": \"/api/v1/instructor-cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto: ya existe una asignación con ese ID", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<InstructorCurso>> createInstructorCurso(
            @Valid @RequestBody InstructorCurso instructorCurso) {
        if (instructorCurso.getId() != null && instructorCursoService.getInstructorCursoById(instructorCurso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un InstructorCurso con id: " + instructorCurso.getId());
        }
        InstructorCurso created = instructorCursoService.saveInstructorCurso(instructorCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Operation(summary = "Actualizar una asignación de instructor a curso", description = "Actualiza los datos de una asignación existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Asignación actualizada correctamente",
            content = @Content(schema = @Schema(implementation = InstructorCurso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"instructorId\": 10,\n  \"curso\": {\n    \"id\": 1,\n    \"titulo\": \"Curso de Java Avanzado\"\n  },\n  \"fechaOtorgacion\": \"2023-07-01\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/instructor-cursos/1\"\n    },\n    \"instructoresCursos\": {\n      \"href\": \"/api/v1/instructor-cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<InstructorCurso>> updateInstructorCurso(
            @Parameter(description = "ID de la asignación a actualizar", example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody InstructorCurso updatedInstructorCurso) {
        if (!instructorCursoService.getInstructorCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id);
        }
        updatedInstructorCurso.setId(id);
        InstructorCurso updated = instructorCursoService.saveInstructorCurso(updatedInstructorCurso);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Eliminar una asignación de instructor a curso", description = "Elimina una asignación existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Asignación eliminada correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstructorCurso(
            @Parameter(description = "ID de la asignación a eliminar", example = "1") @PathVariable("id") Long id) {
        if (!instructorCursoService.getInstructorCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("InstructorCurso no encontrado con id: " + id);
        }
        instructorCursoService.deleteInstructorCurso(id);
        return ResponseEntity.noContent().build();
    }
}

