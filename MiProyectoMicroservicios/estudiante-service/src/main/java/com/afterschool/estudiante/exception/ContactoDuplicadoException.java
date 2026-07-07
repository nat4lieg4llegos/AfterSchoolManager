package com.afterschool.estudiante.exception;

public class ContactoDuplicadoException extends RuntimeException {

    public ContactoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}