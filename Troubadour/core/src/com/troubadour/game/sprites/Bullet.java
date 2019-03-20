package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bullet {
    public static final int BULLET_SIZE = 8;
    private float movement = 200;

    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;
    private Rectangle bounds;
    private Animation bulletAnimation;

    public Bullet(float x, float y, float speed){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("bulletAnimation2.png");
        bulletAnimation = new Animation(new TextureRegion(texture), 2, 0.1f);
        bounds = new Rectangle(x, y, BULLET_SIZE, BULLET_SIZE);
        movement=speed;
    }

    public void update(float dt){

        velocity.scl(dt);
        position.add(0, movement *dt, 0);

        bounds.setPosition(position.x, position.y);
        bulletAnimation.update(dt);

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
    /*
    public void Shoot(Array<Bullet> projectiles,float x,float y){
        Bullet bullet = new Bullet(x,y);
        projectiles.add(bullet);
    }
    */

    public boolean collides(Rectangle ennemyHitBox){
        return ennemyHitBox.overlaps(bounds);
    }
}
