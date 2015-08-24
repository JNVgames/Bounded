/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Constants;

import java.text.DecimalFormat;
import java.util.Random;

public class SplashScreen extends GameState {
    private Texture splash;
    private TextureRegion icon;
    private BitmapFont font;
    private float loadingFieldWidth, loadingFieldHeight, time, angle, gap;
    private DecimalFormat format;
    private Label loadingFont;

    public SplashScreen(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
        game.textureLoader.loadLoadingScreenSprites();
        game.res.finishLoading();

        splash = game.res.getTexture("splash");
        Random random = new Random();
        if (random.nextInt(2) == 0) icon = new TextureRegion(game.res.getTexture("ball"));
        else icon = new TextureRegion(game.res.getTexture("black hole"));

        loadingFont = new Label("Loading... 99%", Bounded.getHurtmoldFontLabelStyle(50));
        float loadingFontWidth = loadingFont.getPrefWidth();
        font = Bounded.getHurtmoldFontLabelStyle(50).font;
        font.setColor(Color.WHITE);

        loadingFieldHeight = 40;
        gap = 30;
        loadingFieldWidth += loadingFontWidth + Constants.BALL_RADIUS * 2 + gap;

        game.textureLoader.loadAll();

        format = new DecimalFormat("###");
    }

    public void update(float dt) {
        time += dt;
    }
    public void handleInput() {

    }
    public void render() {
        if (game.res.update()) gsm.setState(GameStateManager.State.MENU);

        calculateAngle();

        cam.update();
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(splash, 0, 0, Bounded.WIDTH, Bounded.HEIGHT);
        loadingFont.setText("Loading... " + format.format(game.res.getProgress() * 100 + 1) + "%");

        font.draw(sb, loadingFont.getText(),
                (Bounded.WIDTH - loadingFieldWidth) / 2 + Constants.BALL_RADIUS * 2 + gap,
                loadingFieldHeight * 2);
        sb.draw(icon, (Bounded.WIDTH - loadingFieldWidth) / 2, loadingFieldHeight,
                Constants.BALL_RADIUS, Constants.BALL_RADIUS, Constants.BALL_RADIUS * 2,
                Constants.BALL_RADIUS * 2, 1, 1, angle);
        sb.end();
    }
    public void dispose() {
        game.res.unloadTexture("splash");
    }

    // Helpers
    private void calculateAngle() {
        float delay = 1 / 30f;
        if (time >= delay) {
            angle -= 4;
            time -= delay;
            if (angle <= 360) {
                angle += 360;
            }
        }
    }
}