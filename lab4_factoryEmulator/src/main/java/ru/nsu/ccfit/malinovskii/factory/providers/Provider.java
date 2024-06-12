package ru.nsu.ccfit.malinovskii.factory.providers;

public class Provider {
    int suppliersCount;

    Provider(){
        this.suppliersCount = 0;
    }

    public void initialize(int suppliersCount){
        this.suppliersCount = suppliersCount;
    }

    public int getSupplierCount(){return this.suppliersCount;}
}
