package ru.nsu.ccfit.malinovskii.exceptions;

public class ThreadException extends AbstractException {
    public ThreadException(String message) {
        super(message);
    }

    public ThreadException(String message, Throwable cause) {
        super(message, cause);
    }
}
