package ru.nsu.ccfit.malinovskii.exceptions;

public class PushException extends CommandException{
    public PushException()
    {
        super("Unable to push!\n");
    }
    public PushException(String message) {
        super(message);
    }

}