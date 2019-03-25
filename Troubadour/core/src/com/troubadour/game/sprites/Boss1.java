package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.troubadour.game.Troubadour;

public class Boss1 {
    //boss du world 2
    public static final int HEIGHT = 60;
    public static final int WIDTH = 60;
    public static final int MOVEMENT = 60;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;
    private Texture hitTexture;

    private Animation animationHit;
    private Animation animation;
    private Animation animationGood;

    private int lifeCount;

    public Boss1(){

        lifeCount=150;

        position = new Vector3(0, 0, 0);
        velocity = new Vector3(MOVEMENT, 0, 0);
        texture = new Texture("Boss1.png");
        hitTexture = new Texture("Boss1_hit.png");
        animationGood = new Animation(new TextureRegion(texture), 3, 0.2f);
        animationHit = new Animation(new TextureRegion(hitTexture), 3, 0.1f);
        animation = animationGood;
        bounds = new Rectangle(position.x, position.y, WIDTH, HEIGHT);

    }

    public void update(float dt){
        animation.update(dt);

        velocity.scl(dt);
        position.add(velocity.x, 0, 0);

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

    public void dispose(){
        texture.dispose();
    }

    public void setTexture(boolean flag){
        if(flag){ animation = animationHit; }
        else{ animation = animationGood; }
    }

    public void hurt(){
        this.lifeCount--;
    }

    public void shotgun(){

    }

    public void shotray(){

    }

    public void shotbomb(){

    }

}
