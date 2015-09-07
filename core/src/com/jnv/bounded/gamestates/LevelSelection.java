/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.scene2d.InputListener;

public class LevelSelection extends GameState {

	private static final int PADDING = Bounded.WIDTH / 25;
	private static final int BUTTON_HEIGHT = Bounded.HEIGHT / 5;
	private static final int BUTTON_WIDTH = Bounded.WIDTH / 5;
	public static int screenNum = 1, maxScreenNum = 5;

	public LevelSelection(GameStateManager gsm) {
		super(gsm);
		loadStage();
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

	// Helpers
	private void loadStage() {

		createImage(game.res.getTexture("level select background"), 0, 0, Bounded.WIDTH, Bounded.HEIGHT, new Runnable() {
			@Override
			public void run() {
			}
		});

		initButtons();

		createImage(game.res.getTexture("info button"), 1160, 600, 100, 100, new Runnable() {
			@Override
			public void run() {
				gsm.setState(GameStateManager.State.FIRSTINFOPAGE);
			}
		});
		createImage(game.res.getTexture("left_arrow"), 50, 50, 100, 50, new Runnable() {
			@Override
			public void run() {
				setPreviousScreen();
			}
		});
		createImage(game.res.getTexture("right_arrow"), 1130, 50, 100, 50, new Runnable() {
			@Override
			public void run() {
				setNextScreen();
			}
		});
		Image levelSelectText = new Image(game.res.getTexture("level select"));
		levelSelectText.layout();
		levelSelectText.setBounds((Bounded.WIDTH - 612) / 2, Bounded.HEIGHT - 170, 612, 144);
		stage.addActor(levelSelectText);
	}

	private void initButtons() {
		for (int i = 1; i <= 4; i++) {
			final int index = i;
			if (Bounded.isLevelUnlocked(i + getScreenNum() * 8)) {
				createImage(game.res.getTexture("level" + (i + getScreenNum() * 8) + "_locked"),
						(i) * PADDING + ((i - 1) * BUTTON_WIDTH), 2 * BUTTON_HEIGHT + 100,
						BUTTON_WIDTH, BUTTON_HEIGHT, new Runnable() {
							@Override
							public void run() {
							}
						});
			} else {
				createImage(game.res.getTexture("level" + (i + getScreenNum() * 8) + "_button"),
						(i) * PADDING + ((i - 1) * BUTTON_WIDTH), 2 * BUTTON_HEIGHT + 100,
						BUTTON_WIDTH, BUTTON_HEIGHT, new Runnable() {
							@Override
							public void run() {
								LevelState.setLevel(index + getScreenNum() * 8);
								LevelState.setMaxDistance();
								gsm.setState(GameStateManager.State.LEVELSTATE);
							}
						});
			}
		}

		for (int i = 5; i <= 8; i++) {
			final int index = i;
			if (Bounded.isLevelUnlocked(i + getScreenNum() * 8)) {
				createImage(game.res.getTexture("level" + (i + getScreenNum() * 8) + "_locked"),
						(i - 4) * PADDING + (i - 5) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH,
						BUTTON_HEIGHT, new Runnable() {
							@Override
							public void run() {
							}
						});
			} else {
				createImage(game.res.getTexture("level" + (i + getScreenNum() * 8) + "_button"),
						(i - 4) * PADDING + (i - 5) * BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH,
						BUTTON_HEIGHT, new Runnable() {
							@Override
							public void run() {
								LevelState.setLevel(index + getScreenNum() * 8);
								LevelState.setMaxDistance();
								gsm.setState(GameStateManager.State.LEVELSTATE);
							}
						});
			}
		}
	}

	// Getters
	private int getScreenNum() {
		return (screenNum - 1);
	}

	// Setters
	private void setNextScreen() {
		if (screenNum != maxScreenNum) {
			screenNum++;
			gsm.setState(GameStateManager.State.LEVELSELECTION);
		}
	}

	private void setPreviousScreen() {
		if (screenNum != 1) {
			screenNum--;
			gsm.setState(GameStateManager.State.LEVELSELECTION);
		}
	}

	private Image createImage(Texture texture, float x, float y, float width, float height,
							  final Runnable action) {
		Image image = new Image(texture);
		image.layout();
		image.setBounds(x, y, width, height);
		image.addListener(new InputListener(image) {
			@Override
			public void doAction() {
				action.run();
			}
		});
		stage.addActor(image);
		return image;
	}
}
