/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.PPM;

public class Wall extends Obstacle {

    private float width, height, angleDeg;

    public Wall(float xPos, float yPos, float width, float height,
                float angleDeg, World world, Bounded bounded) {
        super("wall", new Vector2(xPos, yPos), world, bounded);

        this.width = width;
        this.height = height;
        this.angleDeg = angleDeg;

        center = new Vector2(xPos / PPM, yPos / PPM);

        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;
        def.position.set(center);
        body = world.createBody(def);

        PolygonShape wallShape = new PolygonShape();
        wallShape.setAsBox(width / 2 / PPM, height / 2 / PPM,
                new Vector2(0, 0), (float) Math.toRadians(angleDeg));

        FixtureDef fdef = new FixtureDef();
        fdef.shape = wallShape;
        body.createFixture(fdef);

        wallShape.dispose();
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(objectTexture, center.x - width / 2 / PPM, center.y - height / 2 / PPM, width / 2 / PPM,
                height / 2 / PPM, width / PPM, height / PPM, 1, 1, angleDeg);
        sb.end();
    }
}
