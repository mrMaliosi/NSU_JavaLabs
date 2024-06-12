package ru.nsu.ccfit.malinovskii.exceptions;

public class PrintException extends CommandException{
    public PrintException()
    {
        super("Nothing to print!\n");
    }

}