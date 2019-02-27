package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
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
    private Rectangle boundsLeft, boundsRight;
    private Random rand;

    public Wall(float y){
        leftWall = new Texture("wall.png");
        rightWall = new Texture("wall.png");
        rand = new Random();

        posrightWall = new Vector2(rand.nextInt(FLUCTUATION)+ WALL_GAP + LOWEST_OPENING, y);
        posleftWall = new Vector2(posrightWall.x - WALL_GAP - 200, y);

        boundsRight = new Rectangle(posrightWall.x, posrightWall.y, WALL_LENGTH , WALL_THICK );
        boundsLeft = new Rectangle(posleftWall.x, posleftWall.y, WALL_LENGTH , WALL_THICK );
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
        boundsRight.setPosition(posrightWall.x, posrightWall.y);
        boundsLeft.setPosition(posleftWall.x, posleftWall.y);
    }

    public boolean collides(Rectangle playerHitBox){
        return playerHitBox.overlaps(boundsRight)||playerHitBox.overlaps(boundsLeft);
    }

    public void dispose(){
        rightWall.dispose();
        leftWall.dispose();
    }
}
