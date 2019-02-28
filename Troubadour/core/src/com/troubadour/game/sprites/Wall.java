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
    private Vector2 posLeftWall, posRightWall;
    private Rectangle boundsLeft, boundsRight;
    private Random rand;

    public Wall(float y){
        leftWall = new Texture("wall.png");
        rightWall = new Texture("wall.png");
        rand = new Random();

        posRightWall = new Vector2(rand.nextInt(FLUCTUATION)+ WALL_GAP + LOWEST_OPENING, y);
        posLeftWall = new Vector2(posRightWall.x - WALL_GAP - 200, y);

        boundsRight = new Rectangle(posRightWall.x, posRightWall.y, WALL_LENGTH , WALL_THICK );
        boundsLeft = new Rectangle(posLeftWall.x, posLeftWall.y, WALL_LENGTH , WALL_THICK );
    }

    public Texture getLeftWall() {
        return leftWall;
    }

    public Texture getRightWall() {
        return rightWall;
    }

    public Vector2 getPosLeftWall() {
        return posLeftWall;
    }

    public Vector2 getPosRightWall() {
        return posRightWall;
    }

    public void reposition(float y){
        posRightWall.set(rand.nextInt(FLUCTUATION)+ WALL_GAP + LOWEST_OPENING, y);
        posLeftWall.set(posRightWall.x - WALL_GAP - 200, y);
        boundsRight.setPosition(posRightWall.x, posRightWall.y);
        boundsLeft.setPosition(posLeftWall.x, posLeftWall.y);
    }

    public boolean collides(Rectangle playerHitBox){
        return playerHitBox.overlaps(boundsRight)||playerHitBox.overlaps(boundsLeft);
    }

    public void dispose(){
        rightWall.dispose();
        leftWall.dispose();
    }
}
