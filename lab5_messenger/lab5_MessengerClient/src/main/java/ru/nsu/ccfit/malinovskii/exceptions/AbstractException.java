package ru.nsu.ccfit.malinovskii.exceptions;

public class AbstractException extends Exception{
    public AbstractException(Throwable cause) {
        super(cause);
    }

    protected AbstractException(String message) {
        super(message);
    }

    protected AbstractException(String message, Throwable cause) {
        super(message, cause);
    }
}
