package com.jnv.bounded.utilities;

import com.badlogic.gdx.math.Vector2;

public class Dimensions {

	private float x, y, width, height;
	private boolean isCenterX, isCenterY;

	public Dimensions(Vector2 coords, float width, float height) {
		this(coords.x, coords.y, width, height, false, false);
	}

	public Dimensions(float x, float y, float width, float height) {
		this(x, y, width, height, false, false);
	}

	public Dimensions(Vector2 coords, float width, float height,
					  boolean isCenterX, boolean isCenterY) {
		this(coords.x, coords.y, width, height, isCenterX, isCenterY);
	}

	public Dimensions(float x, float y, float width, float height,
					  boolean isCenterX, boolean isCenterY) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.isCenterX = isCenterX;
		this.isCenterY = isCenterY;
	}

	// Getters
	public Vector2 getCoords() {
		return new Vector2(getX(), getY());
	}
	public float getX() {
		if (isCenterX) return x - width / 2;
		else return x;
	}

	public float getY() {
		if (isCenterY) return y - height / 2;
		else return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
