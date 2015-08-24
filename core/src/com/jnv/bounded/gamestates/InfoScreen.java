/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.handlers.screentouch.BoundedGestureProcessor;
import com.jnv.bounded.handlers.screentouch.BoundedInput;
import com.jnv.bounded.handlers.screentouch.BoundedInputProcessor;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.SimpleButton;

public class InfoScreen extends GameState {

    private Texture page1, page2, page3;
    private SimpleButton exitButton;
    private BoundedGestureProcessor gp;
    private OrthographicCamera Xcam;
    private int page = 0;

    public InfoScreen(GameStateManager gsm) {
        super(gsm);

        cam = new OrthographicCamera();
        Xcam = new OrthographicCamera();
        cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
        Xcam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);

        page1 = game.res.getTexture("infopage1");
        page2 = game.res.getTexture("infopage2");
        page3 = game.res.getTexture("infopage3");

        exitButton = new SimpleButton(game.res.getTexture("x"),
                1180, 620, 100, 100);
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new BoundedInputProcessor());
        gp = new BoundedGestureProcessor();
        GestureDetector gd = new GestureDetector(gp);
        im.addProcessor(gd);
        Gdx.input.setInputProcessor(im);
    }

    public void update(float dt) {
        float newPosY = cam.position.y + gp.getFlingVelocityY() * dt;
        handleInput();
        gp.flingDecelerate(dt);
        if (newPosY < 360 && newPosY > -1800) {
            cam.position.y += gp.getFlingVelocityY() * dt;
        }
        cam.update();
    }
    public void handleInput() {
        if (BoundedInput.pannedDown || BoundedInput.pannedUp) {
            if ((cam.position.y + BoundedInput.deltaY) < 360 &&
                    (cam.position.y + BoundedInput.deltaY) > -1800) {
                cam.position.set(cam.position.x, cam.position.y + BoundedInput.deltaY, 0);
                cam.update();
            }
        }

        if (BoundedInput.leftFling) {
            nextPage();
        }

        if (BoundedInput.rightFling) {
            prevPage();
        }

        if (BoundedInput.isTapped) {
            if (exitButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                goToLevelSelect();
            }
        }

    }
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        switch (page) {
            case 0:
                sb.draw(page1, 0, -2160, 1280, 2880);
                break;
            case 1:
                sb.draw(page2, 0, -2160, 1280, 2880);
                break;
            case 2:
                sb.draw(page3, 0, -720, 1280, 1440);
                break;
            default:
                break;
        }
        sb.end();

        sb.setProjectionMatrix(Xcam.combined);
        sb.begin();
        sb.draw(exitButton.getSkin(), 1180, 620, 100, 100);
        sb.end();
    }
    public void dispose() {

    }

    // Helpers
    private void nextPage() {
        if (page != 2) {
            page++;
            resetCam();
        }
    }
    private void prevPage() {
        if (page != 0) {
            page--;
            resetCam();
        }
    }
    private void resetCam() {
        cam.position.x = Xcam.position.x;
        cam.position.y = Xcam.position.y;
    }
    private void goToLevelSelect() { gsm.setState(GameStateManager.State.LEVELSELECTION); }
}
