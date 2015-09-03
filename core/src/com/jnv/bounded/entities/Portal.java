/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.PORTAL_HEIGHT;
import static com.jnv.bounded.utilities.Constants.PORTAL_WIDTH;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Portal extends Obstacle {

	private float angle, spawnTime, destroyTime, dt;
	private boolean isDrawn = true;
	private Animation spawnAnimation, destroyAnimation;
	private Mode mode = Mode.SPAWN;

	public Portal(float xPos, float yPos, float angle, World world, Bounded bounded) {
		super("portal", new Vector2(xPos, yPos), world, bounded);

		this.angle = angle;

		PolygonShape portal = new PolygonShape();
		portal.setAsBox(PORTAL_WIDTH / 2 / PPM, PORTAL_HEIGHT / 2 / PPM, new Vector2(0, 0),
				(float) Math.toRadians(angle));

		FixtureDef fdef = new FixtureDef();
		fdef.shape = portal;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData("portal");

		portal.dispose();

		spawnAnimation = new Animation(1 / 30f, bounded.textureLoader.getReversePortalFrames());
		destroyAnimation = new Animation(1 / 30f, bounded.textureLoader.getPortalFrames());
	}

	public void update(float dt) {
		this.dt = dt;
	}

	public void render(SpriteBatch sb) {
		switch (mode) {
			case SPAWN:
				spawn(sb);
				break;
			case NORMAL:
				renderPortal(sb);
				break;
			case DESTROY:
				destroy(sb);
				break;
			default:
				break;
		}
	}

	// Helpers
	private void spawn(SpriteBatch sb) {
		spawnTime += dt;
		sb.begin();
		sb.draw(spawnAnimation.getKeyFrame(spawnTime), center.x - PORTAL_WIDTH / 2 / PPM,
				center.y - PORTAL_HEIGHT / 2 / PPM, PORTAL_WIDTH / 2 / PPM, PORTAL_HEIGHT / 2 / PPM,
				PORTAL_WIDTH / PPM, PORTAL_HEIGHT / PPM, 1, 1, angle);
		sb.end();

		if (spawnAnimation.isAnimationFinished(spawnTime)) {
			spawnTime = 0;
			mode = Mode.NORMAL;
		}
	}

	private void renderPortal(SpriteBatch sb) {
		sb.begin();
		sb.draw(objectTexture, center.x - PORTAL_WIDTH / 2 / PPM, center.y - PORTAL_HEIGHT / 2 / PPM,
				PORTAL_WIDTH / 2 / PPM, PORTAL_HEIGHT / 2 / PPM, PORTAL_WIDTH / PPM,
				PORTAL_HEIGHT / PPM, 1, 1, angle);
		sb.end();
	}

	private void destroy(SpriteBatch sb) {
		if (isDrawn) {
			destroyTime += dt;
			sb.begin();
			sb.draw(destroyAnimation.getKeyFrame(destroyTime), center.x - PORTAL_WIDTH / 2 / PPM,
					center.y - PORTAL_HEIGHT / 2 / PPM, PORTAL_WIDTH / 2 / PPM, PORTAL_HEIGHT / 2 / PPM,
					PORTAL_WIDTH / PPM, PORTAL_HEIGHT / PPM, 1, 1, angle);
			sb.end();
		}

		if (destroyAnimation.isAnimationFinished(destroyTime)) {
			destroyTime = 0;
			mode = Mode.EMPTY;
			isDrawn = false;
		}
	}

	// Setters
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public enum Mode {
		SPAWN,
		NORMAL,
		DESTROY,
		EMPTY
	}

}
