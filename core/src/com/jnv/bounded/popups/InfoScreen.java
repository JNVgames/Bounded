/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.popups;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.scene2d.InputListener;

public class InfoScreen {

	private BoundedAssetManager res;
	private int page = 0;
	private ScrollPane scrollPane;
	private Table table;
	private Label label;
	private Group infoScreen;
	private Stage stage;

	public InfoScreen(final GameStateManager gsm) {
		res = gsm.game().res;
		stage = gsm.game().getStage();

		infoScreen = new Group();
		infoScreen.addActor(new Image(res.getTexture("total black background")));
		table = new Table();
		table.add(new Image(res.getTexture("infopage1")));
		table.layout();
		table.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		scrollPane = new ScrollPane(table);
		scrollPane.setBounds(0, 100, Bounded.WIDTH, Bounded.HEIGHT - 100);
		scrollPane.layout();
		scrollPane.setZIndex(0);
		scrollPane.setScrollingDisabled(true, false);
		infoScreen.addActor(scrollPane);

		Image xButton = new Image(res.getTexture("x"));
		xButton.layout();
		xButton.setBounds(Bounded.WIDTH - 100, Bounded.HEIGHT - 100, 100, 100);
		xButton.addListener(new InputListener(xButton) {
			@Override
			public void doAction() {
				infoScreen.remove();
			}
		});
		infoScreen.addActor(xButton);

		addArrowButtons();

		label = new Label("Page " + (page + 1) + "/3", Bounded.getFont(50));
		label.setBounds((Bounded.WIDTH - label.getPrefWidth()) / 2,
				(100 - label.getPrefHeight()) / 2, label.getPrefWidth(), label.getPrefHeight());
		label.layout();
		infoScreen.addActor(label);
		setInfoPage(0);
	}

	public void dispose() {

	}

	private void addArrowButtons() {
		Image left_arrow = new Image(res.getTexture("left_arrow"));
		left_arrow.layout();
		left_arrow.setBounds(100, 12.5f, 200 * 3 / 4, 100 * 3 / 4);
		left_arrow.addListener(new InputListener(left_arrow) {
			@Override
			public void doAction() {
				prevPage();
			}
		});
		infoScreen.addActor(left_arrow);

		Image right_arrow = new Image(res.getTexture("right_arrow"));
		right_arrow.layout();
		right_arrow.setBounds(Bounded.WIDTH - left_arrow.getWidth() - left_arrow.getX(),
				left_arrow.getY(), left_arrow.getWidth(), left_arrow.getHeight());
		right_arrow.addListener(new InputListener(right_arrow) {
			@Override
			public void doAction() {
				nextPage();
			}
		});
		infoScreen.addActor(right_arrow);
	}

	public void addToStage() {
		stage.addActor(infoScreen);
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
		table.add(new Image(res.getTexture("infopage" + (page + 1))));
		scrollPane.scrollTo(0, scrollPane.getMaxY(), Bounded.WIDTH, Bounded.HEIGHT - 100);
		scrollPane.setVelocityY(0);
		label.setText("Page " + (page + 1) + "/3");
	}
}
