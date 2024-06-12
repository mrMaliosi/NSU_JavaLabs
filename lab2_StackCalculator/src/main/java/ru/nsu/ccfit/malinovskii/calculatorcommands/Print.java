package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;
import ru.nsu.ccfit.malinovskii.exceptions.PrintException;

public class Print implements Command{
    @Override
    public void execute(ExecutionContext context) throws PrintException {
        if (context.stackIsEmpty())
        {
            throw new PrintException();
        }
        System.out.println(context.stackGetFirst());
    }
}