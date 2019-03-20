package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

public class Ghost  {

    public static final int GHOST_HEIGHT = 26;
    public static final int GHOST_WIDTH = 19;
    private static final int FLUCTUATION = 100; //180;
    private static  final int ENEMY_GAP = 80;




    private Vector3 position;
    private Texture texture;
    private Animation mobAnimation;
    private Rectangle bounds;
    //private Animation mobAnimation;
    private Random rand;

    //useless method
    public Ghost(float x, float y) {
       position = new Vector3(x, y, 0);
       texture = new Texture("squeleton.png");
       mobAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
       bounds = new Rectangle(position.x+5, y, GHOST_WIDTH, GHOST_HEIGHT);
       rand = new Random();
    }
/*
    public Ghost(float y) {
        rand = new Random();
        positionLeft = new Vector3(rand.nextInt(FLUCTUATION), y, 0);
        positionRight = new Vector3(110+rand.nextInt(FLUCTUATION), y, 0);
        //il faut modifier pour qu'il ait la bonne taille : ok
        texture = new Texture("squeleton.png");
        mobAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        boundsLeft = new Rectangle((positionLeft.x)+5, y, GHOST_WIDTH, GHOST_HEIGHT);
        boundsRight = new Rectangle((positionRight.x)+5, y, GHOST_WIDTH, GHOST_HEIGHT);

    }
*/
    public void update(float dt) {
        mobAnimation.update(dt);
    }


    public Vector3 getPosition() {
        return position;
    }



    public TextureRegion getTexture() {
        return mobAnimation.getFrame();
    }
/*
    public void reposition(float y){
        positionLeft.set(rand.nextInt(FLUCTUATION), y,0);
        positionRight.set(110+rand.nextInt(FLUCTUATION), y,0);
        boundsLeft.setPosition(positionLeft.x, positionLeft.y);
        boundsRight.setPosition(positionRight.x, positionRight.y);

    }
*/
    public boolean collides(Rectangle playerHitBox){
        return playerHitBox.overlaps(bounds);
    }

    public void dispose() {
       texture.dispose();

    }


    public Rectangle getBounds() {
        return bounds;
    }
/*
    public Rectangle getRightBounds() {
        return boundsRight;
    }
*/

}
