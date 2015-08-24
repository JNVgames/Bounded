/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Constants;

public class Obstacle {

    protected Bounded bounded;
    protected World world;
    protected Body body;
    protected BodyDef bodyDef;
    protected TextureRegion objectTexture;
    protected Vector2 center;

    protected Obstacle(String name, Vector2 center, World world, Bounded bounded) {
        this.bounded = bounded;
        this.world = world;
        this.center = center.scl(1 / Constants.PPM);
        objectTexture = new TextureRegion(bounded.res.getTexture(name));

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(center);
        body = world.createBody(bodyDef);
    }

    // Getters
    public Body getBody() { return body; }
}
