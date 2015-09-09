/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.scene2d;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class InputListener extends com.badlogic.gdx.scenes.scene2d.InputListener {

	private Actor actor;
	private boolean hasPressedEffect, isPressed;
	private float width, height;

	public InputListener(Actor actor) {
		this(actor, false);
	}

	public InputListener(Actor actor, boolean hasPressedEffect) {
		this.actor = actor;
		width = actor.getWidth();
		height = actor.getHeight();
		this.hasPressedEffect = hasPressedEffect;
	}

	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if (hasPressedEffect && !isPressed) {
			actor.addAction(Actions.moveBy(5, -5));
			isPressed = true;
		}
		return true;
	}

	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if (x >= 0 && x <= width && y >= 0 && y <= height) doAction();
		else if (hasPressedEffect && isPressed) {
			actor.addAction(Actions.moveBy(-5, 5));
			isPressed = false;
		}
	}

	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		if (hasPressedEffect && isPressed) {
			actor.addAction(Actions.moveBy(-5, 5));
			isPressed = false;
		}
	}

	public void doAction() {
	}
}
