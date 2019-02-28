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
    private static final float MOVEMENT = 80;
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;

    private Texture life;
    public Animation lifeAnimation;
    private int lifeCount;

    private Texture player;
    private Animation playerAnimation;

    public Player(int x, int y){
        lifeCount=3;
        life = new Texture("life.png");
        lifeAnimation= new Animation(new TextureRegion(life), 2, 0.5f);
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("troubadouranimation.png");
        playerAnimation = new Animation(new TextureRegion(texture), 3, 0.2f);
        bounds = new Rectangle(x, y, PLAYER_WIDTH, PLAYER_HEIGHT);

    }

    public void update(float dt){
        playerAnimation.update(dt);
        lifeAnimation.update(dt);
        
        velocity.scl(dt);
        position.add(0, MOVEMENT*dt, 0);

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

    public void move(){
        position.x = (Gdx.input.getX()/2)-(PLAYER_WIDTH/2);
    }
    public void dispose(){
        texture.dispose();
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
