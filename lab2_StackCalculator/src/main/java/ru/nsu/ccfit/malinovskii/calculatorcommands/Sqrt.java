package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;

import static java.lang.Math.sqrt;

public class Sqrt implements Command{
    @Override
    public void execute(ExecutionContext context)
    {
        double a = context.pop();
        context.push(sqrt(a));
    }
}