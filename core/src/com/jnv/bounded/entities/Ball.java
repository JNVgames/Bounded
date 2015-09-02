/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.level.LevelEventsHandler;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.BALL_RADIUS;
import static com.jnv.bounded.utilities.Constants.PPM;

public class Ball {

    private Body body;
    private Vector2 center;
    private TextureRegion tr;
    private boolean isDrawn, destroyAnimationFinished = false;
    private Animation spawnAnimation, destroyAnimation;
    private float spawnTime, destroyTime, resetTime, dt;

    public enum Mode {
        NORMAL,
        SPAWN,
        DESTROY,
        RESET
    }

    private Mode mode = Mode.SPAWN;

    public Ball(float xPos, float yPos, World world, Bounded bounded) {
        isDrawn = true;
        center = new Vector2(xPos / PPM, yPos / PPM);

        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(center);
        bdef.fixedRotation = false;
        body = world.createBody(bdef);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(BALL_RADIUS / PPM);

        // Create fixture
        FixtureDef fdef = new FixtureDef();
        fdef.shape = circleShape;
        fdef.density = 1.0f;
        fdef.restitution = .2f;
        body.createFixture(fdef).setUserData("ball");

        tr = new TextureRegion(bounded.res.getTexture("ball"));

        spawnAnimation = new Animation(1 / 30f, bounded.textureLoader.getReverseBallFrames());
        destroyAnimation = new Animation(1 / 30f, bounded.textureLoader.getBallFrames());

    }

    public void update(float dt) {
        this.dt = dt;
    }
    public void render(SpriteBatch sb) {
        if (isDrawn) {
            switch (mode) {
                case NORMAL:
                    normalAnimation(sb);
                    destroyAnimationFinished = false;
                    break;
                case SPAWN:
                    spawnAnimation(sb);
                    break;
                case DESTROY:
                    destroyAnimation(sb);
                    break;
                case RESET:
                    resetAnimation(sb);
                default:
                    break;
            }
        }
    }

    // Helpers
    private void normalAnimation(SpriteBatch sb) {
        // Do nothing essentially
        sb.begin();
        sb.draw(tr,
                body.getPosition().x - BALL_RADIUS / PPM, body.getPosition().y - BALL_RADIUS / PPM,
                BALL_RADIUS / PPM, BALL_RADIUS / PPM, BALL_RADIUS * 2 / PPM, BALL_RADIUS * 2 / PPM,
                1, 1, (float) Math.toDegrees(body.getAngle()));
        sb.end();
    }
    private void spawnAnimation(SpriteBatch sb) {
        // Spawn animation
        spawnTime += dt;
        sb.begin();
        sb.draw(spawnAnimation.getKeyFrame(spawnTime), body.getPosition().x - BALL_RADIUS / PPM,
                body.getPosition().y - BALL_RADIUS / PPM, BALL_RADIUS / PPM, BALL_RADIUS / PPM,
                BALL_RADIUS * 2 / PPM, BALL_RADIUS * 2 / PPM, 1, 1, 0);
        sb.end();

        if (spawnTime >= spawnAnimation.getAnimationDuration()) {
            spawnTime = 0;
            mode = Mode.NORMAL;
        }
    }
    private void destroyAnimation(SpriteBatch sb) {
        // Destroy animation
        destroyTime += dt;
        sb.begin();
        sb.draw(destroyAnimation.getKeyFrame(destroyTime), LevelEventsHandler.getDestroyedPosition().x - BALL_RADIUS / PPM,
                LevelEventsHandler.getDestroyedPosition().y - BALL_RADIUS / PPM, BALL_RADIUS / PPM, BALL_RADIUS / PPM,
                BALL_RADIUS * 2 / PPM, BALL_RADIUS * 2 / PPM, 1, 1, 0);
        sb.end();

        if (destroyAnimation.isAnimationFinished(destroyTime)) {
            destroyTime = 0;
            destroyAnimationFinished = true;
        }
    }
    private void resetAnimation(SpriteBatch sb) {
        resetTime += dt;
        sb.begin();
        sb.draw(spawnAnimation.getKeyFrame(resetTime), body.getPosition().x - BALL_RADIUS / PPM,
                body.getPosition().y - BALL_RADIUS / PPM, BALL_RADIUS / PPM, BALL_RADIUS / PPM,
                BALL_RADIUS * 2 / PPM, BALL_RADIUS * 2 / PPM, 1, 1, 0);
        sb.draw(destroyAnimation.getKeyFrame(resetTime), LevelEventsHandler.getDestroyedPosition().x - BALL_RADIUS / PPM,
                    LevelEventsHandler.getDestroyedPosition().y - BALL_RADIUS / PPM, BALL_RADIUS / PPM, BALL_RADIUS / PPM,
                    BALL_RADIUS * 2 / PPM, BALL_RADIUS * 2 / PPM, 1, 1, 0);
        sb.end();
        if (destroyAnimation.isAnimationFinished(resetTime)) {
            resetTime = 0;
            mode = Mode.NORMAL;
        }
    }

    // Getters
    public Body getBody() { return body; }
    public Vector2 getOriginalPosition() { return center; }
    public boolean isDestroyAnimationFinished() {
        return destroyAnimationFinished;
    }

    // Setters
    public void setErased() { isDrawn = false; }
    public void setMode(Mode mode) {
        this.mode = mode;
    }
}
