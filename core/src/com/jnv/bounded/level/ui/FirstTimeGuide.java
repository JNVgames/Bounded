package com.jnv.bounded.level.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.scene2d.Actor;
import com.jnv.bounded.scene2d.InputListener;
import com.jnv.bounded.utilities.TutorialDialogCoords;

import java.util.ArrayList;
import java.util.List;

public class FirstTimeGuide {

	private Stage stage;
	private LevelState levelState;
	private int level;
	private List<Image> levelHelperMasks;
	private List<Image> levelHelpers;
	private List<Actor> triggerMasks;
	private static final int[] numTextures = {
			15, 0, 0, 0, 0, 0, 0, 0
	};

	public FirstTimeGuide(final int level, GameStateManager gsm, LevelState levelState) {
		this.levelState = levelState;
		this.level = level;
		stage = gsm.game().getStage();
		BoundedAssetManager res = gsm.game().res;
		levelHelperMasks = new ArrayList<Image>();
		levelHelpers = new ArrayList<Image>();
		triggerMasks = new ArrayList<Actor>();
		for (int i = 0; i < numTextures[level - 1]; i++) {
			levelHelperMasks.add(new Image(res.getTexture("helper-level1-mask" + (i + 1))));
			levelHelpers.add(new Image(res.getTexture("helper-level1-" + (i + 1))));
			levelHelpers.get(i).setX(TutorialDialogCoords.level1[i].x);
			levelHelpers.get(i).setY(TutorialDialogCoords.level1[i].y);
			triggerMasks.add(new Actor());
			triggerMasks.get(i).setBounds(TutorialDialogCoords.level1Triggers[i]);
			addAppropriateListener(level, i);
		}
	}

	public void addToStage() {
		if (numTextures[level - 1] > 0) {
			stage.addActor(levelHelperMasks.get(0));
			stage.addActor(levelHelpers.get(0));
			stage.addActor(triggerMasks.get(0));
		}
	}

	private void addAppropriateListener(final int level, final int index) {
		if (!addSpecialListener(level, index)) {
			triggerMasks.get(index).addListener(new InputListener(triggerMasks.get(index)) {
				@Override
				public void doAction() {
					levelHelperMasks.get(index).remove();
					levelHelpers.get(index).remove();
					triggerMasks.get(index).remove();
					if (index < numTextures[level - 1] - 1) {
						stage.addActor(levelHelperMasks.get(index + 1));
						stage.addActor(levelHelpers.get(index + 1));
						stage.addActor(triggerMasks.get(index + 1));
					}
				}
			});
		}
	}

	private boolean addSpecialListener(final int level, final int index) {
		if (level == 1 && index == 5) {
			triggerMasks.get(index).addListener(new InputListener(triggerMasks.get(index)) {
				@Override
				public void doAction() {
					levelHelperMasks.get(index).remove();
					levelHelpers.get(index).remove();
					triggerMasks.get(index).remove();
					levelState.getToolbar().showToolbar();
					if (index < numTextures[level - 1] - 1) {
						stage.addActor(levelHelperMasks.get(index + 1));
						stage.addActor(levelHelpers.get(index + 1));
						stage.addActor(triggerMasks.get(index + 1));
					}
				}
			});
			return true;
		}
		if (level == 1 && index == 14) {
			triggerMasks.get(index).addListener(new InputListener(triggerMasks.get(index)) {
				@Override
				public void doAction() {
					levelHelperMasks.get(index).remove();
					levelHelpers.get(index).remove();
					triggerMasks.get(index).remove();
					levelState.getLevelEventsHandler().setPlayMode();
					if (index < numTextures[level - 1] - 1) {
						stage.addActor(levelHelperMasks.get(index + 1));
						stage.addActor(levelHelpers.get(index + 1));
						stage.addActor(triggerMasks.get(index + 1));
					}
				}
			});
			return true;
		}
		return false;
	}
}
