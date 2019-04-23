package com.troubadour.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.troubadour.game.Troubadour;

import java.util.Random;

public class Ghost extends Mob {

    public static final int HEIGHT = 20;
    public static final int WIDTH = 21;
    public static final int MOVEMENT = 80;




    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Animation animation;
    private Rectangle bounds;
    private Random rand;
    private int lifeCount;
    private boolean dark;
    int signe;

    public Ghost(float x, float y) {
        super(x,y);
        rand = new Random();
        dark=false;
        if(rand.nextInt(5)==4){
            dark = true;
            //toujours 3 car on a pas de skin differents
            lifeCount=3;
            //texture = new Texture("squeletondark.png");
            texture = new Texture("ghost.png");
        }
        else{
            dark=false;
            lifeCount=3;
            //texture = new Texture("squeleton.png");
            texture = new Texture("ghost.png");
        }
        position = new Vector3(x, y, 0);
        if(rand.nextBoolean()) signe = 1;
        else signe = -1;
        velocity = new Vector3(signe*MOVEMENT, 0, 0);
        //animation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x, y, WIDTH, HEIGHT);

    }

    public void update(float dt) {
        //animation.update(dt);
        if(position.x <0) velocity.x = MOVEMENT;
        if(position.x > Troubadour.WIDTH/2) velocity.x=-MOVEMENT;

        position.add(velocity.x*dt, 0, 0);

       bounds.setPosition(position.x, position.y);
    }


    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity(){
        return velocity;
    }

    public boolean isDark(){
        return dark;
    }


    public TextureRegion getTexture() {

        return animation.getFrame();
    }

    public Texture getTexture2(){
        return texture;
    }

    public boolean collides(Rectangle playerHitBox){

        return playerHitBox.overlaps(bounds);
    }

    public void dispose() {
       texture.dispose();
    }

    public void hurt() {
        lifeCount--;
        /*if(!dark) {
            if(lifeCount==2) {
                texture = new Texture("squeletonhurt1.png");
            }
            else if (lifeCount==1){
                texture = new Texture("squeletonhurt2.png");
            }
            animation = new Animation(new TextureRegion(texture), 3, 0.5f);
        }*/
    }


    public int getLifeCount() {
        return lifeCount;
    }


    public Rectangle getBounds() {

        return bounds;
    }


}
