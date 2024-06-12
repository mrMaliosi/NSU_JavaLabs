package ru.nsu.ccfit.malinovskii.exceptions;

public class CommandNotFoundException extends AbstractException{
    public CommandNotFoundException(){
        super("Command not found!\n");
    }
}
