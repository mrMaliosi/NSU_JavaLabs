package ru.nsu.ccfit.malinovskii.context;

import java.util.*;

public class ExecutionContext implements Context{

    private final Map<String, Double> parametrs;
    private final LinkedList<Double> stack;
    private final LinkedList<String> command;
    public Boolean isEnd;

    public ExecutionContext() {
        this.parametrs = new HashMap<String, Double>();
        this.stack = new LinkedList<Double>();
        this.command = new LinkedList<String>();
        this.isEnd = false;
    }

    public Double pop() throws EmptyStackException {
        if (!stack.isEmpty()) {
            return this.stack.pop();
        } else {
            throw new EmptyStackException();
        }
    }

    public void push(Double number) {
        this.stack.push(number);
    }

    public String getCommandName() {
        return this.command.getFirst();
    }

    public void commandClear(){
        this.command.clear();
    }

    public void commandAdd(String [] parts){
        this.command.addAll(Arrays.asList(parts));
    }

    public int commandSize() {
        return this.command.size();
    }
    public String getFirstParametr() {
        return this.command.get(1);
    }
    public String getSecondParametr() {
        return this.command.get(2);
    }

    public Boolean containsParametr(String param) {
        return this.parametrs.containsKey(param);
    }

    public Double returnParametrValue(String param) {
        return this.parametrs.get(param);
    }

    public void parametrsPut() {
        this.parametrs.put(this.getFirstParametr(), Double.parseDouble(this.getSecondParametr()));
    }

    public boolean stackIsEmpty(){
        return this.stack.isEmpty();
    }

    public Double stackGetFirst() throws EmptyStackException{
        if (!this.stack.isEmpty()) {
            return this.stack.getFirst();
        }
        else{
            throw new EmptyStackException();
        }

    }


}