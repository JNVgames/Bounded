/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.handlers.SaveGameHelper;
import com.jnv.bounded.handlers.screentouch.BoundedInput;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.utilities.LevelDistances;
import com.jnv.bounded.utilities.TextureLoader;

import java.util.ArrayList;
import java.util.List;

public class Bounded extends Game {

	private GameStateManager gsm;
	private StretchViewport stretchViewport;

	public static List<Boolean> lockedLevels;

	//The "Game Universe Camera"
	private OrthographicCamera camera;
	private SpriteBatch sb;

    public BoundedAssetManager res;
    public TextureLoader textureLoader;

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;

    private static boolean gamePaused = false;
    private static GameStateManager.State state;

    private static FreeTypeFontGenerator generator;

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
            gsm.setState(state);
            lockedLevels = SaveGameHelper.loadLockedLevels();
            gamePaused = false;
            gsm.resume();
        }
	}
	public void pause() {
        System.out.println("pause");
        gamePaused = true;
        state = gsm.currentState;
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

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HURTMOLD.ttf"));
        res = new BoundedAssetManager();
        textureLoader = new TextureLoader(this);
        LevelDistances.loadDistances();

        // Make all levels locked
        Bounded.lockedLevels = new ArrayList<Boolean>(40);

        for(int i = 0; i < 40; i++) {
            if(i == 0) {
                Bounded.lockedLevels.add(false);
            }
            else {
                Bounded.lockedLevels.add(true);
            }
        }
        Bounded.lockedLevels = SaveGameHelper.loadLockedLevels();
    }

    // Getters
    public SpriteBatch getSpriteBatch() { return sb; }
    public OrthographicCamera getCamera() { return camera; }
    public StretchViewport getViewPort() { return stretchViewport; }
    public static Label.LabelStyle getHurtmoldFontLabelStyle(int fontSize) {
        FreeTypeFontGenerator.FreeTypeFontParameter fontDetails =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontDetails.size = fontSize;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = generator.generateFont(fontDetails);
        return labelStyle;
    }
}
