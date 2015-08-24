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
import static com.jnv.bounded.utilities.Constants.TELEPORTER_DISTANCE;
import static com.jnv.bounded.utilities.Constants.TELEPORTER_HEIGHT;
import static com.jnv.bounded.utilities.Constants.TELEPORTER_SENSOR_OFFSET;
import static com.jnv.bounded.utilities.Constants.TELEPORTER_WIDTH;

public class Teleporter extends Obstacle {

    private float angle;
    private int targetTeleporter, tpNum;

    public Teleporter(float xPos, float yPos, float angle, int tpNum, int targetTeleporter,
                      World world, Bounded bounded) {
        super("teleporter", new Vector2(xPos, yPos), world, bounded);
        this.tpNum = tpNum;
        this.angle = angle;
        this.targetTeleporter = targetTeleporter;

        PolygonShape teleporter = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        // Create teleporter hitbox
        teleporter.setAsBox(TELEPORTER_WIDTH / 4 / PPM, TELEPORTER_HEIGHT / 2 / PPM,
                new Vector2(-TELEPORTER_WIDTH / 4 * (float) Math.cos(Math.toRadians(angle)) / PPM,
                        -TELEPORTER_WIDTH / 4 * (float) Math.sin(Math.toRadians(angle)) / PPM),
                (float) Math.toRadians(angle));
        fdef.shape = teleporter;
        body.createFixture(fdef);

        // teleporter sensor area
        teleporter.setAsBox(TELEPORTER_WIDTH / 4 / PPM, (TELEPORTER_HEIGHT -
                        TELEPORTER_SENSOR_OFFSET) / 2 / PPM,
                new Vector2(TELEPORTER_WIDTH / 4 * (float) Math.cos(Math.toRadians(angle)) / PPM,
                        TELEPORTER_WIDTH / 4 * (float) Math.sin(Math.toRadians(angle)) / PPM),
                (float) Math.toRadians(angle));
        fdef.shape = teleporter;
        body.createFixture(fdef).setUserData("teleporter");

        teleporter.dispose();
    }

    public void update(Ball ball, Vector2 velocity) {
        System.out.println(velocity);
        if (ball.getBody() != null) {
            float ballSpeed = velocity.len();
            float ballAngularVelocity = ball.getBody().getAngularVelocity();

            ball.getBody().setTransform(center.x + TELEPORTER_DISTANCE *
                            (float) Math.cos(Math.toRadians(angle)) / PPM,
                    center.y + TELEPORTER_DISTANCE *
                            (float) Math.sin(Math.toRadians(angle)) / PPM, 0);

            ball.getBody().setLinearVelocity(ballSpeed * (float) Math.cos(Math.toRadians(angle)),
                    ballSpeed * (float) Math.sin(Math.toRadians(angle)));
            ball.getBody().setAngularVelocity(ballAngularVelocity);

            ball.setMode(Ball.Mode.SPAWN);
        }
    }
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(objectTexture, center.x - TELEPORTER_WIDTH / 2 / PPM,
                center.y - TELEPORTER_HEIGHT / 2 / PPM, TELEPORTER_WIDTH / 2 / PPM,
                TELEPORTER_HEIGHT / 2 / PPM, TELEPORTER_WIDTH / PPM, TELEPORTER_HEIGHT / PPM, 1,
                1, angle);
        sb.end();
    }

    // Getters
    public int getTPNum() { return tpNum; }
    public int getTargetTeleporter() { return targetTeleporter; }
}
