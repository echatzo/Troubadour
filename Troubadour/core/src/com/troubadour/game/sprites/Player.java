package com.troubadour.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.troubadour.game.Troubadour;

public class Player {
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 25;
    public float MOVEMENT = 120;
    private static final float MAX_CONTROL_SPEED = 800;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds; //player hitBox

    private Texture texture;
    private Animation playerAnimationGood; //Player animation

    private Texture hitTexture;
    private Animation playerAnimationHit; //Player animation when hit

    private Animation playerAnimation; //Current player animation



    //player life characteristics
    private Texture life;
    public Animation lifeAnimation;
    private int lifeCount;
    private float lifeTimer; //time remaining before the player can be hit again


    public Player(int x, int y){

        lifeCount=3;
        lifeTimer=-5f;
        life = new Texture("life.png");
        lifeAnimation= new Animation(new TextureRegion(life), 2, 0.5f);

        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("troubadouranimation.png");
        hitTexture = new Texture("troubadouranimationHit.png");
        playerAnimationGood = new Animation(new TextureRegion(texture), 3, 0.2f);
        playerAnimationHit = new Animation(new TextureRegion(hitTexture), 3, 0.1f);
        playerAnimation=playerAnimationGood;
        bounds = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT-10);

    }

    public void update(float dt){
        playerAnimation.update(dt);
        lifeAnimation.update(dt);

        velocity.scl(dt);
        position.add(velocity.x, MOVEMENT*dt, 0);

        if(position.x<0){
            position.x=0;
        }
        if(position.x> (Troubadour.WIDTH /2)-(PLAYER_WIDTH)){
            position.x=(Troubadour.WIDTH /2)-(PLAYER_WIDTH);
        }

        bounds.setPosition(position.x, position.y);

    }

    public Vector3 getPosition() {
        return position;
    }


    public TextureRegion getTexture() {
        return playerAnimation.getFrame();
    }

    //moves the player toward the finger
    public void move(){
        float fingerPosition=(((Gdx.input.getX()*240)/Gdx.app.getGraphics().getWidth())- PLAYER_WIDTH/2);
        if(this.getPosition().x < fingerPosition -10){
            velocity.set(MAX_CONTROL_SPEED, 0, 0);
        }
        else if(this.getPosition().x > fingerPosition+10){
            velocity.set(-MAX_CONTROL_SPEED, 0, 0);
        }
        else {
            position.x = fingerPosition;
        }
    }
    public void dispose(){
        texture.dispose();
        life.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public int getLifeCount(){
        return this.lifeCount;
    }

    public void decLifeCount(){
        this.lifeCount--;
        System.out.println(lifeCount);
    }
    public void incLifeCount(){
        this.lifeCount++;
    }

    public float getLifeTimer(){
        return lifeTimer;
    }

    public void incLifeTimer(float dt){
        lifeTimer+=dt;
    }

    public void resetLifeTimer(){
        lifeTimer = 0f;
    }

    public void setTexture(int n){
        if (n==1){
            playerAnimation=playerAnimationGood;
        }
        if (n==2){
            playerAnimation=playerAnimationHit;
        }
    }

}
