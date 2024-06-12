package ru.nsu.ccfit.malinovskii.factory.dealerships;

public class CarDealership implements Dealership{
    private final int dealersCount;

    public CarDealership() {
        this.dealersCount = 0;
    }
    public CarDealership(int dealersCount) {
        this.dealersCount = dealersCount;
    }

    public void initialize (int dealersCount){
        dealersCount = dealersCount;
    }

    public int getDealersCount() {return this.dealersCount;}
}
