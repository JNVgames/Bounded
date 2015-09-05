package com.jnv.bounded.level.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.inputprocessors.InputListener;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.utilities.TutorialDialogCoords;

import java.util.ArrayList;
import java.util.List;

public class FirstTimeGuide {

	private Stage stage;
	private BoundedAssetManager res;
	private List<Image> levelHelperMasks;
	private List<Image> levelHelpers;
	private Image endTexture;
	private static final int[] numTextures = {
			15, 1, 1, 1, 1, 1, 1, 1
	};

	public FirstTimeGuide(final int level, GameStateManager gsm) {
		stage = gsm.game().getStage();
		res = gsm.game().res;
		levelHelperMasks = new ArrayList<Image>();
		levelHelpers = new ArrayList<Image>();
		for (int i = 0; i < numTextures[level - 1]; i++) {
			levelHelperMasks.add(new Image(res.getTexture("helper-level1-mask" + (i + 1))));
			levelHelpers.add(new Image(res.getTexture("helper-level1-" + (i + 1))));
			levelHelpers.get(i).setX(TutorialDialogCoords.level1[i].x);
			levelHelpers.get(i).setY(TutorialDialogCoords.level1[i].y);
			addAppropriateListener(i, level);
		}
		stage.addActor(levelHelperMasks.get(0));
		stage.addActor(levelHelpers.get(0));
	}

	private void addAppropriateListener(final int index, final int level) {
		levelHelpers.get(index).addListener(new InputListener(levelHelpers.get(index)) {
			@Override
			public void doAction() {
				levelHelperMasks.get(index).remove();
				levelHelpers.get(index).remove();
				if (index < numTextures[level - 1] - 1) {
					stage.addActor(levelHelperMasks.get(index + 1));
					stage.addActor(levelHelpers.get(index + 1));
				}
			}
		});
	}
}
