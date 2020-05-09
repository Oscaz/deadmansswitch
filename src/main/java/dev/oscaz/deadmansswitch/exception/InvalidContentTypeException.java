package dev.oscaz.deadmansswitch.exception;

public class InvalidContentTypeException extends RuntimeException {

    public InvalidContentTypeException() {
        // empty constructor
    }

    public InvalidContentTypeException(String message) {
        super(message);
    }

}
