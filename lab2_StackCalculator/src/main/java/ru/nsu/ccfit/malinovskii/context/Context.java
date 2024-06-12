package ru.nsu.ccfit.malinovskii.context;

import java.util.EmptyStackException;

public interface Context {
    Double pop() throws EmptyStackException;

    void push(Double number);

    String getCommandName();

    void commandClear();

    void commandAdd(String [] parts);

    int commandSize();
    String getFirstParametr();
    String getSecondParametr();

    Boolean containsParametr(String param);

    Double returnParametrValue(String param);

    void parametrsPut();

    boolean stackIsEmpty();

    Double stackGetFirst();
}
