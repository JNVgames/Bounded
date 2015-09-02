/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.PPM;

public class Boundaries {

	private Body pBody;
	private TextureRegion tr;
	private int levelWidth, levelHeight;

	public Boundaries(int levelWidth, int levelHeight, World world, Bounded bounded) {

		this.levelWidth = levelWidth;
		this.levelHeight = levelHeight;

		BodyDef def = new BodyDef();
		def.type = BodyDef.BodyType.StaticBody;
		def.fixedRotation = true;
		pBody = world.createBody(def);

		EdgeShape boundary = new EdgeShape();
		FixtureDef fdef = new FixtureDef();

		//bottom edge
		boundary.set(0, 1 / PPM, levelWidth / PPM, 1 / PPM);
		fdef.shape = boundary;
		fdef.friction = 0.3f;
		pBody.createFixture(fdef);

		//left edge
		boundary.set(1 / PPM, 0, 1 / PPM, levelHeight / PPM);
		fdef.shape = boundary;
		fdef.friction = 0.3f;
		pBody.createFixture(fdef);

		//right edge
		boundary.set(levelWidth / PPM, 0, levelWidth / PPM, levelHeight / PPM);
		fdef.shape = boundary;
		fdef.friction = 0.3f;
		pBody.createFixture(fdef);

		//top edge
		boundary.set(0, (levelHeight - 1) / PPM, levelWidth / PPM, (levelHeight - 1) / PPM);
		fdef.shape = boundary;
		fdef.friction = 0.3f;
		pBody.createFixture(fdef);

		boundary.dispose();

		tr = new TextureRegion(bounded.res.getTexture("boundaries"));

	}

	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(tr, 0, -tr.getRegionHeight() / 2 / PPM, levelWidth / PPM,
				tr.getRegionHeight() / PPM);
		sb.draw(tr, 0, -tr.getRegionHeight() / 2 / PPM, 0, tr.getRegionHeight() / 2 / PPM,
				levelHeight / PPM, tr.getRegionHeight() / PPM, 1, 1, 90);
		sb.draw(tr, 0, (levelHeight - tr.getRegionHeight() / 2) / PPM, levelWidth / PPM,
				tr.getRegionHeight() / PPM);
		sb.draw(tr, levelWidth / PPM, -tr.getRegionHeight() / 2 / PPM, 0, tr.getRegionHeight() / 2 / PPM,
				levelHeight / PPM, tr.getRegionHeight() / PPM, 1, 1, 90);
		sb.end();
	}

	// Getters
	public Body getBody() {
		return pBody;
	}
}
