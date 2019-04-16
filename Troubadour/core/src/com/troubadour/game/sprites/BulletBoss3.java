package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class BulletBoss3 {

    public static final int BULLET_LENGTH = 24;
    public static final int BULLET_WIDTH = 15;
    public static final float SPEED = 150;
    private float movement;

    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation bulletAnimation;

    public BulletBoss3(float x, float y, float x_target, float y_target){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("tadpod.png");
        bulletAnimation = new Animation(new TextureRegion(texture), 4, 0.3f);
        bounds = new Rectangle(x, y, BULLET_WIDTH, BULLET_LENGTH);
    }

    public void update(float dt,float x_target, float y_target){

        float R = (position.x-x_target)/(position.y-y_target);
        float Vx = (float) (SPEED/Math.sqrt(1/Math.pow(R,2.0)+1));
        if (position.x > x_target){
            velocity.x = -Vx;
        }
        else {
            velocity.x = Vx;
        }
        velocity.y = (float) -(SPEED/Math.sqrt(Math.pow(R,2.0)+1))-60;

        //si proche de la cible continue tout droit
        if(Math.abs(position.y - y_target)<10) velocity.x = 0;


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
