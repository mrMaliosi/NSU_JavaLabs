package ru.nsu.ccfit.malinovskii.exceptions;

public class DivisionException extends CommandException{
    public DivisionException()
    {
        super("Division by zero!\n");
    }

}
