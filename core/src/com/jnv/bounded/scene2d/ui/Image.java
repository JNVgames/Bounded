package com.jnv.bounded.scene2d.ui;

import com.jnv.bounded.utilities.Dimensions;

public class Image extends com.badlogic.gdx.scenes.scene2d.ui.Image {

	public void setBounds(Dimensions dim) {
		setBounds(dim.getX(), dim.getY(), dim.getWidth(), dim.getHeight());
	}
}
