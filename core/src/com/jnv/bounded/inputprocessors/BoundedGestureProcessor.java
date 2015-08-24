/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.inputprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class BoundedGestureProcessor implements GestureDetector.GestureListener {

    float initialScale = 1;
    float flingVelocityX, flingVelocityY;

    public boolean touchDown(float x, float y, int pointer, int button) {
        initialScale = BoundedInput.zoom;
        return false;
    }
    public boolean tap(float x, float y, int count, int button) {
        BoundedInput.isTapped = true;
        return false;
    }
    public boolean longPress(float x, float y) {
        return false;
    }
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log("GestureDetectorTest", "fling " + velocityX + ", " + velocityY);
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                BoundedInput.rightFling = true;
            } else {
                BoundedInput.leftFling = true;
            }
        } else {
            if (velocityY > 0) {
                BoundedInput.downFling = true;
            } else {
                BoundedInput.upFling = true;
            }
        }

        if (Math.abs(velocityX) > 100 || Math.abs(velocityY) > 100) {
            // start rendering continuously until fling done
            Gdx.graphics.setContinuousRendering(true);
            this.flingVelocityX = velocityX;
            this.flingVelocityY = velocityY;
        }

        return false;
    }
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if(deltaX < 0) {
            BoundedInput.pannedRight = true;
            BoundedInput.deltaX = deltaX;
        }

        if(deltaX > 0) {
            BoundedInput.pannedLeft = true;
            BoundedInput.deltaX = deltaX;
        }

        if(deltaY < 0) {
            BoundedInput.pannedUp = true;
            BoundedInput.deltaY = deltaY;
        }

        if(deltaY > 0) {
            BoundedInput.pannedDown = true;
            BoundedInput.deltaY = deltaY;
        }

        BoundedInput.deltaX = deltaX;
        BoundedInput.deltaY = deltaY;

        return false;
    }
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }
    public boolean zoom(float initialDistance, float distance) {
        return true;
    }
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        BoundedInput.deltaX2 = pointer1.x - initialPointer1.x;
        BoundedInput.deltaY2 = pointer1.y - initialPointer1.y;
        BoundedInput.isPanned = true;
        return true;
    }
    public void flingDecelerate(float time) {
        if (this.flingVelocityX != 0f || this.flingVelocityY != 0f) {
            float newFlingX = Math.max(0,  Math.abs(this.flingVelocityX)-Math.abs(this.flingVelocityX)*5f*time);
            float newFlingY = Math.max(0,  Math.abs(this.flingVelocityY)-Math.abs(this.flingVelocityY)*5f*time);
            if ( this.flingVelocityX < 0 )
                this.flingVelocityX = newFlingX*-1;
            else
                this.flingVelocityX = newFlingX;
            if ( this.flingVelocityY < 0 )
                this.flingVelocityY = newFlingY*-1;
            else
                this.flingVelocityY = newFlingY;

            if ( Math.abs(this.flingVelocityX) < 10 ) this.flingVelocityX = 0f;
            if ( Math.abs(this.flingVelocityY) < 10 ) this.flingVelocityY = 0f;

            if (this.flingVelocityX == 0 && this.flingVelocityY == 0)
                Gdx.graphics.setContinuousRendering(false);
        }
    }

    // Getters
    public float getFlingVelocityX() {
        return flingVelocityX;
    }
    public float getFlingVelocityY() {
        return flingVelocityY;
    }

}