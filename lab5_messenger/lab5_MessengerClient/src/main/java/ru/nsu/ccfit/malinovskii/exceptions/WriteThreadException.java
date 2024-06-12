package ru.nsu.ccfit.malinovskii.exceptions;

public class WriteThreadException extends ThreadException {
    public WriteThreadException()
    {
        super("Unable to push!\n");
    }
    public WriteThreadException(String message) {
        super(message);
    }

}