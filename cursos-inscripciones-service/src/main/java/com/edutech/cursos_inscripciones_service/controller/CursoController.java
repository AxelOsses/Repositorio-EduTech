package com.edutech.cursos_inscripciones_service.controller;

import com.edutech.cursos_inscripciones_service.model.Curso;
import com.edutech.cursos_inscripciones_service.service.CursoService;
import com.edutech.cursos_inscripciones_service.exception.ResourceNotFoundException;
import com.edutech.cursos_inscripciones_service.exception.ConflictException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import com.edutech.cursos_inscripciones_service.assemblers.CursoModelAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

@Tag(name = "Cursos", description = "Operaciones relacionadas con cursos")
@RestController
@RequestMapping("/api/v1/cursos")
public class CursoController {

    private final CursoService cursoService;
    private final CursoModelAssembler assembler;

    public CursoController(CursoService cursoService, CursoModelAssembler assembler) {
        this.cursoService = cursoService;
        this.assembler = assembler;
    }

    @Operation(summary = "Obtener todos los cursos", description = "Devuelve una lista de todos los cursos disponibles.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de cursos obtenida correctamente",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Curso.class)),
                examples = @ExampleObject(value = "{\n  \"_embedded\": {\n    \"cursoList\": [\n      {\n        \"id\": 1,\n        \"titulo\": \"Curso de Java\",\n        \"descripcion\": \"Aprende Java desde cero\",\n        \"fechaCreacion\": \"2023-01-01\",\n        \"fechaActualizacion\": \"2023-01-10\",\n        \"duracionHoras\": 40,\n        \"numeroOrden\": 1,\n        \"estado\": \"ACTIVO\",\n        \"_links\": {\n          \"self\": {\n            \"href\": \"/api/v1/cursos/1\"\n          },\n          \"cursos\": {\n            \"href\": \"/api/v1/cursos\"\n          }\n        }\n      }\n    ]\n  },\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/cursos\"\n    }\n  }\n}")))
    })
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Curso>>> getAllCursos() {
        List<EntityModel<Curso>> cursos = cursoService.getAllCursos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(cursos));
    }

    @Operation(summary = "Obtener un curso por ID", description = "Devuelve un curso específico según su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso encontrado",
            content = @Content(schema = @Schema(implementation = Curso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"titulo\": \"Curso de Java\",\n  \"descripcion\": \"Aprende Java desde cero\",\n  \"fechaCreacion\": \"2023-01-01\",\n  \"fechaActualizacion\": \"2023-01-10\",\n  \"duracionHoras\": 40,\n  \"numeroOrden\": 1,\n  \"estado\": \"ACTIVO\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/cursos/1\"\n    },\n    \"cursos\": {\n      \"href\": \"/api/v1/cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> getCursoById(
            @Parameter(description = "ID del curso", example = "1") @PathVariable("id") Long id) {
        return cursoService.getCursoById(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
    }

    @Operation(summary = "Crear un nuevo curso", description = "Crea un curso nuevo en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Curso creado correctamente",
            content = @Content(schema = @Schema(implementation = Curso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 2,\n  \"titulo\": \"Curso de Python\",\n  \"descripcion\": \"Aprende Python desde cero\",\n  \"fechaCreacion\": \"2023-02-01\",\n  \"fechaActualizacion\": \"2023-02-10\",\n  \"duracionHoras\": 30,\n  \"numeroOrden\": 2,\n  \"estado\": \"ACTIVO\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/cursos/2\"\n    },\n    \"cursos\": {\n      \"href\": \"/api/v1/cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "409", description = "Conflicto: ya existe un curso con ese ID", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EntityModel<Curso>> createCurso(
            @Valid @RequestBody Curso curso) {
        if (curso.getId() != null && cursoService.getCursoById(curso.getId()).isPresent()) {
            throw new ConflictException("Ya existe un curso con id: " + curso.getId());
        }
        Curso created = cursoService.saveCurso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(created));
    }

    @Operation(summary = "Actualizar un curso", description = "Actualiza los datos de un curso existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso actualizado correctamente",
            content = @Content(schema = @Schema(implementation = Curso.class),
                examples = @ExampleObject(value = "{\n  \"id\": 1,\n  \"titulo\": \"Curso de Java Avanzado\",\n  \"descripcion\": \"Java avanzado\",\n  \"fechaCreacion\": \"2023-01-01\",\n  \"fechaActualizacion\": \"2023-03-01\",\n  \"duracionHoras\": 50,\n  \"numeroOrden\": 1,\n  \"estado\": \"ACTIVO\",\n  \"_links\": {\n    \"self\": {\n      \"href\": \"/api/v1/cursos/1\"\n    },\n    \"cursos\": {\n      \"href\": \"/api/v1/cursos\"\n    }\n  }\n}"))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Curso>> updateCurso(
            @Parameter(description = "ID del curso a actualizar", example = "1") @PathVariable("id") Long id,
            @Valid @RequestBody Curso updatedCurso) {
        if (!cursoService.getCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        updatedCurso.setId(id);
        Curso updated = cursoService.saveCurso(updatedCurso);
        return ResponseEntity.ok(assembler.toModel(updated));
    }

    @Operation(summary = "Eliminar un curso", description = "Elimina un curso existente por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Curso eliminado correctamente", content = @Content),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(
            @Parameter(description = "ID del curso a eliminar", example = "1") @PathVariable("id") Long id) {
        if (!cursoService.getCursoById(id).isPresent()) {
            throw new ResourceNotFoundException("Curso no encontrado con id: " + id);
        }
        cursoService.deleteCurso(id);
        return ResponseEntity.noContent().build();
    }
}

