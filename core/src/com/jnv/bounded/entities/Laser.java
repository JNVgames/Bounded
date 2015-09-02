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

import static com.jnv.bounded.utilities.Constants.LASER_HEIGHT;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Laser extends Obstacle {

	private float angle, width;

	public Laser(float xPos, float yPos, float width, float angle, World world, Bounded bounded) {
		super("laser", new Vector2(xPos, yPos), world, bounded);
		this.width = width;
		this.angle = angle;
		PolygonShape laser = new PolygonShape();
		laser.setAsBox(width / 2 / PPM, LASER_HEIGHT / 2 / PPM, new Vector2(0, 0),
				(float) Math.toRadians(angle));

		FixtureDef fdef = new FixtureDef();
		fdef.shape = laser;
		fdef.isSensor = true;

		body.createFixture(fdef).setUserData("laser");

		laser.dispose();
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(objectTexture, center.x - width / 2 / PPM, center.y - LASER_HEIGHT / 2 / PPM,
				width / 2 / PPM, LASER_HEIGHT / 2 / PPM, width / PPM, LASER_HEIGHT / PPM, 1, 1,
				angle);
		sb.end();
	}

}
