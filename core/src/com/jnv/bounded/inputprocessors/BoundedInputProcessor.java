/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.inputprocessors;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.jnv.bounded.main.Bounded;

public class BoundedInputProcessor extends InputAdapter {

	private OrthographicCamera camera;
	private Vector3 touch;

	public BoundedInputProcessor() {
		touch = new Vector3();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
	}

	// Helpers
	public boolean touchDown(int x, int y, int pointer, int button) {
		//Convert coordinates
		touch.set(x, y, 0);
		camera.unproject(touch);
		BoundedInput.x = touch.x;
		BoundedInput.y = touch.y;
		BoundedInput.down = true;
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		//Convert coordinates
		touch.set(x, y, 0);
		camera.unproject(touch);
		BoundedInput.x = touch.x;
		BoundedInput.y = touch.y;
		BoundedInput.down = false;
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		//Convert coordinates
		touch.set(x, y, 0);
		camera.unproject(touch);
		BoundedInput.x = touch.x;
		BoundedInput.y = touch.y;
		BoundedInput.down = true;
		return false;
	}
}