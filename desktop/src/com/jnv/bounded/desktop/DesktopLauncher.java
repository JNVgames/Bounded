package com.jnv.bounded.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jnv.bounded.main.Bounded;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bounded";
		config.width = 1280;
		config.height = 720;
		config.addIcon("game_images/Bounded_Icon144x144.png", Files.FileType.Internal);
		new LwjglApplication(new Bounded(), config);
	}
}
