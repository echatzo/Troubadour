package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.Troubadour;

public class EndGameState extends State {

    private Texture background;
    private Texture playTexture;

    public EndGameState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("Victorybackground.png");
    }

    @Override
    public void dispose() {
        background.dispose();
        playTexture.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
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
        sb.end();
    }

}
