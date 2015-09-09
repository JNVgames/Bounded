/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.level.events;


import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.scene2d.InputListener;
import com.jnv.bounded.scene2d.ui.Image;
import com.jnv.bounded.scene2d.Dimensions;

public class LevelComplete {

	private Group levelCompleteGroup;
	private GameStateManager gsm;
	private Stage stage;

	public LevelComplete(final LevelState levelState) {
		gsm = levelState.getGameStateManager();
		stage = gsm.game().getStage();
		BoundedAssetManager res = gsm.game().res;
		levelCompleteGroup = new Group();

		Image opacityMask = new Image(res.getTexture("opacity_mask"));
		opacityMask.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		levelCompleteGroup.addActor(opacityMask);

		Image levelCompleteWindow = new Image(res.getTexture("level_complete_window"));
		levelCompleteWindow.setBounds(new Dimensions(Bounded.WIDTH / 2, Bounded.HEIGHT / 2, 800,
				400, true, true));
		levelCompleteGroup.addActor(levelCompleteWindow);

		Image levelSelection = new Image(res.getTexture("level_select_button"));
		levelSelection.setBounds(240 + 96, levelCompleteWindow.getY() + 30, Bounded.WIDTH / 5,
				Bounded.HEIGHT / 5);
		levelSelection.addListener(new InputListener(levelSelection) {
			@Override
			public void doAction() {
				LevelState.setNextLevel();
				LevelState.setMaxDistance();
				levelState.getLevelEventsHandler().unlockLevel();
				gsm.setState(GameStateManager.State.LEVELSELECTION);
			}
		});
		levelCompleteGroup.addActor(levelSelection);

		Image nextLevelButton = new Image(res.getTexture("next_level"));
		nextLevelButton.setBounds(688, levelSelection.getY(), Bounded.WIDTH / 5, Bounded.HEIGHT / 5);
		nextLevelButton.addListener(new InputListener(nextLevelButton) {
			@Override
			public void doAction() {
				LevelState.setNextLevel();
				LevelState.setMaxDistance();
				levelState.getLevelEventsHandler().unlockLevel();
				gsm.setState(GameStateManager.State.LEVELSTATE);
			}
		});
		levelCompleteGroup.addActor(nextLevelButton);

		stage.addActor(levelCompleteGroup);
		levelCompleteGroup.setVisible(false);
		levelCompleteGroup.setTouchable(Touchable.disabled);
	}

	public void setVisible() {
		levelCompleteGroup.setVisible(true);
		levelCompleteGroup.setTouchable(Touchable.enabled);
	}
}