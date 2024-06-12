package ru.nsu.ccfit.malinovskii.calculatorcommands;

import ru.nsu.ccfit.malinovskii.context.ExecutionContext;
import ru.nsu.ccfit.malinovskii.exceptions.DivisionException;

public class Div implements Command{
    @Override
    public void execute(ExecutionContext context) throws DivisionException {
        double a = context.pop();
        if (a == 0)
        {
            throw new DivisionException();
        }
        double b = context.pop();
        context.push(b/a);
    }
}
