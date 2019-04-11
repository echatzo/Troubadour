package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BulletBoss3 {

    public static final int BULLET_LENGTH = 24;
    public static final int BULLET_WIDTH = 15;
    public static final float SPEED_X = 100;
    public static final float SPEED_Y = 100;
    private float movement;

    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation bulletAnimation;

    public BulletBoss3(float x, float y, float x_target, float y_target){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(-((x-x_target)/Math.abs(x-x_target))*SPEED_X, -SPEED_Y, 0);
        texture = new Texture("tadpod.png");
        bulletAnimation = new Animation(new TextureRegion(texture), 4, 0.3f);
        bounds = new Rectangle(x, y, BULLET_WIDTH, BULLET_LENGTH);
    }

    public void update(float dt,float x_target){

        velocity.x = -((position.x-x_target)/Math.abs(position.x-x_target))*SPEED_X;

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
