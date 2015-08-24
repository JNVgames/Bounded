/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.main.Bounded;

import java.util.Random;

import static com.jnv.bounded.utilities.Constants.PPM;

public class BlackHole extends Obstacle {

    private Ball ball;
    private Animation animation;
    private float radius;

    private float time = 0, secondTime = 0, angle = (float) new Random().nextInt(360);

    public BlackHole(float xPos, float yPos, float radius,
                     Ball ball, World world, Bounded bounded) {
        super("black hole", new Vector2(xPos, yPos), world, bounded);
        this.ball = ball;
        this.radius = radius;

        FixtureDef fdef = new FixtureDef();
        CircleShape blackhole = new CircleShape();
        blackhole.setRadius(radius / PPM);
        fdef.shape = blackhole;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("blackHole");

        // Create blackHole center
        blackhole.setRadius(15f / PPM);
        fdef.shape = blackhole;
        fdef.isSensor = true;
        body.createFixture(fdef).setUserData("blackHoleCenter");

        blackhole.dispose();

        animation = new Animation(1 / 12f, bounded.textureLoader.getBlackHoleFrames());
    }

    public void update(float dt) {
        updateTime(dt);
    }
    public void update(boolean isTouchingBlackHole) {
        updateBall(isTouchingBlackHole);
    }
    public void render(SpriteBatch sb) {
        blackHoleRingsAnimation(sb);
        blackHoleRotationAnimation(sb);
    }

    // Helpers
    private void blackHoleRotationAnimation(SpriteBatch sb) {
        float delay = 1 / 30f;
        if (time >= delay) {
            angle += 2;
            time -= delay;
            if (angle >= 360) {
                angle -= 360;
            }
        }
        sb.begin();
        sb.draw(objectTexture, center.x - radius / 3 / PPM,
                center.y - radius / 3 / PPM, radius / 3 / PPM,
                radius / 3 / PPM, radius / 3 * 2 / PPM,
                radius / 3 * 2 / PPM, 1, 1, -angle);
        sb.end();
    }
    private void blackHoleRingsAnimation(SpriteBatch sb) {
        sb.begin();
        sb.draw(animation.getKeyFrame(secondTime, true), center.x - radius / PPM,
                center.y - radius / PPM, radius * 2 / PPM, radius * 2 / PPM);
        sb.end();
    }

    private void updateBall(boolean isTouchingBlackHole) {
        if (isTouchingBlackHole) {
            Vector2 force = getLocalVector(center, ball.getBody().getPosition());
            float distance = force.len();
            force.scl(-1.4f / distance);
            world.clearForces();
            ball.getBody().applyForce(force, ball.getBody().getPosition(), true);
        }
    }
    private void updateTime(float dt) {
        time += dt;
        secondTime += dt;
    }

    private Vector2 getLocalVector(Vector2 center, Vector2 localPoint) {
        return new Vector2(localPoint.x - center.x, localPoint.y - center.y);
    }
}