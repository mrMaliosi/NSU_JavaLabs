package ru.nsu.ccfit.malinovskii.Model.Tetramino;

import java.util.Random;

public class Tetramino {
    Block[] mino  = new Block[4];
    String name;
    int rotation = 0;

    public Tetramino(){
        Random rand = new Random();
        int type = rand.nextInt(7);
        for (int i = 0; i < 4; ++i){
            mino[i] = new Block();
        }

        switch (type){
            case 0:
                this.name = "stick";
                mino[0].setPos(3, 0);
                mino[1].setPos(4, 0);
                mino[2].setPos(5, 0);
                mino[3].setPos(6, 0);
                break;
            case 1:
                this.name = "square";
                mino[0].setPos(4, 0);
                mino[1].setPos(4, 1);
                mino[2].setPos(5, 0);
                mino[3].setPos(5, 1);
                break;
            case 2:
                this.name = "T";
                mino[0].setPos(3, 0);
                mino[1].setPos(4, 0);
                mino[2].setPos(5, 0);
                mino[3].setPos(4, 1);
                break;
            case 3:
                this.name = "Z";
                mino[0].setPos(3, 0);
                mino[1].setPos(4, 0);
                mino[2].setPos(4, 1);
                mino[3].setPos(5, 1);
                break;
            case 4:
                this.name = "notZ";
                mino[0].setPos(5, 0);
                mino[1].setPos(4, 0);
                mino[2].setPos(4, 1);
                mino[3].setPos(3, 1);
                break;
            case 5:
                this.name = "L";
                mino[0].setPos(3, 0);
                mino[1].setPos(4, 0);
                mino[2].setPos(5, 0);
                mino[3].setPos(5, 1);
                break;
            case 6:
                this.name = "notL";
                mino[0].setPos(3, 1);
                mino[1].setPos(4, 1);
                mino[2].setPos(5, 1);
                mino[3].setPos(5, 0);
                break;
        }

    }

    public int getX(int idx){
        return mino[idx].getX();
    }

    public int getY(int idx){
        return mino[idx].getY();
    }

    public int getColor(){
        return mino[0].getColor();
    }

    public void changePos(int x, int y){
        for (int i = 0; i < 4; ++i){
            mino[i].changePos(x, y);
        }
    }

    public void rotate(int dimension){
        //По часовой 0, иначе 1
        switch (this.name) {
            case "stick":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, 1);
                        mino[3].changePos(-2, 2);
                        break;
                    case 90:
                        mino[0].changePos(1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(-2, -2);
                        break;
                    case 180:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, -1);
                        mino[3].changePos(2, -2);
                        break;
                    case 270:
                        mino[0].changePos(-1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(2, 2);
                        break;
                }
                break;
            case "L":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, 1);
                        mino[3].changePos(-2, 0);
                        break;
                    case 90:
                        mino[0].changePos(1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(0, -2);
                        break;
                    case 180:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, -1);
                        mino[3].changePos(2, 0);
                        break;
                    case 270:
                        mino[0].changePos(-1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(0, 2);
                        break;
                }
                break;
            case "notL":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, 1);
                        mino[3].changePos( 0, 2);
                        break;
                    case 90:
                        mino[0].changePos(1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(-2, 0);
                        break;
                    case 180:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, -1);
                        mino[3].changePos(0, -2);
                        break;
                    case 270:
                        mino[0].changePos(-1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(2, 0);
                        break;
                }
                break;
            case "T":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, 1);
                        mino[3].changePos( -1, -1);
                        break;
                    case 90:
                        mino[0].changePos(1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(1, -1);
                        break;
                    case 180:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, -1);
                        mino[3].changePos(1, 1);
                        break;
                    case 270:
                        mino[0].changePos(-1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(-1, 1);
                        break;
                }
                break;
            case "notZ":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(0, -2);
                        break;
                    case 90:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(0, 2);
                        break;
                    case 180:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(0, -2);
                        break;
                    case 270:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(0, 2);
                        break;
                }
                break;
            case "Z":
                switch ((rotation + 180 * dimension) % 360) {
                    case 0:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(-2, 0);
                        break;
                    case 90:
                        mino[0].changePos(-1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(1, 1);
                        mino[3].changePos(2, 0);
                        break;
                    case 180:
                        mino[0].changePos(1, -1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, -1);
                        mino[3].changePos(-2, 0);
                        break;
                    case 270:
                        mino[0].changePos(1, 1);
                        mino[1].changePos(0, 0);
                        mino[2].changePos(-1, 1);
                        mino[3].changePos(0, -2);
                        break;
                }
                break;

        }
        rotation = ((rotation + 180*dimension + 90)%360);
    }
}
