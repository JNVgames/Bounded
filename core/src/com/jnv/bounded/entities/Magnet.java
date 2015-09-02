/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.MAGNET_SIZE;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Magnet extends Obstacle {

	private Animation animation;
	private Ball ball;
	private float time = 0, radius;

	public Magnet(float xPos, float yPos, float radius, Ball ball, World world, Bounded bounded) {
		super("magnet", new Vector2(xPos, yPos), world, bounded);

		this.ball = ball;
		this.radius = radius;

		FixtureDef fdef = new FixtureDef();
		CircleShape magnet = new CircleShape();

		magnet.setRadius(radius / PPM);
		fdef.shape = magnet;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("magnet");

		magnet.setRadius(MAGNET_SIZE / PPM);
		fdef.shape = magnet;
		body.createFixture(fdef);

		magnet.dispose();

		animation = new Animation(1 / 12f, bounded.textureLoader.getMagnetFrames());
	}

	public void update(float dt) {
		updateTime(dt);
	}

	public void update(boolean isTouchingMagnet) {
		updateBall(isTouchingMagnet);
	}

	public void render(SpriteBatch sb) {
		renderMagnetRings(sb);
		renderMagnet(sb);
	}

	// Helpers
	private Vector2 getLocalVector(Vector2 center, Vector2 localPoint) {
		return new Vector2(localPoint.x - center.x, localPoint.y - center.y);
	}

	private void updateBall(boolean isTouchingMagnet) {
		if (isTouchingMagnet) {
			Vector2 force = getLocalVector(center, ball.getBody().getPosition());
			float distance = force.len();
			force.scl(1.2f / distance);
			ball.getBody().applyForceToCenter(force, true);
		}
	}

	private void updateTime(float dt) {
		time += dt;
	}

	private void renderMagnet(SpriteBatch sb) {
		sb.begin();
		sb.draw(objectTexture, center.x - radius / 5 / PPM, center.y - radius / 5 / PPM,
				radius / 5 / PPM, radius / 5 / PPM,
				radius * 2 / 5 / PPM, radius * 2 / 5 / PPM, 1, 1, 0);
		sb.end();
	}

	private void renderMagnetRings(SpriteBatch sb) {
		sb.begin();
		sb.draw(animation.getKeyFrame(time, true), center.x - radius / PPM,
				center.y - radius / PPM, radius * 2 / PPM,
				radius * 2 / PPM);
		sb.end();
	}

	// Getters
	public Body getBody() {
		return body;
	}

}