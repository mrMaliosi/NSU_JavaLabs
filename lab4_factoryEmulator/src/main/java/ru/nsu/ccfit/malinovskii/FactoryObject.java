package ru.nsu.ccfit.malinovskii;

import java.util.Date;
import java.util.LinkedList;

public class FactoryObject {
    int id;
    String type;
    int providerID;
    LinkedList<FactoryObject> details = null;

    public FactoryObject(String typeName, int id, int providerID) {
        this.type = typeName;
        this.id = id;
        this.providerID = providerID;
        this.details = null;
    }

    public String getType() {
        return this.type;
    }

    public int getID() {
        return this.id;
    }

    public int getProviderID() {
        return this.providerID;
    }

    public void pushDetail(FactoryObject detail){
        if (this.details == null){
            this.details = new LinkedList<>();
        }
        this.details.push(detail);
    }

    public FactoryObject getDetail(int index){
        if (this.details.size() > index){
            return this.details.get(index);
        }
        return null;
    }

    public void printFactoryObjectInfo(int dealerID){
        Date currentDate = new Date();
        if (this.details != null){
            System.out.println(currentDate + ": Dealer " + dealerID + ": ID " + this.id + " (Body: " + getDetail(1).getID() + ", Motor: " + getDetail(2).getID() + ", Accessory: " + getDetail(0).getID() + ")");
        }
        else {
            System.out.println(currentDate + ": Dealer " + dealerID + ": ID " + this.id);
        }

    }
}
