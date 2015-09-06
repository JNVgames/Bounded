package com.jnv.bounded.scene2d.ui;

import com.badlogic.gdx.graphics.Texture;
import com.jnv.bounded.utilities.Dimensions;

public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image {

	public Image(Texture texture) {
		super(texture);
	}

	public void setBounds(Dimensions dim) {
		super.setBounds(dim.getX(), dim.getY(), dim.getWidth(), dim.getHeight());
	}
}
