
package com.troubadour.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.troubadour.game.Troubadour;

public class PauseState extends State {

    private Texture background;
    private TextButton resume, music;
    private Stage stage;
    private Skin skin;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    public PauseState(final GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));
        resume = new TextButton("Resume", skin);
        resume.setSize(col_width*6,row_height*2);
        resume.setPosition( col_width*3,row_height*7);
        resume.getLabel().setFontScale(col_width/23,row_height/23);
        resume.setChecked(false);
        resume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try
                {
                    Thread.sleep(500);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                gsm.pop();
            }
        });
        stage.addActor(resume);

        music = new TextButton("Mute Music", skin);
        music.setSize(col_width*6/2,row_height*2/2);
        music.setPosition( col_width*8,row_height );
        //music.getLabel().setFontScale(col_width/23,row_height/23);
        music.setChecked(false);
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Troubadour.mute) {
                    Troubadour.mute = false;
                    Troubadour.playMusic();

                }
                else {
                    Troubadour.mute = true;
                    Troubadour.muteMusic();
                }
            }
        });
        stage.addActor(music);
    }

    @Override
    public void dispose() {
        background.dispose();
        skin.dispose();
        System.out.println("Pause State Disposed");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
           // gsm.pop();
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

        stage.act();
        stage.draw();
    }

}