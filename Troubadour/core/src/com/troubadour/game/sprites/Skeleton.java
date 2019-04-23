package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

public class Skeleton extends Mob{

    public static final int HEIGHT = 26;
    public static final int WIDTH = 19;

    private Vector3 position;
    private Texture texture;
    private Animation animation;
    private Rectangle bounds;
    private Random rand;
    private int lifeCount;
    private boolean invincible;


    public Skeleton(float x, float y) {
        super(x,y);
        rand = new Random();
        invincible =false;
        if(rand.nextInt(3)==1){
            invincible = true;
            lifeCount=8;
            texture = new Texture("squeletondiams.png");
        }
        else{
            invincible =false;
            lifeCount=3;
            texture = new Texture("squeleton.png");
        }
        position = new Vector3(x, y, 0);
        animation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, y, WIDTH, HEIGHT);

    }

    public void update(float dt) {
        animation.update(dt);
    }


    public Vector3 getPosition() {
        return super.getPosition();
    }

    public boolean isInvincible(){
        return invincible;
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

        if(!invincible) {
            lifeCount--;
            if(lifeCount==2) {
                texture = new Texture("squeletonhurt1.png");
            }
            else if (lifeCount==1){
                texture = new Texture("squeletonhurt2.png");
            }
            animation = new Animation(new TextureRegion(texture), 3, 0.5f);
        }
    }


    public int getLifeCount() {
        return lifeCount;
    }


    public Rectangle getBounds() {

        return bounds;
    }
}
