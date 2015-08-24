/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;

public abstract class GameState {

    protected GameStateManager gsm;
    protected Bounded game;
    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected StretchViewport stretchViewport;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCamera();
        stretchViewport = game.getViewPort();
    }

    public abstract void update(float dt);
    public abstract void handleInput();
    public abstract void render();
    public abstract void dispose();

    public OrthographicCamera getCam() { return cam; }

}