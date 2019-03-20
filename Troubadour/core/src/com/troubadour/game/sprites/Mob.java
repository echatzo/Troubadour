package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

//base model for enemies
public abstract class Mob {
    private Vector3 position;
    private Texture texture;
    private Rectangle bounds;
    private Animation animation;
    public static final int HEIGHT = 26;
    public static final int WIDTH = 19;

    public Mob(float x, float y) {
        position = new Vector3(x, y, 0);
        texture = new Texture("squeleton.png");
        animation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(position.x+5, y, WIDTH, HEIGHT);
    }

    public void update(float dt) {
        animation.update(dt);
    }

    public Vector3 getPosition(){
        return position;
    }

    public TextureRegion getTexture(){
        return animation.getFrame();
    }

    public void dispose() {
        texture.dispose();
    }

    public Rectangle getBounds() {
        return bounds;
    }




}
