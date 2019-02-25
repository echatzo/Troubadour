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
        background = new Texture("background.png");
        playbutton = new Texture("play.png");
    }

    @Override
    public void dispose() {
        background.dispose();
        playbutton.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }


    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Troubadour.WIDTH, Troubadour.HEIGHT);
        sb.draw(playbutton, (Troubadour.WIDTH/2)- (playbutton.getWidth()/2), (Troubadour.HEIGHT /2) - (playbutton.getHeight()/2));
        sb.end();
    }

}
