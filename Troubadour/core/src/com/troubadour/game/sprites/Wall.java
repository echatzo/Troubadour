package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Wall {
    public static final float WALL_THICK = 30;
    public static final float WALL_LENGTH = 200;
    private static final int FLUCTUATION = 190;
    private static  final int WALL_GAP = 50;
    private static final int LOWEST_OPENING = 10;
    private Texture leftWall, rightWall;
    private Vector2 posleftWall, posrightWall;
    private Random rand;

    public Wall(float y){
        leftWall = new Texture("wall.png");
        rightWall = new Texture("wall.png");
        rand = new Random();

        posrightWall = new Vector2(rand.nextInt(FLUCTUATION)+ WALL_GAP + LOWEST_OPENING, y);
        posleftWall = new Vector2(posrightWall.x - WALL_GAP - 200, y);

    }

    public Texture getleftWall() {
        return leftWall;
    }

    public Texture getrightWall() {
        return rightWall;
    }

    public Vector2 getPosleftWall() {
        return posleftWall;
    }

    public Vector2 getPosrightWall() {
        return posrightWall;
    }

    public void reposition(float y){
        posrightWall.set(rand.nextInt(FLUCTUATION)+ WALL_GAP + LOWEST_OPENING, y);
        posleftWall.set(posrightWall.x - WALL_GAP - 200, y);
    }
}