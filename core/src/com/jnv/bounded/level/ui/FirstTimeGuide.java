package com.jnv.bounded.level.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jnv.bounded.handlers.GameStateManager;

import java.util.List;

public class FirstTimeGuide {

	private List<Image> levelHelpers;
	private Image endTexture;
	private static final int[] numTextures = {
			1, 1, 1, 1, 1, 1, 1, 1
	};

	public FirstTimeGuide(int level, GameStateManager gsm) {
		for (int i = 0; i < numTextures[level - 1]; i++) {
			//levelHelpers.add();
		}
	}
}
