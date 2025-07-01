package com.edutech.cursos_inscripciones_service.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum que define los posibles estados de un curso en el sistema.
 */
public enum TipoEstadoCurso {
    /**
     * Curso activo y disponible para los usuarios.
     */
    @Schema(description = "Curso activo y disponible para los usuarios", example = "ACTIVO")
    ACTIVO,

    /**
     * Curso inactivo o no disponible.
     */
    @Schema(description = "Curso inactivo o no disponible", example = "INACTIVO")
    INACTIVO,

    /**
     * Curso en proceso de revisión y validación.
     */
    @Schema(description = "Curso en proceso de revisión y validación", example = "EN_REVISION")
    EN_REVISION
}
