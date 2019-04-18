package com.troubadour.game.states;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.troubadour.game.Troubadour;

import java.awt.Font;

public class ChooseWorldState extends State {

    private Texture background;
    private Stage stage;
    private Skin skin;
    private TextButton world1, world2, world3, world4, music,reset;
    private ImageButton w1, w2, w3, w4;
    private BitmapFont font;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;
    private int score1, score2, score3, score4;

    private FitViewport fitViewport;
    private Texture textw1, textw2, textw3, textw4, starW1, starW2, starW3,starW4;
    private TextureRegion textrw1,textrw2, textrw3, textrw4;
    private TextureRegionDrawable textrdw1,textrdw2, textrdw3, textrdw4;

    public ChooseWorldState(final GameStateManager gsm){
        super(gsm);
        Save.load();
        cam.setToOrtho(false, Troubadour.WIDTH , Troubadour.HEIGHT );
        background = new Texture("background.png");

        //fitViewport = new FitViewport(Troubadour.WIDTH, Troubadour.HEIGHT);
        //playTexture = new Texture("play.png");
        //stage = new Stage(fitViewport);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));


        textw1 = new Texture(Gdx.files.internal("levels_but/level 1.png"));
        textrw1 = new TextureRegion(textw1);
        textrdw1 = new TextureRegionDrawable(textrw1);
        w1 = new ImageButton(textrdw1);
        //w1.setTransform(true);
        //w1.setScale(2f);
        w1.setSize(col_width*3,row_height*1);
        w1.getImage().setFillParent(true);
        w1.setPosition(col_width*1 ,row_height*8);
        w1.setChecked(false);
        w1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gsm.set(new PlayState(gsm));
            }
        });
        w1.debug();
        stage.addActor(w1);
        score1= Save.gd.getHighScores(1);
        if(score1<25) {
            starW1 = new Texture("star0.png");
        }
        else if (score1<35){
            starW1 = new Texture("star.png");
        }
        else if (score1<50){
            starW1 = new Texture("star2.png");
        }
        else{
            starW1 = new Texture("star3.png");
        }

        textw2 = new Texture(Gdx.files.internal("levels_but/level 2.png"));
        textrw2 = new TextureRegion(textw2);
        textrdw2 = new TextureRegionDrawable(textrw2);
        w2 = new ImageButton(textrdw2);
        w2.setSize(col_width*3,row_height*1);
        w2.setPosition(col_width*6,row_height*8);
        w2.setChecked(false);
        w2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(score1>25) {
                    gsm.set(new PlayStateWorld2(gsm));
                }
            }
        });
        //w2.debug();
        w2.getImage().setFillParent(true);
        stage.addActor(w2);
        score2= Save.gd.getHighScores(2);
        if(score1<25) {
            starW2 = new Texture("lock.png");
        }
        else if(score2<50) {
            starW2 = new Texture("star0.png");
        }
        else if (score2<60){
            starW2 = new Texture("star.png");
        }
        else if (score2<75){
            starW2 = new Texture("star2.png");
        }
        else{
            starW2 = new Texture("star3.png");
        }

        textw3 = new Texture(Gdx.files.internal("levels_but/level 3.png"));
        textrw3 = new TextureRegion(textw3);
        textrdw3 = new TextureRegionDrawable(textrw3);
        w3 = new ImageButton(textrdw3);
        w3.setSize(col_width*3,row_height*1);
        w3.setPosition(col_width*1,row_height*5);
        w3.setChecked(false);
        w3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(score2>50) {
                    gsm.set(new PlayStateWorld3(gsm));
                }
            }
        });
        w3.getImage().setFillParent(true);
        //w3.debug();
        stage.addActor(w3);
        score3= Save.gd.getHighScores(3);
        if(score2<50) {
            starW3 = new Texture("lock.png");
        }
        else if (score3<50) {
            starW3 = new Texture("star0.png");
        }
        else if (score3<60){
            starW3 = new Texture("star.png");
        }
        else if (score3<75){
            starW3 = new Texture("star2.png");
        }
        else{
            starW3 = new Texture("star3.png");
        }

        textw4 = new Texture(Gdx.files.internal("levels_but/level 4.png"));
        textrw4 = new TextureRegion(textw4);
        textrdw4 = new TextureRegionDrawable(textrw4);
        w4 = new ImageButton(textrdw4);
        w4.setSize(col_width*3,row_height*1);
        w4.setPosition(col_width*6,row_height*5);
        w4.setChecked(false);
        w4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(score3>50) {
                    gsm.set(new PlayStateWorld4(gsm));
                }
            }
        });
        w4.getImage().setFillParent(true);
        //w4.debug();
        stage.addActor(w4);
        score4= Save.gd.getHighScores(4);
        if(score3<50) {
            starW4 = new Texture("lock.png");
        }
        else if (score4<50) {
            starW4 = new Texture("star0.png");
        }
        else if (score4<60){
            starW4 = new Texture("star.png");
        }
        else if (score4<75){
            starW4 = new Texture("star2.png");
        }
        else{
            starW4 = new Texture("star3.png");
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
        //System.out.println(col_width+", X: "+w1.getX()+"  "+w1.getWidth()+", Y: "+w1.getY()+"  "+(w1.getHeight()-starW1.getHeight())/2+", Width: "+starW1.getWidth()+", Height: "+starW1.getHeight());
        sb.end();

        stage.act();
        stage.draw();
        sb.begin();
        sb.draw(starW1,w1.getX()+(w1.getWidth()-starW1.getWidth())/2,w1.getY()+w1.getHeight(),starW1.getWidth(),starW1.getHeight());
        sb.draw(starW2,w2.getX()+(w2.getWidth()-starW2.getWidth())/2,w2.getY()+w2.getHeight(),starW2.getWidth(),starW2.getHeight());
        sb.draw(starW3,w3.getX()+(w3.getWidth()-starW3.getWidth())/2,w3.getY()+w3.getHeight(),starW3.getWidth(),starW3.getHeight());
        sb.draw(starW4,w4.getX()+(w4.getWidth()-starW4.getWidth())/2,w4.getY()+w4.getHeight(),starW4.getWidth(),starW4.getHeight());
        sb.end();
    }


}
