package com.troubadour.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.troubadour.game.Troubadour;

public class Player {
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 25;
    private static final float MOVEMENT = 80;
    private Vector3 position;
    private Vector3 velocity;

    private Texture player;

    public Player(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        player = new Texture("player.png");


    }

    public void update(float dt){

        velocity.scl(dt);
        position.add(0, MOVEMENT*dt, 0);
        velocity.scl(1/dt);
        if(position.x<0){
            position.x=0;
        }
        if(position.x> (Troubadour.WIDTH /2)-(PLAYER_WIDTH)){
            position.x=(Troubadour.WIDTH /2)-(PLAYER_WIDTH);
        }

    }

    public Vector3 getPosition() {
        return position;
    }


    public Texture getTexture() {
        return player;
    }

    public void move(){
        position.x = (Gdx.input.getX()/2)-(PLAYER_WIDTH/2);
    }
}
