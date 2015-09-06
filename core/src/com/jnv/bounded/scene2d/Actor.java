package com.jnv.bounded.scene2d;

import com.jnv.bounded.utilities.Dimensions;

public class Actor extends com.badlogic.gdx.scenes.scene2d.Actor {

	public Actor() {
		super();
	}

	public void setBounds(Dimensions dimensions) {
		setBounds(dimensions.getX(), dimensions.getY(), dimensions.getWidth(),
				dimensions.getHeight());
	}
}
