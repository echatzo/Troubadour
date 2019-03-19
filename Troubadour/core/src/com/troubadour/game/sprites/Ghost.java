package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

public class Ghost  {

    public static final int GHOST_HEIGHT = 26;
    public static final int GHOST_WIDTH = 34;
    private static final float MOVEMENT = 80;
    private static final float MAX_CONTROL_SPEED = 1000;
    private static final int FLUCTUATION = 100; //180;
    private static  final int ENEMY_GAP = 80;
    public static final float ENEMY_THICK = 25;
    public static final float ENEMY_LENGTH = 45;



    private Vector3 positionLeft, positionRight;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle boundsLeft, boundsRight;
    //private Animation mobAnimation;
    private Random rand;


    //useless method
    public Ghost(float x, float y) {
       positionLeft = new Vector3(x, y, 0);
       positionRight = new Vector3(x, y, 0);
       velocity = new Vector3(0, 0, 0);
       texture = new Texture("enemyRezizev6.png");
       boundsLeft = new Rectangle(x, y, GHOST_WIDTH, GHOST_HEIGHT);
       boundsRight = new Rectangle(x, y, GHOST_WIDTH, GHOST_HEIGHT);
       rand = new Random();
    }

    public Ghost(float y) {
        rand = new Random();
        positionLeft = new Vector3(rand.nextInt(FLUCTUATION), y, 0);
        positionRight = new Vector3(110+rand.nextInt(FLUCTUATION), y, 0);
        velocity = new Vector3(0, 0, 0);
        //il faut modifier pour qu'il ait la bonne taille : ok
        texture = new Texture("enemyRezizev6.png");
        boundsLeft = new Rectangle((positionLeft.x)+5, y, GHOST_WIDTH, GHOST_HEIGHT);
        boundsRight = new Rectangle((positionRight.x)+5, y, GHOST_WIDTH, GHOST_HEIGHT);

    }

    public void update(float dt) {

    }


    public Vector3 getPositionLeft() {
        return positionLeft;
    }

    public Vector3 getPositionRight() {
        return positionRight;
    }


    public Texture getTexture() {
        return texture;
    }

    public void reposition(float y){
        positionLeft.set(rand.nextInt(FLUCTUATION), y,0);
        positionRight.set(110+rand.nextInt(FLUCTUATION), y,0);
        boundsLeft.setPosition(positionLeft.x, positionLeft.y);
        boundsRight.setPosition(positionRight.x, positionRight.y);

    }

    public boolean collides(Rectangle playerHitBox){
        return playerHitBox.overlaps(boundsLeft)||playerHitBox.overlaps(boundsRight);
    }

    public void dispose() {
       texture.dispose();

    }


    public Rectangle getLeftBounds() {
        return boundsLeft;
    }

    public Rectangle getRightBounds() {
        return boundsRight;
    }


}
