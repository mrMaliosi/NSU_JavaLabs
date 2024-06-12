package ru.nsu.ccfit.malinovskii.Model.Tetramino;

import java.util.Random;

public class Block {
    int x;
    int y;
    int color;

    public Block(){
        this.x = -1;
        this.y = -1;
        this.color = -1;
    }

    public Block(int x, int y){
        Random rand = new Random();
        this.color = rand.nextInt(7);
        this.x = x;
        this.y = y;
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void changePos(int x, int y){
        this.x += x;
        this.y += y;
    }

    public int getX(){return this.x;}

    public int getY(){return this.y;}

    public int getColor(){return y;}
}
