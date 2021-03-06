package com.troubadour.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.troubadour.game.Troubadour;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Troubadour.WIDTH;
		config.height = Troubadour.HEIGHT;
		config.title = Troubadour.TITLE;
		new LwjglApplication(new Troubadour(), config);
	}
}
