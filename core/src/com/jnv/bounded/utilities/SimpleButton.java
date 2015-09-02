/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SimpleButton {

	public boolean isPressed;
	private Sprite skin;
	private Sprite pressedSkin;
	private float width;
	private float height;

	//PPMFactor defaults to false
	public SimpleButton(Texture texture, float x, float y,
						float width, float height, Texture pressedTexture) {
		skin = new Sprite(texture); // your image
		pressedSkin = new Sprite(pressedTexture);
		skin.setPosition(x, y);
		skin.setSize(width, height);
		pressedSkin.setPosition(x, y);
		pressedSkin.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	public SimpleButton(Texture texture, float x, float y,
						float width, float height) {
		skin = new Sprite(texture); // your image
		pressedSkin = new Sprite(texture);
		skin.setPosition(x, y);
		skin.setSize(width, height);
		pressedSkin.setPosition(x, y);
		pressedSkin.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public void draw(SpriteBatch batch) {
		if (isPressed) {
			pressedSkin.draw(batch);
			isPressed = false;
		} else
			skin.draw(batch);
	}

    /*public void update (SpriteBatch batch, float input_x, float input_y) {
		checkIfClicked(input_x, input_y);
        skin.draw(batch); // draw the button
    }*/

	public boolean checkIfClicked(float ix, float iy) {

		if (ix > skin.getX() && ix < skin.getX() + skin.getWidth()) {
			if ((iy) > skin.getY() &&
					(iy) < skin.getY() + skin.getHeight()) {

				//Add button down functionality
				isPressed = true;
				return true;
			}
		} else {
			isPressed = false;
		}

		return false;
	}

	public Sprite getSkin() {
		return skin;
	}

	public Sprite getPressedSkin() {
		return pressedSkin;
	}
}