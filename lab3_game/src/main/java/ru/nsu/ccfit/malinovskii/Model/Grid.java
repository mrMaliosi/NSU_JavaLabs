package ru.nsu.ccfit.malinovskii.Model;

import java.util.Random;

public class Grid {
    int color;
    int state;

    public Grid(){
        this.color = -1;
        this.state = 0;
    }

    public void setState(int newState){
        this.state = newState;
    }

    public void setColor(int newColor){
        this.color = newColor;
    }

    public int getState(){
        return this.state;
    }


    public int getColor(){
        return this.color;
    }
}
