package ru.nsu.ccfit.malinovskii.exceptions;

public class ReadThreadException extends ThreadException {
    public ReadThreadException()
    {
        super("Division by zero!\n");
    }
    public ReadThreadException(String message) {
        super(message);
    }
}
