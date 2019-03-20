package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.util.Random;

public class Ghost  {

    public static final int HEIGHT = 26;
    public static final int WIDTH = 19;




    private Vector3 position;
    private Texture texture;
    private Animation mobAnimation;
    private Rectangle bounds;


    public Ghost(float x, float y) {
       position = new Vector3(x, y, 0);
       texture = new Texture("squeleton.png");
       mobAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
       bounds = new Rectangle(position.x+5, y, WIDTH, HEIGHT);
       rand = new Random();
    }

    public void update(float dt) {
        mobAnimation.update(dt);
    }


    public Vector3 getPosition() {

        return position;
    }



    public TextureRegion getTexture() {

        return mobAnimation.getFrame();
    }

    public boolean collides(Rectangle playerHitBox){

        return playerHitBox.overlaps(bounds);
    }

    public void dispose() {
       texture.dispose();
    }


    public Rectangle getBounds() {

        return bounds;
    }


}
