package com.edutech.cursos_inscripciones_service.exception;

/**
 * Excepción lanzada cuando se intenta eliminar un módulo que no existe en un curso.
 */
public class ModuloInexistenteException extends RuntimeException {
    public ModuloInexistenteException(String message) {
        super(message);
    }
}
