package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Background {
    private Vector3 position;
    private Texture texture;
    private static final float MOVEMENT = 80;

    public Background(int x, int y){

        position = new Vector3 (x, y, 0);
        texture  = new Texture("ground.png");

    }


    public void reposition(){
        position.y+=800;
    }
    public Vector3 getPos(){
        return position;
    }

    public Texture getTexture(){
        return texture;
    }
    public void dispose(){
        texture.dispose();
    }
}
