package ru.nsu.ccfit.malinovskii.factory.assemblers;

import ru.nsu.ccfit.malinovskii.FactoryObject;

public class MachineAssembler implements Assembler{
    private int workersCount;

    public MachineAssembler() {
        this.workersCount = 0;
    }
    public MachineAssembler(int workersCount) {
        this.workersCount = workersCount;
    }

    public void initialize (int workersCount){
        this.workersCount = workersCount;
    }

    public int getWorkersCount(){return this.workersCount;}

    public FactoryObject makeCar(String name, int id, int providerID){
        return new FactoryObject(name, id, providerID);
    }
}
