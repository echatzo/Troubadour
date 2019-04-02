package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BulletBoss1 {

    public static final int BULLET_SIZE = 12;
    public static final double SPEED = 1;
    private float movement;

    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation bulletAnimation;

    public BulletBoss1(float x, float y, float x_target, float y_target){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(-(x-x_target), -(y-y_target), 0);
        System.out.println(velocity.x);
        System.out.println(velocity.y);
        texture = new Texture("BulletBoss1.png");
        bulletAnimation = new Animation(new TextureRegion(texture), 2, 0.3f);
        bounds = new Rectangle(x, y, BULLET_SIZE, BULLET_SIZE);
    }

    public void update(float dt){

        velocity.scl(dt);

        position.add(velocity.x, velocity.y, 0);

        bounds.setPosition(position.x, position.y);
        bulletAnimation.update(dt);

        velocity.scl(1/dt);

    }
    public TextureRegion getTexture() {
        return bulletAnimation.getFrame();
    }

    public Vector3 getPosition() {
        return position;
    }

    public void dispose(){
        texture.dispose();
    }

    public boolean collides(Rectangle ennemyHitBox){
        return ennemyHitBox.overlaps(bounds);
    }


}
