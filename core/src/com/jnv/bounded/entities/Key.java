/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.KEY_HEIGHT;
import static com.jnv.bounded.utilities.Constants.KEY_WIDTH;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Key extends Obstacle {

	private FixtureDef fdef;
	private boolean isCollected = false;

	public Key(float xPos, float yPos, World world, Bounded bounded) {
		super("key", new Vector2(xPos, yPos), world, bounded);
		this.world = world;

		PolygonShape keyShape = new PolygonShape();
		keyShape.setAsBox(KEY_WIDTH / 2 / PPM, KEY_HEIGHT / 2 / PPM);
		fdef = new FixtureDef();

		fdef.shape = keyShape;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("key");
	}

	public void update(boolean isCollected) {
		this.isCollected = isCollected;
	}

	public void render(SpriteBatch sb) {
		if (!isCollected) {
			sb.begin();
			sb.draw(objectTexture, center.x - KEY_WIDTH / 2 / PPM,
					center.y - KEY_HEIGHT / 2 / PPM, KEY_WIDTH / 2 / PPM,
					KEY_HEIGHT / 2 / PPM, KEY_WIDTH / PPM, KEY_HEIGHT / PPM, 1, 1, 0);
			sb.end();
		}
	}

	// Getters
	public boolean isCollected() {
		return isCollected;
	}

	// Setters
	public void createKey() {
		body = world.createBody(bodyDef);
		body.createFixture(fdef).setUserData("key");
	}
}




