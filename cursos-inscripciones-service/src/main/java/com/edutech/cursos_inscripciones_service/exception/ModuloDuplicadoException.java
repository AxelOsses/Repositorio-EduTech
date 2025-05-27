package com.edutech.cursos_inscripciones_service.exception;

/**
 * Excepción lanzada cuando se intenta agregar un módulo duplicado a un curso.
 */
public class ModuloDuplicadoException extends RuntimeException {
    public ModuloDuplicadoException(String message) {
        super(message);
    }
}
