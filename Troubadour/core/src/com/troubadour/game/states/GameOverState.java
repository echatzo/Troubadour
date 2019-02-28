package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.Troubadour;

public class GameOverState extends State {
    private Texture background;
    private Texture gameOver;
    private String yourScoreName;
    BitmapFont yourBitmapFontName;

    public GameOverState(GameStateManager gsm, int score) {
        super(gsm);
        yourBitmapFontName = new BitmapFont();
        yourScoreName = "score: " + score;
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");
        gameOver = new Texture("GameOver.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.pop();
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
        sb.draw(gameOver, cam.position.x-200/2, cam.position.y - 150/2, 200, 150);
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.draw(sb, yourScoreName, 80, cam.position.y  - (100));
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
