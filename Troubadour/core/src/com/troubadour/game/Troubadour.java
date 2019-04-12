package com.troubadour.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.troubadour.game.states.GameStateManager;
import com.troubadour.game.states.MenuState;

public class Troubadour extends ApplicationAdapter{


	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Troubadour";

	private GameStateManager gsm;
	private SpriteBatch batch;

	private static Music music;
	public static boolean mute =false;


    @Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		//music debut
        music = Gdx.audio.newMusic((Gdx.files.internal("VS_EVIL.mp3")));
        music.setLooping(true);
        music.setVolume(0.18f);
        music.play();
        //music fin
		Gdx.gl.glClearColor(0, 0, 1, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}


    @Override
    public void dispose() {
        super.dispose();
        music.dispose();
    }

    public static void muteMusic(){
		music.pause();
	}
    public static void playMusic(){
        //music debut
        music = Gdx.audio.newMusic((Gdx.files.internal("music.mp3")));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
        //music fin
    }
}
