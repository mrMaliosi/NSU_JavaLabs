package ru.nsu.ccfit.malinovskii.factory.storages;

import ru.nsu.ccfit.malinovskii.FactoryObject;

import java.util.LinkedList;

public class Storage {
    protected int maxSize;
    protected int size;
    protected LinkedList <FactoryObject> FactoryObjects;

    Storage(){
        this.maxSize = 0;
        this.size = 0;
        this.FactoryObjects = null;
    }


    public void initialize(int maxSize){
        this.maxSize = maxSize;
        this.size = 0;
        this.FactoryObjects = new LinkedList <FactoryObject>();
    }

    public Boolean push(FactoryObject factoryObject){
        if (this.size < this.maxSize) {
            this.FactoryObjects.push(factoryObject);
            ++this.size;
        }
        else{
            return false;
        }
        return true;
    }

    public FactoryObject getFactoryObject(){
        if (!FactoryObjects.isEmpty()) {
            --this.size;
            return this.FactoryObjects.removeFirst();
        } else {
            return null;
        }
    }

    public Boolean isEmpty(){
        return this.size <= 0;
    }

    public int getSize(){
        return this.size;
    }

    public int getMaxSize(){
        return this.maxSize;
    }
}
