package ru.nsu.ccfit.malinovskii.factory.controllers;

public class CarAssemblerController implements Controller{
    private final int workersCount;
    CarAssemblerController(int workersCount){
        this.workersCount = workersCount;
    }
}
