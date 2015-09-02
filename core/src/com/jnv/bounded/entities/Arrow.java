/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.ARROW_HEIGHT;
import static com.jnv.bounded.utilities.Constants.ARROW_WIDTH;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Arrow extends Obstacle {

	private float arrowAngleX, arrowAngleY, angle, arrowImpulseScale;

	public Arrow(float xPos, float yPos, float angleDeg, World world, Bounded bounded) {
		super("arrow", new Vector2(xPos, yPos), world, bounded);

		this.angle = angleDeg;
		arrowAngleX = (float) Math.cos(Math.toRadians(angle));
		arrowAngleY = (float) Math.sin(Math.toRadians(angle));
		arrowImpulseScale = 8;

		EdgeShape arrowShape = new EdgeShape();
		arrowShape.set(-(ARROW_WIDTH * arrowAngleX) / 2 / PPM,
				-(ARROW_WIDTH * arrowAngleY) / 2 / PPM,
				(ARROW_WIDTH * arrowAngleX) / 2 / PPM,
				(ARROW_WIDTH * arrowAngleY) / 2 / PPM);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = arrowShape;
		fdef.isSensor = true;

		body.createFixture(fdef).setUserData("arrow");

		arrowShape.dispose();
	}

	public void update(Body ball) {
		ball.setAwake(true);
		ball.setLinearVelocity((arrowAngleX * arrowImpulseScale),
				(arrowAngleY * arrowImpulseScale));
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(objectTexture, center.x - ARROW_WIDTH / 2 / PPM, center.y - ARROW_HEIGHT / 2 / PPM,
				ARROW_WIDTH / 2 / PPM, ARROW_HEIGHT / 2 / PPM, ARROW_WIDTH / PPM,
				ARROW_HEIGHT / PPM, 1, 1, angle);
		sb.end();
	}
}
