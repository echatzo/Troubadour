package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

//base model for enemies
public abstract class Mob {
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation mobAnimation;
    private Random rand;
    public static final int HEIGHT = 26;
    public static final int WIDTH = 19;

    private Mob(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("squeleton.png");
        mobAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, WIDTH, HEIGHT);
        rand = new Random();
    }

    public void update(float dt) {
        mobAnimation.update(dt);
    }

    public Vector3 getPosition(){
        return position;
    }

    public abstract Texture getTexture();

    public abstract void dispose();

    public abstract Rectangle getBounds();





}
