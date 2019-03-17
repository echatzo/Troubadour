package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.Troubadour;

public class MenuState extends State {

    private Texture background;
    private Texture playTexture;

    public MenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");
        playTexture = new Texture("play.png");
    }

    @Override
    public void dispose() {
        background.dispose();
        playTexture.dispose();
        System.out.println("Menu State Disposed");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            //world 1
            //gsm.set(new PlayState(gsm));
            //world 2
            //gsm.set(new PlayStateWorld2(gsm));
            //choose World
            gsm.set(new ChooseWorldState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.setProjectionMatrix(cam.combined);
        sb.draw(background, 0, 0, cam.viewportWidth, cam.viewportHeight);
        sb.draw(playTexture, cam.position.x-60/2, cam.position.y - 40/2, 60, 40);
        sb.end();
    }

}
