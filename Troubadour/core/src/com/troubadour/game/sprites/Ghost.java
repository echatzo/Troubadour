package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

public class Ghost  {

    public static final int GHOST_HEIGHT = 30;
    public static final int GHOST_WIDTH = 25;
    private static final float MOVEMENT = 80;
    private static final float MAX_CONTROL_SPEED = 1000;
    private static final int FLUCTUATION = 180;
    private static  final int ENEMY_GAP = 50;
    public static final float ENEMY_THICK = 25;
    public static final float ENEMY_LENGTH = 45;



    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    //private Animation mobAnimation;
    private Random rand;


    public Ghost(float x, float y) {
       position = new Vector3(x, y, 0);
       velocity = new Vector3(0, 0, 0);
       //il faut modifier pour qu'il ait la bonne taille
       texture = new Texture("enemyRezize.png");
       bounds = new Rectangle(x, y, GHOST_WIDTH, GHOST_HEIGHT);
       rand = new Random();
    }

    public Ghost(float y) {
        rand = new Random();
        position = new Vector3(rand.nextInt(FLUCTUATION), y, 0);
        velocity = new Vector3(0, 0, 0);
        //il faut modifier pour qu'il ait la bonne taille
        texture = new Texture("enemyRezize.png");
        bounds = new Rectangle(position.x, y, GHOST_WIDTH, GHOST_HEIGHT);

    }

    public void update(float dt) {

    }


    public Vector3 getPosition() {
        return position;
    }


    public Texture getTexture() {
        return texture;
    }

    public void reposition(float y){
        position.set(rand.nextInt(FLUCTUATION), y,0);
        bounds.setPosition(position.x, position.y);

    }

    public boolean collides(Rectangle playerHitBox){
        return playerHitBox.overlaps(bounds);
    }

    public void dispose() {
       texture.dispose();

    }


    public Rectangle getBounds() {
        return bounds;
    }


}
