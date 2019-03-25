package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Boss1 {
    //boss du world 2
    public static final int HEIGHT = 60;
    public static final int WIDTH = 60;

    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture texture;

    //private Texture hitTexture;
    //private Animation animationHit; //Player animation when hit
    private Animation animation;
    private Animation animationGood;

    private int lifeCount;

    public Boss1(){

        lifeCount=150;

        position = new Vector3(0, 0, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("enemysque.png");
        //hitTexture = new Texture("troubadouranimationHit.png");
        animationGood = new Animation(new TextureRegion(texture), 3, 0.2f);
        //playerAnimationHit = new Animation(new TextureRegion(hitTexture), 3, 0.1f);
        animation = animationGood;
        bounds = new Rectangle(position.x, position.y, WIDTH, HEIGHT);

    }
}
