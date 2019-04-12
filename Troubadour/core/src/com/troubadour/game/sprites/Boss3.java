package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.troubadour.game.Troubadour;

public class Boss3 {
    //boss du world 4
    public static final int HEIGHT = 60;
    public static final int WIDTH = 60;
    public static final int MOVEMENT = 70;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;
    private Texture hitTexture;

    private Animation animationHit;
    private Animation animation;
    private Animation animationGood;

    private int lifeCount;

    public Boss3(float x, float y){

        lifeCount=100;

        position = new Vector3(x, y, 0);
        velocity = new Vector3(MOVEMENT, MOVEMENT, 0);
        texture = new Texture("ghostboss.png");
        hitTexture = new Texture("Boss1_hit.png");
        animationGood = new Animation(new TextureRegion(texture), 3, 0.5f);
        animationHit = new Animation(new TextureRegion(hitTexture), 3, 0.1f);
        animation = animationGood;
        bounds = new Rectangle(position.x, position.y, WIDTH, HEIGHT);

    }

    public void update(float dt){
        animation.update(dt);

        position.add(velocity.x*dt, 0, 0);

        if(position.x < 0){

            position.x = 0;
            velocity.x = MOVEMENT;

        }

        if(position.x > (Troubadour.WIDTH /2)-(WIDTH)){
            position.x=(Troubadour.WIDTH /2)-(WIDTH);
            velocity.x = - MOVEMENT;
        }


        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition(){ return position; }

    public Vector3 getVelocity(){ return velocity; }

    public void dispose(){
        texture.dispose();
    }

    public void setTexture(boolean flag){
        if(flag){ animation = animationHit; }
        else{ animation = animationGood; }
    }

    public Animation getAnimation(){
        return animation;
    }

    public void hurt(){
        this.lifeCount--;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public int getLifeCount(){
        return lifeCount;
    }

    public boolean collides(Rectangle ennemyHitBox){
        return ennemyHitBox.overlaps(bounds);
    }


    public void shotgun(){

    }

    public void shotray(){

    }

    public void shotbomb(){

    }

}
