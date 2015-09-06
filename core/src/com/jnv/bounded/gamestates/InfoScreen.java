/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.scene2d.InputListener;

public class InfoScreen extends GameState {

	private int page = 0;
	private ScrollPane scrollPane;
	private Table table;
	private Label label;

	public InfoScreen(final GameStateManager gsm) {
		super(gsm);

		table = new Table();
		table.add(new Image(game.res.getTexture("infopage1")));
		table.layout();
		table.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		scrollPane = new ScrollPane(table);
		scrollPane.setBounds(0, 100, Bounded.WIDTH, Bounded.HEIGHT - 100);
		scrollPane.layout();
		scrollPane.setZIndex(0);
		scrollPane.setScrollingDisabled(true, false);
		stage.addActor(scrollPane);

		Image xButton = new Image(game.res.getTexture("x"));
		xButton.layout();
		xButton.setBounds(Bounded.WIDTH - 100, Bounded.HEIGHT - 100, 100, 100);
		xButton.addListener(new InputListener(xButton) {
			@Override
			public void doAction() {
				gsm.setState(GameStateManager.State.LEVELSELECTION);
			}
		});
		stage.addActor(xButton);

		addArrowButtons();

		label = new Label("Page " + (page + 1) + "/3", Bounded.getFont(50));
		label.setBounds((Bounded.WIDTH - label.getPrefWidth()) / 2,
				(100 - label.getPrefHeight()) / 2, label.getPrefWidth(), label.getPrefHeight());
		label.layout();
		stage.addActor(label);
		setInfoPage(0);
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

	}

	private void addArrowButtons() {
		Image left_arrow = new Image(game.res.getTexture("left_arrow"));
		left_arrow.layout();
		left_arrow.setBounds(100, 12.5f, 200 * 3 / 4, 100 * 3 / 4);
		left_arrow.addListener(new InputListener(left_arrow) {
			@Override
			public void doAction() {
				prevPage();
			}
		});
		stage.addActor(left_arrow);

		Image right_arrow = new Image(game.res.getTexture("right_arrow"));
		right_arrow.layout();
		right_arrow.setBounds(Bounded.WIDTH - left_arrow.getWidth() - left_arrow.getX(),
				left_arrow.getY(), left_arrow.getWidth(), left_arrow.getHeight());
		right_arrow.addListener(new InputListener(right_arrow) {
			@Override
			public void doAction() {
				nextPage();
			}
		});
		stage.addActor(right_arrow);
	}

	// Helpers
	private void nextPage() {
		if (page != 2) {
			page++;
			setInfoPage(page);
		}
	}

	private void prevPage() {
		if (page != 0) {
			page--;
			setInfoPage(page);
		}
	}

	// Setters
	private void setInfoPage(int page) {
		table.clearChildren();
		table.add(new Image(game.res.getTexture("infopage" + (page + 1))));
		scrollPane.scrollTo(0, scrollPane.getMaxY(), Bounded.WIDTH, Bounded.HEIGHT - 100);
		scrollPane.setVelocityY(0);
		label.setText("Page " + (page + 1) + "/3");
	}
}
