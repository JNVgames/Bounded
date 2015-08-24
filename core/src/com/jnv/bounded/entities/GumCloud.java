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

import static com.jnv.bounded.utilities.Constants.PPM;

public class GumCloud extends Obstacle {

    private float width, height;

    public GumCloud(float xPos, float yPos, float width,
                    float height, World world, Bounded bounded) {
        super("gum cloud", new Vector2(xPos, yPos), world, bounded);

        this.width = width;
        this.height = height;

        PolygonShape gumCloud = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        gumCloud.setAsBox(width / 2 / PPM, height / 2 / PPM);
        fdef.shape = gumCloud;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("gumCloud");

        gumCloud.dispose();
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(objectTexture, center.x - width / 2 / PPM, center.y - height / 2 / PPM,
                width / 2 / PPM, height / 2 / PPM, width / PPM, height / PPM, 1, 1, 0);
        sb.end();
    }
}