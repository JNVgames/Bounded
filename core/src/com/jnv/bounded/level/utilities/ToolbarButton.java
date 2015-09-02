package com.jnv.bounded.level.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.jnv.bounded.inputprocessors.InputListener;
import com.jnv.bounded.main.Bounded;

public class ToolbarButton extends Actor {

	private Mode mode = Mode.NORMAL;
	private Texture normal, pressed, gray;
	private float x, y, width, height;

	public ToolbarButton(Texture normal, Texture pressed, Texture gray, float x, float y,
						 float width, float height) {
		this.normal = normal;
		this.pressed = pressed;
		this.gray = gray;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		addListener(new InputListener(this) {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (mode != Mode.GRAY) mode = Mode.PRESSED;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (mode != Mode.GRAY) mode = Mode.NORMAL;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (mode != Mode.GRAY) mode = Mode.NORMAL;
			}
		});
	}

	public ToolbarButton(Texture normal, Texture pressed, float x, float y, float width,
						 float height) {
		this(normal, pressed, null, x, y, width, height);
	}

	public void setValues(Texture normal, Texture pressed, Texture gray, float x, float y,
						  float width, float height) {
		this.normal = normal;
		this.pressed = pressed;
		this.gray = gray;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		addListener(new InputListener(this) {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				if (mode != Mode.GRAY) mode = Mode.PRESSED;
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if (mode != Mode.GRAY) mode = Mode.NORMAL;
			}

			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				if (mode != Mode.GRAY) mode = Mode.NORMAL;
			}
		});
	}

	public void setValuesNoGray(Texture normal, Texture pressed, float x, float y, float width,
								float height) {
		setValues(normal, pressed, null, x, y, width, height);
	}

	public void draw(Batch batch, float parentAlpha) {
		switch (mode) {
			case NORMAL:
				batch.draw(normal, x, y, width, height);
				break;
			case PRESSED:
				batch.draw(pressed, x, y, width, height);
				break;
			case GRAY:
				if (gray != null) batch.draw(gray, x, y, width, height);
				break;
			default:
				if (Bounded.debug) throw new AssertionError("Toolbar button: No exisitng mode");
				break;
		}
	}

	public void setGray() {
		mode = Mode.GRAY;
	}

	public void setNormal() {
		mode = Mode.NORMAL;
	}

	public void setPressed() {
		mode = Mode.PRESSED;
	}

	private enum Mode {
		NORMAL,
		PRESSED,
		GRAY
	}
}
