package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.Troubadour;

public class MenuState extends State {

    private Texture background;
    private Texture playbutton;

    public MenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");
        playbutton = new Texture("play.png");
    }

    @Override
    public void dispose() {
        background.dispose();
        playbutton.dispose();
        System.out.println("Menu State Disposed");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            //world 1
            //gsm.push(new PlayState(gsm));
            //world 2
            gsm.push(new PlayStateWorld2(gsm));
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
        sb.draw(playbutton, cam.position.x-60/2, cam.position.y - 40/2, 60, 40);
        sb.end();
    }

}
