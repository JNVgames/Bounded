/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.handlers.SaveGameHelper;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.utilities.LevelDistances;
import com.jnv.bounded.utilities.TextureLoader;

import java.util.ArrayList;
import java.util.List;

public class Bounded extends Game {

	public final static int WIDTH = 1280;
	public final static int HEIGHT = 720;
	public static boolean debug = false;
	public static List<Boolean> lockedLevels;
	private static boolean gamePaused = false;
	private static FreeTypeFontGenerator generator;
	public BoundedAssetManager res;
	public TextureLoader textureLoader;
	private GameStateManager gsm;
	private StretchViewport stretchViewport;
	private OrthographicCamera camera;
	private SpriteBatch sb;
	private Stage stage;

	public void create() {
		init();

		gsm = new GameStateManager(this);
		if (!gamePaused) gsm.setState(GameStateManager.State.SPLASH);

		resume();
	}

	public void render() {
		camera.update();
		sb.setProjectionMatrix(camera.combined);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		BoundedInput.update();
	}

	public void dispose() {
		System.out.println("dispose()");
		SaveGameHelper.saveLockedLevels();
		sb.dispose();
		res.dispose();
		gamePaused = false;
	}

	public void resume() {
		System.out.println("resume");
		if (gamePaused) {
			lockedLevels = SaveGameHelper.loadLockedLevels();
			gamePaused = false;
			gsm.resume();
		}
	}

	public void pause() {
		System.out.println("pause");
		gamePaused = true;
		SaveGameHelper.saveLockedLevels();
		gsm.pause();
	}

	public void resize(int width, int height) {
		float aspectRatio = (float) width / (float) height;
		camera = new OrthographicCamera(2f * aspectRatio, 2f);
		stretchViewport.update(width, height);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	// Helpers
	private void init() {
		camera = new OrthographicCamera();
		sb = new SpriteBatch();
		stretchViewport = new StretchViewport(WIDTH, HEIGHT, camera);
		stretchViewport.setScreenSize(WIDTH, HEIGHT);
		stretchViewport.apply();
		stage = new Stage(stretchViewport, sb);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HURTMOLD.ttf"));
		res = new BoundedAssetManager();
		textureLoader = new TextureLoader(this);
		LevelDistances.loadDistances();
		Gdx.input.setInputProcessor(stage);
		stage.setDebugUnderMouse(debug);

		// Make all levels locked
		Bounded.lockedLevels = new ArrayList<Boolean>(40);

		for (int i = 0; i < 40; i++) {
			if (i == 0) {
				Bounded.lockedLevels.add(false);
			} else {
				Bounded.lockedLevels.add(true);
			}
		}
		Bounded.lockedLevels = SaveGameHelper.loadLockedLevels();
	}

	// Getters
	public SpriteBatch getSpriteBatch() {
		return sb;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public StretchViewport getViewPort() {
		return stretchViewport;
	}

	public Stage getStage() {
		return stage;
	}

	public static boolean isLevelUnlocked(int level) {
		return lockedLevels.get(level - 1);
	}

	// Setters
	public static void unlockLevel(int level) {
		lockedLevels.set(level - 1, false);
	}

	// Static convenience methods
	public static Label.LabelStyle getFont(int fontSize) {
		FreeTypeFontGenerator.FreeTypeFontParameter fontDetails =
				new FreeTypeFontGenerator.FreeTypeFontParameter();
		fontDetails.size = fontSize;
		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = generator.generateFont(fontDetails);
		return labelStyle;
	}

}
