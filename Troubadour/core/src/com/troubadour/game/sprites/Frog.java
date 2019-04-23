package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Frog extends Mob{
    public static final int HEIGHT = 29;
    public static final int WIDTH = 37;




    private Vector3 position;
    private Texture texture;
    private Animation animation;
    private Rectangle bounds;
    private int lifeCount;


    public Frog(float x, float y) {
        super(x,y);

         lifeCount=5;
         texture = new Texture("frog.png");

        position = new Vector3(x, y, 0);
        animation = new Animation(new TextureRegion(texture), 3, 0.8f);
        bounds = new Rectangle(position.x, y, WIDTH, HEIGHT);

    }

    public void update(float dt) {
        animation.update(dt);
    }


    public Vector3 getPosition() {
        return super.getPosition();
    }



    public TextureRegion getTexture() {

        return animation.getFrame();
    }

    public boolean collides(Rectangle playerHitBox){

        return playerHitBox.overlaps(bounds);
    }

    public void dispose() {
        texture.dispose();
    }

    public void hurt() {
        lifeCount--;
    }


    public int getLifeCount() {
        return lifeCount;
    }


    public Rectangle getBounds() {

        return bounds;
    }
}
