package com.troubadour.game.states;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.troubadour.game.Troubadour;

import java.awt.Font;

public class ChooseWorldState extends State {

    private Texture background;
    private Stage stage;
    private Skin skin;
    private TextButton world1, world2, world3, world4, music,reset;
    private BitmapFont font;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    private FitViewport fitViewport;

    public ChooseWorldState(final GameStateManager gsm){
        super(gsm);
        Save.load();
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");

        //fitViewport = new FitViewport(Troubadour.WIDTH, Troubadour.HEIGHT);

        //stage = new Stage(fitViewport);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        world1 = new TextButton("World 1", skin);
        world1.setSize(col_width*5,row_height*2);
        world1.setPosition(col_width*1,row_height*8);
        //world1.setTransform(true);
        //world1.scaleBy(2f);
        world1.getLabel().setFontScale(col_width/23,row_height/23);
        world1.setChecked(false);
        world1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new PlayState(gsm));
            }
        });
        stage.addActor(world1);

        if(Save.gd.getHighScores(1)>25) {
            world2 = new TextButton("World 2", skin);
            world2.setSize(col_width * 5, row_height * 2);
            world2.setPosition(col_width * 6, row_height * 8);
            world2.getLabel().setFontScale(col_width / 23, row_height / 23);
            world2.setChecked(false);
            world2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gsm.set(new PlayStateWorld2(gsm));
                }
            });
            stage.addActor(world2);

            if (Save.gd.getHighScores(2) > 49) {
                world3 = new TextButton("World 3", skin);
                world3.setSize(col_width * 5, row_height * 2);
                world3.setPosition(col_width * 1, row_height * 5);
                world3.getLabel().setFontScale(col_width / 23, row_height / 23);
                world3.setChecked(false);
                world3.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gsm.set(new PlayStateWorld3(gsm));
                    }
                });
                stage.addActor(world3);

                if (Save.gd.getHighScores(3) > 49) {
                    world4 = new TextButton("World 4", skin);
                    world4.setSize(col_width * 5, row_height * 2);
                    world4.setPosition(col_width * 6, row_height * 5);
                    world4.getLabel().setFontScale(col_width / 23, row_height / 23);
                    world4.setChecked(false);
                    world4.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            gsm.set(new PlayStateWorld4(gsm));
                        }
                    });
                    stage.addActor(world4);
                }
            }
        }


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

        reset = new TextButton("Reset scores", skin);
        reset.setSize(col_width*6/2,row_height*2/2);
        reset.setPosition( col_width*2,row_height );
        //music.getLabel().setFontScale(col_width/23,row_height/23);
        reset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Save.gd.resetScores();
                Save.save();
                gsm.set(new MenuState(gsm));
            }
        });
        stage.addActor(reset);


    }


    @Override
    public void dispose() {
        background.dispose();

        System.out.println("Menu State Disposed");
    }

    @Override
    public void handleInput() {

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
