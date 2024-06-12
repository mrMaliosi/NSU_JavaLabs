package ru.nsu.ccfit.malinovskii.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerScore {
    private final String player;
    private final int score;

    public PlayerScore(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }
}