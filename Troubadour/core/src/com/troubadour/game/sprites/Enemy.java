package com.troubadour.game.sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    public static final int PLAYER_HEIGHT = 30;
    public static final int PLAYER_WIDTH = 25;
    private Animation enemyAnimation;



    public TextureRegion getTexture() {
        return enemyAnimation.getFrame();
    }
}
