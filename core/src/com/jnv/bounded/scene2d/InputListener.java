/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class InputListener extends com.badlogic.gdx.scenes.scene2d.InputListener {

	private float width, height;

	public InputListener(Actor actor) {
		width = actor.getWidth();
		height = actor.getHeight();
	}

	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		return true;
	}

	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (x >= 0 && x <= width && y >= 0 && y <= height) doAction();
	}

	public void doAction() {
	}
}
