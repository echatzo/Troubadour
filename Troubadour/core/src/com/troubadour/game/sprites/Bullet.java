package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;

public class Bullet {
    public static final int BULLET_SIZE = 8;
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Circle bounds;
    private Animation bulletAnimation;
    public Bullet(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("bulletAnimation.png");
        bulletAnimation = new Animation(new TextureRegion(texture), 2, 0.1f);
        bounds = new Circle(x, y, BULLET_SIZE);
    }
    public TextureRegion getTexture() {
        return bulletAnimation.getFrame();
    }
}
