package com.jnv.bounded.level.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.level.utilities.TutorialDialogCoords;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.scene2d.Actor;
import com.jnv.bounded.scene2d.InputListener;
import com.jnv.bounded.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

public class FirstTimeGuide {

	private Stage stage;
	private Group allActors;
	private LevelState levelState;
	private int level;
	private List<Image> levelHelperMasks;
	private List<Image> levelHelpers;
	private List<Actor> triggerMasks;
	private Image skipLevelButton;
	private static final int[] numTextures = {
			TutorialDialogCoords.allCoords[0].length,
			TutorialDialogCoords.allCoords[1].length,
			TutorialDialogCoords.allCoords[2].length,
			TutorialDialogCoords.allCoords[3].length,
			TutorialDialogCoords.allCoords[4].length,
			TutorialDialogCoords.allCoords[5].length,
			0, 0
	};

	public FirstTimeGuide(final int level, GameStateManager gsm, final LevelState levelState) {
		this.levelState = levelState;
		this.level = level;
		stage = gsm.game().getStage();
		if (numTextures[level - 1] > 0) levelState.setDrawable(false);
		BoundedAssetManager res = gsm.game().res;
		allActors = new Group();
		skipLevelButton = new Image(res.getTexture("skip_level"));
		skipLevelButton.setBounds(TutorialDialogCoords.SKIP_LEVEL_DIM);
		skipLevelButton.addListener(new InputListener(skipLevelButton) {
			@Override
			public void doAction() {
				allActors.remove();
				levelState.setDrawable(true);
			}
		});
		levelHelperMasks = new ArrayList<Image>();
		levelHelpers = new ArrayList<Image>();
		triggerMasks = new ArrayList<Actor>();
		for (int i = 0; i < numTextures[level - 1]; i++) {
			levelHelperMasks.add(new Image(res.getTexture("helper-level" + level + "-mask"
					+ (i + 1))));
			levelHelpers.add(new Image(res.getTexture("helper-level" + level + "-" + (i + 1))));
			levelHelpers.get(i).setX(TutorialDialogCoords.allCoords[level - 1][i].x);
			levelHelpers.get(i).setY(TutorialDialogCoords.allCoords[level - 1][i].y);
			triggerMasks.add(new Actor());
			triggerMasks.get(i).setBounds(TutorialDialogCoords.levelTriggers[level - 1][i]);
			addAppropriateListener(level, i);
		}
	}

	public void addToStage() {
		if (numTextures[level - 1] > 0) {
			allActors.addActor(levelHelperMasks.get(0));
			allActors.addActor(levelHelpers.get(0));
			allActors.addActor(triggerMasks.get(0));
			allActors.addActor(skipLevelButton);
		}
		stage.addActor(allActors);
	}

	private void addAppropriateListener(final int level, final int index) {
		if (level == 1) {
			addListener(level, index, new Runnable() {
				@Override
				public void run() {
					switch (index) {
						case 3:
							levelState.setDrawable(true);
							break;
						case 4:
							levelState.setDrawable(false);
							break;
						case 5:
							levelState.getToolbar().showToolbar();
							break;
						case 14:
							levelState.getLevelEventsHandler().setPlayMode();
							skipLevelButton.remove();
							break;
					}
				}
			});
		} else {
			addListener(level, index, null);
		}
	}

	private void addListener(final int level, final int index, final Runnable action) {
		triggerMasks.get(index).addListener(new InputListener(triggerMasks.get(index)) {
			@Override
			public void doAction() {
				levelHelperMasks.get(index).remove();
				levelHelpers.get(index).remove();
				triggerMasks.get(index).remove();
				skipLevelButton.remove();
				if (action != null) action.run();
				if (index < numTextures[level - 1] - 1) {
					allActors.addActor(levelHelperMasks.get(index + 1));
					allActors.addActor(levelHelpers.get(index + 1));
					allActors.addActor(triggerMasks.get(index + 1));
					allActors.addActor(skipLevelButton);
				} else levelState.setDrawable(true);
			}
		});
	}
}
