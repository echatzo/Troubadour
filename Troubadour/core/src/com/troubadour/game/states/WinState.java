package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.Troubadour;

public class WinState extends State {
    private Texture background;
    private Texture gameOver;
    private String yourScoreName;
    private String bestScore;
    BitmapFont yourBitmapFontName;

    public WinState(GameStateManager gsm, int score,int world) {
        super(gsm);
        yourBitmapFontName = new BitmapFont();
        yourScoreName = "score: " + score;
        Save.addHighScore(score,world);
        Save.save();
        bestScore = "Best score: "+Save.getHighScores(world);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");
        gameOver = new Texture("Victory.png");
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
        sb.draw(gameOver, cam.position.x-200/2, cam.position.y - 150/2, 200, 150);
        yourBitmapFontName.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        yourBitmapFontName.draw(sb, yourScoreName, 100, cam.position.y  - (100));
        yourBitmapFontName.draw(sb, bestScore, 100 , cam.position.y  - (150));
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
