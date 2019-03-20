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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.troubadour.game.Troubadour;

import java.awt.Font;

public class ChooseWorldState extends State {

    private Texture background;
    private Stage stage;
    private Skin skin;
    private TextButton world1, world2;
    private BitmapFont font;
    int row_height = Gdx.graphics.getHeight() / 12;
    int col_width = Gdx.graphics.getWidth() / 12;

    private FitViewport fitViewport;

    public ChooseWorldState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Troubadour.WIDTH /2, Troubadour.HEIGHT /2);
        background = new Texture("background.png");

        //fitViewport = new FitViewport(Troubadour.WIDTH, Troubadour.HEIGHT);

        //stage = new Stage(fitViewport);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("button/star-soldier/skin/star-soldier-ui.json"));

        world1 = new TextButton("World 1", skin);
        world1.setSize(col_width*6,row_height*2);
        world1.setPosition(col_width*3,Gdx.graphics.getHeight()-row_height*5);
        //world1.setTransform(true);
        //world1.scaleBy(2f);
        world1.getLabel().setFontScale(5,5);
        world1.setChecked(false);
        stage.addActor(world1);

        world2 = new TextButton("World 2", skin);
        world2.setSize(col_width*6,row_height*2);
        world2.setPosition( col_width*3,row_height*3 );
        world2.getLabel().setFontScale(5,5);
        world2.setChecked(false);
        stage.addActor(world2);


    }


    @Override
    public void dispose() {
        background.dispose();

        System.out.println("Menu State Disposed");
    }

    @Override
    public void handleInput() {
        if(world1.isPressed()){
            //world 1
            gsm.push(new PlayState(gsm));
        }
        if(world2.isPressed()){
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
        sb.end();

        stage.act();
        stage.draw();
    }


}
