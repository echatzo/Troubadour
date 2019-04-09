package com.troubadour.game.states;

import java.io.Serializable;

public class GameData implements Serializable {

    private static final long serialVersionUID = 1;

    private final int NBR_WORLDS = 4;
    private int[] highScores;

    public GameData() {
        highScores = new int[NBR_WORLDS];
    }

    // sets up an empty high scores table
    public void init() {
        for(int i = 0; i < NBR_WORLDS; i++) {
            highScores[i] = 0;
        }
    }


    public int getHighScores(int i) { return highScores[i-1]; }

    public void addHighScore(int newScore, int world) {
        if(newScore > highScores[world -1]) {
            highScores[world - 1] = newScore;
        }
    }

    public void resetScores(){
        for(int i = 0; i < NBR_WORLDS; i++) {
            highScores[i] = 0;
        }
    }
}