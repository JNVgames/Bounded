/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.inputprocessors.BoundedGestureProcessor;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.inputprocessors.BoundedInputProcessor;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class LevelSelection extends GameState {

    private List<SimpleButton> unlockedButtonsArray;
    private List<SimpleButton> lockedButtonsArray;

    private static final int PADDING = Bounded.WIDTH / 25;
    private static final int BUTTON_WIDTH = Bounded.WIDTH / 5;
    private static final int BUTTON_HEIGHT = Bounded.HEIGHT / 5;

    private Texture levelSelectionImageTexture;
    private Texture background;

    private SimpleButton infoButton;
    private SimpleButton nextButton;
    private SimpleButton prevButton;

    public static int screenNum = 1, maxScreenNum = 5;

    public LevelSelection(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
        stretchViewport.setScreenSize(0, 0);

        initializeButtonArrays();
        levelSelectionImageTexture = game.res.getTexture("level select");
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new GestureDetector(new BoundedGestureProcessor()));
        im.addProcessor(new BoundedInputProcessor());
        Gdx.input.setInputProcessor(im);
    }

    public void update(float dt) {
        handleInput();
    }
    public void handleInput() {
        if(BoundedInput.isTapped) {

            //Gdx.app.log("LevelSelection", "viewport camera x = " + )
            for (int i = 0; i < 8; i++) {
                if (unlockedButtonsArray.get(i).checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                    if (!Bounded.lockedLevels.get(i + getScreenNum() * 8)) {
                        LevelState.setLevel(i + 1 + getScreenNum() * 8);
                        LevelState.setMaxDistance();
                        gsm.setState(GameStateManager.State.LEVELSTATE);
                    }
                }
            }

            if (infoButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                goToFirstInfoPage();
            }

            else if (prevButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                setNextScreen();
            }

            else if (nextButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                setPreviousScreen();
            }

        }

        if(BoundedInput.leftFling) {
            setNextScreen();
        }

        if(BoundedInput.rightFling) {
            setPreviousScreen();
        }
    }
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0, 1280, 720);
        sb.draw(levelSelectionImageTexture, (Bounded.WIDTH / 2) - 306, Bounded.HEIGHT - 200);
        drawButtons();
        sb.end();
    }
    public void dispose() {

    }

    // Helpers
    private void goToFirstInfoPage() { gsm.setState(GameStateManager.State.FIRSTINFOPAGE); }
    private void initializeButtonArrays() {

        //Instantiate ArrayLists and add button names to the arraylists
        //For locked and unlocked buttons
        List<Texture> unlockedTexturesArray = new ArrayList<Texture>();
        List<Texture> lockedTexturesArray = new ArrayList<Texture>();

        initTextureArrays(unlockedTexturesArray, lockedTexturesArray);
        setButtons(unlockedTexturesArray, lockedTexturesArray);

        Texture infoButtonTexture = game.res.getTexture("info button");
        infoButton = new SimpleButton(infoButtonTexture, 1160, 600, 100, 100);

        background = game.res.getTexture("level select background");

        nextButton = new SimpleButton(game.res.getTexture("left_arrow"), 50, 50, 100, 50);
        prevButton = new SimpleButton(game.res.getTexture("right_arrow"), 1130, 50, 100, 50);
    }
    private void initTextureArrays(List<Texture> unlockedTexturesArray,
                                   List<Texture> lockedTexturesArray) {
        unlockedButtonsArray = new ArrayList<SimpleButton>();
        for (int i = 1; i <= 8; i++) {
            unlockedTexturesArray.add(game.res.getTexture("level" + (i + getScreenNum() * 8)
                    + "_button"));
        }

        lockedButtonsArray = new ArrayList<SimpleButton>();
        for (int i = 1; i <= 8; i++) {
            lockedTexturesArray.add(game.res.getTexture("level" + (i + getScreenNum() * 8)
                    + "_locked"));
        }
    }
    private void setButtons(List<Texture> unlockedTexturesArray,
                            List<Texture> lockedTexturesArray) {
        // Add locked and unlocked buttons and textures to the ArrayLists for levels 1-4
        for(int i = 0; i < 4; i++) {
            SimpleButton unlockedLevelButton = new SimpleButton(unlockedTexturesArray.get(i),
                    (i + 1) * PADDING + (i * BUTTON_WIDTH),
                    2 * BUTTON_HEIGHT + 100,
                    BUTTON_WIDTH, BUTTON_HEIGHT);

            SimpleButton lockedLevelButton = new SimpleButton(lockedTexturesArray.get(i),
                    (i + 1) * PADDING + (i * BUTTON_WIDTH),
                    2 * BUTTON_HEIGHT + 100,
                    BUTTON_WIDTH, BUTTON_HEIGHT);

            unlockedButtonsArray.add(i, unlockedLevelButton);
            lockedButtonsArray.add(i, lockedLevelButton);
        }

        // Add locked and unlocked buttons and textures to the ArrayLists for levels 5-8
        for(int i = 4; i < 8; i++) {
            SimpleButton unlockedLevelButton = new SimpleButton(unlockedTexturesArray.get(i),
                    (i - 3) * PADDING + (i - 4) * BUTTON_WIDTH,
                    BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

            SimpleButton lockedLevelButton = new SimpleButton(lockedTexturesArray.get(i),
                    (i - 3) * PADDING + (i - 4) * BUTTON_WIDTH,
                    BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);

            unlockedButtonsArray.add(i, unlockedLevelButton);
            lockedButtonsArray.add(i, lockedLevelButton);
        }
    }
    private void drawButtons() {

        //Draw the red locked word over the middle of the buttons if the level is locked

        for(int i = 0; i < 8; i++) {
            if(Bounded.lockedLevels.get(i + getScreenNum() * 8)) {
                lockedButtonsArray.get(i).draw(sb);
            }

            else {
                unlockedButtonsArray.get(i).draw(sb);
            }

        }

        infoButton.draw(sb);

        //Draw arrows
        nextButton.draw(sb);
        prevButton.draw(sb);
    }

    // Getters
    private int getScreenNum() { return (screenNum - 1); }

    // Setters
    private void setNextScreen() {
        if (screenNum != maxScreenNum) {
            screenNum++;
            gsm.setState(GameStateManager.State.LEVELSELECTION);
        }
    }
    private void setPreviousScreen() {
        if (screenNum != 1) {
            screenNum--;
            gsm.setState(GameStateManager.State.LEVELSELECTION);
        }
    }
}
