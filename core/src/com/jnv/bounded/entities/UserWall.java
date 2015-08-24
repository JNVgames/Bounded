/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import java.util.List;

import static com.jnv.bounded.utilities.Constants.PPM;
import static com.jnv.bounded.utilities.Constants.USERWALL_HEIGHT;

public class UserWall {

    private Body pBody;
    private BodyDef def;
    private FixtureDef fdef;
    private float halfDistance, angle;
    private TextureRegion tr;
    private Vector2 center;
    private boolean isDrawn;
    private World world;

    public UserWall(List<Float> points, World world, Bounded bounded) {

        this.world = world;

        float x1, y1, x2, y2;
        x1 = points.get(0);
        y1 = points.get(1);
        x2 = points.get(2);
        y2 = points.get(3);

        center = new Vector2((x1 + x2) / 2, (y1 + y2) / 2);
        angle = (float) Math.atan2(y2 - y1, x2 - x1);

        def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.fixedRotation = true;
        def.angle = angle;
        def.position.set(center.scl(1 / PPM));
        pBody = world.createBody(def);

        halfDistance = (float) Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)) / 2;

        PolygonShape userwall = new PolygonShape();
        userwall.setAsBox(halfDistance / PPM, USERWALL_HEIGHT / PPM);

        fdef = new FixtureDef();
        fdef.shape = userwall;
        fdef.friction = 0.3f;
        pBody.createFixture(fdef).setUserData("userWall");
        isDrawn = true;

        tr = new TextureRegion();
        tr.setRegion(bounded.res.getTexture("wall"));
    }

    public UserWall(UserWall userWall) {
        this.fdef = userWall.fdef;
        this.halfDistance = userWall.halfDistance;
        this.angle = userWall.angle;
        this.tr = userWall.tr;
        this.center = userWall.center;
        this.isDrawn = userWall.isDrawn;
        this.world = userWall.world;
    }

    public void render(SpriteBatch sb) {
        if (isDrawn) {
            sb.begin();
            sb.draw(tr, center.x - halfDistance / PPM, center.y - USERWALL_HEIGHT / PPM,
                    halfDistance / PPM, USERWALL_HEIGHT / PPM, (halfDistance * 2 + 1) / PPM,
                    USERWALL_HEIGHT * 3 / PPM, 1, 1, (float) Math.toDegrees(angle));
            sb.end();
        }
    }

    // Getters
    public Body getBody() { return pBody; }
    public float getDistance() { return halfDistance * 2 / PPM; }
    public boolean isDrawn() { return isDrawn; }

    // Setters
    public void createUserWall() {
        pBody = world.createBody(def);
        pBody.createFixture(fdef).setUserData("userWall");
        isDrawn = true;
    }
    public void setErased() { isDrawn = false; }
}