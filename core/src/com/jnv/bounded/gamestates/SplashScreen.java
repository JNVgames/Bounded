/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Constants;

import java.text.DecimalFormat;
import java.util.Random;

public class SplashScreen extends GameState {

	private TextureRegion icon;
	private float time, angle, gap;
	private DecimalFormat format;
	private Label loadingText;
	private Actor ballIcon;

	public SplashScreen(GameStateManager gsm) {
		super(gsm);

		cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
		game.textureLoader.loadLoadingScreenSprites();
		game.res.finishLoading();

		Image image = new Image(game.res.getTexture("splash"));
		image.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		image.layout();
		stage.addActor(image);

		loadingText = new Label("Loading... 99%", Bounded.getFont(50));
		loadingText.setBounds(
				(Bounded.WIDTH - loadingText.getPrefWidth()) / 2 + Constants.BALL_RADIUS * 2 + gap,
				loadingText.getPrefHeight(), loadingText.getPrefWidth(),
				loadingText.getPrefHeight());
		loadingText.layout();
		stage.addActor(loadingText);

		drawBallIcon();

		gap = 30;

		game.textureLoader.loadAll();

		format = new DecimalFormat("###");
	}

	public void update(float dt) {
		time += dt;
	}

	public void handleInput() {

	}

	public void render() {
		if (game.res.update()) gsm.setState(GameStateManager.State.MENU);

		calculateAngle();

		loadingText.setText("Loading... " + format.format(game.res.getProgress() * 100) + "%");

		stage.draw();
	}

	public void dispose() {

	}

	// Helpers
	private void drawBallIcon() {
		Random random = new Random();
		if (random.nextInt(2) == 0) icon = new TextureRegion(game.res.getTexture("ball"));
		else icon = new TextureRegion(game.res.getTexture("black hole"));

		ballIcon = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.draw(icon, (Bounded.WIDTH - loadingText.getWidth() - 60) / 2, loadingText.getHeight(),
						Constants.BALL_RADIUS, Constants.BALL_RADIUS, Constants.BALL_RADIUS * 2,
						Constants.BALL_RADIUS * 2, 1, 1, angle);
			}
		};
		ballIcon.setBounds((Bounded.WIDTH - loadingText.getWidth()) / 2, loadingText.getHeight(),
				Constants.BALL_RADIUS * 2, Constants.BALL_RADIUS * 2);
		stage.addActor(ballIcon);
	}

	private void calculateAngle() {
		float delay = 1 / 30f;
		if (time >= delay) {
			angle -= 4;
			time -= delay;
			if (angle <= 360) {
				angle += 360;
			}
		}
	}
}