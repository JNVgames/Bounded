/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.scene2d.InputListener;

public class MenuState extends GameState {

	public MenuState(final GameStateManager gsm) {
		super(gsm);

		Image image = new Image(game.res.getTexture("start screen"));
		image.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		image.layout();
		stage.addActor(image);
		cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
		stretchViewport.setScreenPosition(0, 0);

		Label label = new Label("Tap anywhere to begin", Bounded.getFont(30));
		label.setBounds((Bounded.WIDTH - label.getPrefWidth()) / 2, Bounded.HEIGHT / 10,
				label.getPrefWidth(), label.getPrefHeight());
		label.layout();
		stage.addActor(label);

		Actor mask = new Actor();
		mask.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		mask.addListener(new InputListener(mask) {
			@Override
			public void doAction() {
				gsm.setState(GameStateManager.State.LEVELSELECTION);
			}
		});
		stage.addActor(mask);
	}

	public void update(float dt) {
		stage.act(dt);
	}

	public void handleInput() {

	}

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.draw();
	}

	public void dispose() {
		Gdx.app.log("MenuState", "dispose()");
	}
}