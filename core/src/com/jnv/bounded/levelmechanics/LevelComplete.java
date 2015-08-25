/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.levelmechanics;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.SimpleButton;

class LevelComplete {

    private BitmapFont completedLevelFont;
    private CharSequence completedLevelString = "You have completed this level!";
    private SimpleButton nextLevelButton;
    private SimpleButton backToLevelSelectionButton;
    private final String NEXT_LEVEL_BUTTON_NAME = "game_images/next_level256x144.png";
    private final String LEVEL_SELECTION_BUTTON_NAME = "game_images/level_select_button256x144.png";
    private Texture nextLevelTexture;
    private Texture levelSelectionTexture;
    private GameStateManager gsm;

    public LevelComplete(LevelState levelState) {

        gsm = levelState.getGameStateManager();

        completedLevelFont = new BitmapFont();
        completedLevelFont.setColor(Color.RED);
        completedLevelFont.getData().scale(2f);

        levelSelectionTexture = new Texture(LEVEL_SELECTION_BUTTON_NAME);
        backToLevelSelectionButton = new SimpleButton(levelSelectionTexture,
                Bounded.WIDTH / 4, 100,
                Bounded.WIDTH / 5,Bounded.HEIGHT / 5);

        nextLevelTexture = new Texture(NEXT_LEVEL_BUTTON_NAME);
        nextLevelButton = new SimpleButton(nextLevelTexture, 2 * Bounded.WIDTH / 4,
                100, Bounded.WIDTH / 5, Bounded.HEIGHT / 5);
    }

    public void handleInput() {
        if (BoundedInput.isReleased()) {
            if (nextLevelButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                LevelState.setNextLevel();
                LevelState.setMaxDistance();
                gsm.setState(GameStateManager.State.LEVELSTATE);
            } else if (backToLevelSelectionButton.checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                gsm.setState(GameStateManager.State.LEVELSELECTION);
            }
        }
    }

    public void render(SpriteBatch sb) {

        sb.draw(gsm.game().res.getTexture("opacity_mask"), 0, 0,
                Bounded.WIDTH, Bounded.HEIGHT);
        completedLevelFont.draw(sb, completedLevelString,
                Bounded.WIDTH / 4, Bounded.HEIGHT / 2);

        nextLevelButton.draw(sb);
        backToLevelSelectionButton.draw(sb);
    }
}