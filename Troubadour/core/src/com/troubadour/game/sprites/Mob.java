package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
//base model for enemies
public abstract class Mob {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation mobAnimation;

    private Mob(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
    }

    public abstract void update(float dt);

    public abstract Vector3 getPosition();

    public abstract Texture getTexture();

    public abstract void dispose();

    public abstract Rectangle getBounds();





}
