package com.edutech.cursos_inscripciones_service.model;

/**
 * Enum que define los posibles estados de un curso en el sistema.
 */
public enum TipoEstadoCurso {
    /**
     * Curso activo y disponible para los usuarios.
     */
    ACTIVO,

    /**
     * Curso inactivo o no disponible.
     */
    INACTIVO,

    /**
     * Curso en proceso de revisión y validación.
     */
    EN_REVISION
}
