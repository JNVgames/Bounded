/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.inputprocessors.BoundedInputProcessor;
import com.jnv.bounded.main.Bounded;

public class MenuState extends GameState {

    private float tapWidth;
    private BitmapFont tapFont;
    private CharSequence tapStr = "Tap anywhere to begin";
    private Texture backgroundBall;

    public MenuState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
        stretchViewport.setScreenPosition(0, 0);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/HURTMOLD.ttf"));
        tapFont = generator.generateFont(20);
        tapFont.setColor(Color.WHITE);

        //Used to get the width of the string
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(tapFont, tapStr);
        tapWidth = glyphLayout.width;

        backgroundBall = game.res.getTexture("start screen");

        tapFont.getData().scale(1);

        Gdx.input.setInputProcessor(new BoundedInputProcessor());
    }

    public void update(float dt) {
        handleInput();
    }
    public void handleInput() {
        if (BoundedInput.isReleased()) {
            Gdx.app.log("MenuState", "clicked: x = " + BoundedInput.x + " y = " + BoundedInput.y);
            gsm.setState(GameStateManager.State.LEVELSELECTION);
        }
    }
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0); // Set background to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(backgroundBall, 0, 0, 1280, 720);
        tapFont.draw(sb, tapStr, (Bounded.WIDTH / 2) - tapWidth, Bounded.HEIGHT / 10);
        sb.end();
    }
    public void dispose() {
        Gdx.app.log("MenuState", "dispose()");
    }
}