package com.jnv.bounded.utilities;

import com.badlogic.gdx.math.Vector2;

public class Dimensions {

	private float x, y, width, height;

	public Dimensions(Vector2 coords, float width, float height) {
		this(coords.x, coords.y, width, height);
	}

	public Dimensions(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	// Getters
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
