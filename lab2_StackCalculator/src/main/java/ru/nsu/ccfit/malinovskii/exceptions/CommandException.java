package ru.nsu.ccfit.malinovskii.exceptions;

public class CommandException extends AbstractException {
    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
