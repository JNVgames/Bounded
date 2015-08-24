/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.handlers.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.ButtonManager;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.handlers.screentouch.BoundedInput;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Constants;

public class ToolbarUI {

    private Bounded game;
    private LevelState levelState;
    private WallsHistoryManager whm;
    private ButtonManager buttonManager;
    private GameStateManager gsm;
    private boolean isToolbarShowing = false;

    private final float TOOLBAR_BUTTON_SIZE = Bounded.WIDTH / 12;
    private final float TOOLBAR_SIZE_X = Bounded.WIDTH;
    private final float TOOLBAR_SIZE_Y = TOOLBAR_BUTTON_SIZE;

    public ToolbarUI(LevelState levelState) {
        this.levelState = levelState;
        gsm = levelState.getGameStateManager();
        game = gsm.game();
        whm = levelState.getWallsHistoryManager();
        buttonManager = levelState.getButtonManager();
    }

    public void handleInput() {
        // If user clicked the options button, pauses screen in background
        if (levelState.getEditState() == LevelState.EditState.OPTIONS) {
            handleOptionsStateTaps();
        } else if (!checkToolbarClick() && (!BoundedInput.isPanned) && (!BoundedInput.zoomed)) {

            if (BoundedInput.isTapped() || BoundedInput.isDragged()
                    || BoundedInput.isReleased()) {

                switch (levelState.getEditState()) {
                    case DRAW: //draw
                        whm.draw();
                        break;

                    case ERASE: //erase
                        whm.erase();
                        break;

                    case OPTIONS:
                        Gdx.app.log("LevelState", "options state");
                        break;

                    default:
                        Gdx.app.log("Editing states", "this shouldn't happen");
                        break; // for debugging. ^should never occur
                }
            }
        }
    }
    public void render(SpriteBatch sb) {
        drawToolbar(sb);

        // Then draw the buttons
        buttonManager.getButtons().get(ButtonManager.EXPAND_TOOLBAR_KEY).draw(sb); //Always shows

        if (isToolbarShowing) {
            if (levelState.getEditState() != LevelState.EditState.PLAY) {
                drawButtons(sb);
            } else {
                drawGreyButtons(sb);
            }
        }
    }

    // Helpers
    private boolean checkToolbarClick() {
        if (BoundedInput.isTapped) {
            // Show toolbar
            if (buttonManager.getButtons().get(ButtonManager.EXPAND_TOOLBAR_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                isToolbarShowing = !isToolbarShowing;
                return true;

                // Set draw state
            } else if (buttonManager.getButtons().get(ButtonManager.DRAW_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y) &&
                    isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                levelState.setEditState(LevelState.EditState.DRAW);
                levelState.setCacheState(LevelState.EditState.DRAW);
                return true;

                // Set erase state
            } else if (buttonManager.getButtons().get(ButtonManager.ERASE_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y) &&
                    isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                levelState.setEditState(LevelState.EditState.ERASE);
                levelState.setCacheState(LevelState.EditState.ERASE);
                return true;

                // Undo
            } else if (buttonManager.getButtons().get(ButtonManager.UNDO_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                whm.undo();
                return true;

                // Redo
            } else if (buttonManager.getButtons().get(ButtonManager.REDO_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                whm.redo();
                return true;

                // Options
            } else if (buttonManager.getButtons().get(ButtonManager.OPTIONS_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing) {
                levelState.setCacheState(levelState.getEditState());
                levelState.setEditState(LevelState.EditState.OPTIONS);
                return true;

                // Set play state
            } else if (buttonManager.getButtons().get(ButtonManager.PLAY_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                levelState.setEditState(LevelState.EditState.PLAY);
                levelState.setCacheState(LevelState.EditState.PLAY);
                isToolbarShowing = false;
                return true;

                // Reset ball
            } else if (buttonManager.getButtons().get(ButtonManager.RESET_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing) {
                levelState.resetBall();
                levelState.setEditState(LevelState.EditState.DRAW);
                levelState.setCacheState(LevelState.EditState.DRAW);
                return true;

                // Clear all lines
            } else if (buttonManager.getButtons().get(ButtonManager.CLEAR_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)
                    && isToolbarShowing && (levelState.getEditState() != LevelState.EditState.PLAY)) {
                whm.clearAll();
                return true;
            }
        }
        return false;
    }
    private void handleOptionsStateTaps() {
        if (BoundedInput.isReleased()) {
            if (buttonManager.getButtons().get(ButtonManager.RETURN_KEY)
                    .checkIfClicked(BoundedInput.x, BoundedInput.y)) {
                gsm.setState(GameStateManager.State.LEVELSELECTION);
            } else {
                levelState.setEditState(levelState.getCacheState());
            }
        }
    }
    private void drawToolbar(SpriteBatch sb) {
        //Draw toolbar first
        if (isToolbarShowing) {
            sb.draw(game.res.getTexture("toolbar_box"), 0, 0,
                    TOOLBAR_SIZE_X / Constants.PPM, TOOLBAR_SIZE_Y / Constants.PPM);
        }
    }
    private void drawButtons(SpriteBatch sb) {
        buttonManager.getButtons().get(ButtonManager.PLAY_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.CLEAR_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.RESET_KEY).draw(sb);

        if (levelState.getEditState() == LevelState.EditState.DRAW) {
            buttonManager.getButtons().get(ButtonManager.PRESSED_DRAW_KEY).draw(sb);
        } else {
            buttonManager.getButtons().get(ButtonManager.DRAW_KEY).draw(sb);
        }

        if (levelState.getEditState() == LevelState.EditState.ERASE) {
            buttonManager.getButtons().get(ButtonManager.PRESSED_ERASE_KEY).draw(sb);
        } else {
            buttonManager.getButtons().get(ButtonManager.ERASE_KEY).draw(sb);
        }

        buttonManager.getButtons().get(ButtonManager.UNDO_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.REDO_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.OPTIONS_KEY).draw(sb);
    }
    private void drawGreyButtons(SpriteBatch sb) {
        buttonManager.getButtons().get(ButtonManager.GRAY_PLAY_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.GRAY_CLEAR_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.RESET_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.GRAY_DRAW_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.GRAY_ERASE_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.GRAY_UNDO_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.GRAY_REDO_KEY).draw(sb);
        buttonManager.getButtons().get(ButtonManager.OPTIONS_KEY).draw(sb);
    }
}
