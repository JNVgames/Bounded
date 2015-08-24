/**
 * Copyright 2015, JNV Games, All rights reserved.
 *
 * Class Name: Fan
 * Desciption:
 * - imparts a force perpendicular from the fan to the ball.
 * - The closer the ball is to the fan, the more force
 * - Wind range is 200
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.FAN_FORCE;
import static com.jnv.bounded.utilities.Constants.FAN_HEIGHT;
import static com.jnv.bounded.utilities.Constants.FAN_RANGE;
import static com.jnv.bounded.utilities.Constants.FAN_SHORTENED_HEIGHT;
import static com.jnv.bounded.utilities.Constants.FAN_WIDTH;
import static com.jnv.bounded.utilities.Constants.GRAVITY;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Fan {

    private World world;
    private Ball ball;
    private Body body;
    private Vector2 center;
    private float angleValueX, angleValueY, angle, time = 0;
    private Animation animation;

    public Fan(float xPos, float yPos, float angle, Ball ball, World world, Bounded bounded) {

        this.world = world;
        this.ball = ball;
        this.angle = angle;

        center = new Vector2(xPos / PPM, yPos / PPM);
        angleValueX = (float) Math.cos(Math.toRadians(angle));
        angleValueY = (float) Math.sin(Math.toRadians(angle));
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.fixedRotation = true;
        bdef.position.set(center);
        body = world.createBody(bdef);

        PolygonShape fan = new PolygonShape();
        FixtureDef fdef = new FixtureDef();

        fan.set(getVertices());
        fdef.shape = fan;
        body.createFixture(fdef);

        // Create fan blowing area
        fan.setAsBox(FAN_RANGE / 2 / PPM, FAN_HEIGHT / 2 / PPM,
                new Vector2((FAN_WIDTH + FAN_RANGE) / 2 * angleValueX / PPM,
                        (FAN_WIDTH + FAN_RANGE) / 2 * angleValueY / PPM),
                (float) Math.toRadians(angle));
        fdef.shape = fan;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("fan");

        fan.dispose();

        animation = new Animation(1 / 60f, bounded.textureLoader.getFanFrames());
    }

    public void update(float dt) {
        updateTime(dt);
    }
    public void update(boolean isTouchingFan) {
        updateBall(isTouchingFan);
    }
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getKeyFrame(time, true), center.x - FAN_WIDTH / 2 / PPM,
                center.y - FAN_HEIGHT / 2 / PPM, FAN_WIDTH / 2 / PPM, FAN_HEIGHT / 2 / PPM,
                FAN_WIDTH / PPM, FAN_HEIGHT / PPM, 1, 1, angle);
        sb.end();
    }

    // Helpers
    private void updateTime(float dt) {
        time += dt;
    }
    private void updateBall(boolean isTouchingFan) {
        if (isTouchingFan) {
            Vector2 force = new Vector2(FAN_FORCE * angleValueX, FAN_FORCE * angleValueY);
            world.clearForces();
            world.setGravity(new Vector2(0, GRAVITY));
            ball.getBody().applyForceToCenter(force, true);
        }
    }
    private Vector2[] getVertices() {
        float halfDiagonal = (float) Math.sqrt(Math.pow(FAN_WIDTH, 2)
                + Math.pow(FAN_HEIGHT, 2)) / 2 / PPM;
        float halfShortDiagonal = (float) Math.sqrt(Math.pow(FAN_SHORTENED_HEIGHT / 2, 2)
                + Math.pow(FAN_WIDTH / 2, 2)) / PPM;
        Vector2 vertices[] = new Vector2[4];
        vertices[0] = new Vector2(
                halfDiagonal * (float) Math.cos(Math.toRadians(angle + 108.435)),
                halfDiagonal * (float) Math.sin(Math.toRadians(angle + 108.435))
        );
        vertices[1] = new Vector2(
                halfShortDiagonal * (float) Math.cos(Math.toRadians(angle + 65.556)),
                halfShortDiagonal * (float) Math.sin(Math.toRadians(angle + 65.556))
        );
        vertices[2] = new Vector2(
                halfShortDiagonal * (float) Math.cos(Math.toRadians(angle + 294.444)),
                halfShortDiagonal * (float) Math.sin(Math.toRadians(angle + 294.444))
        );
        vertices[3] = new Vector2(
                halfDiagonal * (float) Math.cos(Math.toRadians(angle + 251.565)),
                halfDiagonal * (float) Math.sin(Math.toRadians(angle + 251.565))
        );
        return vertices;
    }

    // Getters
    public Body getBody() { return body; }

}