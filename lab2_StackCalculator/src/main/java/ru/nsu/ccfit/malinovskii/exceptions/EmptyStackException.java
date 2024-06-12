package ru.nsu.ccfit.malinovskii.exceptions;

public class EmptyStackException extends CommandException{
    public EmptyStackException()
    {
        super("Stack is empty!\n");
    }

}