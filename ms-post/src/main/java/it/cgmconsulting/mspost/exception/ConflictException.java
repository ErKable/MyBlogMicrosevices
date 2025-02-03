package it.cgmconsulting.mspost.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException{

    private final String message;

    public ConflictException(String message) {
        super(String.format("%s", message));
        this.message = message;

    }
}
