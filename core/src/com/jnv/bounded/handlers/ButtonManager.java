/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.handlers;

import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.SimpleButton;

import java.util.ArrayList;
import java.util.List;

public class ButtonManager {
    /**********************************************************************************************
     * KEY VALUES FOR EACH BUTTON
     *
     */

    public static final int EXPAND_TOOLBAR_KEY = 0;
    public static final int PLAY_KEY = 1;
    public static final int DRAW_KEY = 2;
    public static final int ERASE_KEY = 3;
    public static final int UNDO_KEY = 4;
    public static final int REDO_KEY = 5;
    public static final int CLEAR_KEY = 6;
    public static final int RESET_KEY = 7;
    public static final int OPTIONS_KEY = 8;
    public static final int RETURN_KEY = 9;
    public static final int PRESSED_DRAW_KEY = 10;
    public static final int PRESSED_ERASE_KEY = 11;
    public static final int GRAY_PLAY_KEY = 12;
    public static final int GRAY_DRAW_KEY = 13;
    public static final int GRAY_ERASE_KEY = 14;
    public static final int GRAY_UNDO_KEY = 15;
    public static final int GRAY_REDO_KEY = 16;
    public static final int GRAY_CLEAR_KEY = 17;

    /**********************************************************************************************/

    private List<SimpleButton> buttons = new ArrayList<SimpleButton>();

    private SimpleButton expandToolbarButton, playButton, drawButton, pressedDrawButton, eraseButton;
    private SimpleButton pressedEraseButton, undoButton, redoButton, clearButton, resetButton, optionsButton;
    private SimpleButton returnToLevelSelectButton, grayPlayButton, grayPencilButton, grayEraserButton;
    private SimpleButton grayUndoButton, grayRedoButton, grayClearButton;

    //Values for toolbar
    private final float TOOLBAR_BUTTON_SIZE = Bounded.WIDTH / 12;
    private final float TOOLBAR_BUTTON_PADDING = TOOLBAR_BUTTON_SIZE * (12 - 9) / 9;

    public ButtonManager(Bounded bounded) {
        createButtons(bounded);
        addValuesToList();
    }

    // Helpers
    private void createButtons(Bounded bounded) {
        createExpandButton(bounded);
        createPlayButton(bounded);
        createDrawingButtons(bounded);
        createUndoButtons(bounded);
        createRedoButtons(bounded);
        createClearButtons(bounded);
        createResetAndOptionsButtons(bounded);
        createReturnToLevelSelectButton(bounded);
    }
    private void createExpandButton(Bounded bounded) {
        expandToolbarButton = new SimpleButton(
                bounded.res.getTexture("toolbar"),
                (8 * TOOLBAR_BUTTON_SIZE + 9 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createPlayButton(Bounded bounded) {
        playButton = new SimpleButton(bounded.res.getTexture(
                "play"),
                TOOLBAR_BUTTON_PADDING, 0, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("play_pressed"));
        grayPlayButton = new SimpleButton(bounded.res.getTexture("play_gray"),
                TOOLBAR_BUTTON_PADDING, 0, TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createDrawingButtons(Bounded bounded) {
        // Draw buttons
        drawButton = new SimpleButton(
                bounded.res.getTexture("pencil"),
                (1 * TOOLBAR_BUTTON_SIZE + 2 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("pencil_pressed"));
        pressedDrawButton = new SimpleButton(
                bounded.res.getTexture("pencil_pressed"),
                (1 * TOOLBAR_BUTTON_SIZE + 2 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
        grayPencilButton = new SimpleButton(
                bounded.res.getTexture("pencil_gray"),
                (1 * TOOLBAR_BUTTON_SIZE + 2 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);

        // Erase buttons
        eraseButton = new SimpleButton(
                bounded.res.getTexture("eraser"),
                (2 * TOOLBAR_BUTTON_SIZE + 3 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("eraser_pressed"));
        pressedEraseButton = new SimpleButton(
                bounded.res.getTexture("eraser_pressed"),
                (2 * TOOLBAR_BUTTON_SIZE + 3 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("eraser_pressed"));
        grayEraserButton = new SimpleButton(
                bounded.res.getTexture("eraser_gray"),
                (2 * TOOLBAR_BUTTON_SIZE + 3 * TOOLBAR_BUTTON_PADDING), 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createUndoButtons(Bounded bounded) {
        undoButton = new SimpleButton(
                bounded.res.getTexture("undo"),
                4 * TOOLBAR_BUTTON_SIZE + 5 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("undo_pressed"));
        grayUndoButton = new SimpleButton(
                bounded.res.getTexture("undo_gray"),
                4 * TOOLBAR_BUTTON_SIZE + 5 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createRedoButtons(Bounded bounded) {
        redoButton = new SimpleButton(
                bounded.res.getTexture("redo"),
                5 * TOOLBAR_BUTTON_SIZE + 6 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("redo_pressed"));
        grayRedoButton = new SimpleButton(
                bounded.res.getTexture("redo_gray"),
                5 * TOOLBAR_BUTTON_SIZE + 6 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createClearButtons(Bounded bounded) {
        clearButton = new SimpleButton(
                bounded.res.getTexture("erase_all"),
                3 * TOOLBAR_BUTTON_SIZE + 4 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("erase_all_pressed"));
        grayClearButton = new SimpleButton(
                bounded.res.getTexture("erase_all_gray"),
                3 * TOOLBAR_BUTTON_SIZE + 4 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
    }
    private void createResetAndOptionsButtons(Bounded bounded) {
        resetButton = new SimpleButton(
                bounded.res.getTexture("reset"),
                6 * TOOLBAR_BUTTON_SIZE + 7 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("reset_pressed"));

        optionsButton = new SimpleButton(
                bounded.res.getTexture("options"),
                7 * TOOLBAR_BUTTON_SIZE + 8 * TOOLBAR_BUTTON_PADDING, 0,
                TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE,
                bounded.res.getTexture("options_pressed"));
    }
    private void createReturnToLevelSelectButton(Bounded bounded) {
        returnToLevelSelectButton = new SimpleButton(
                bounded.res.getTexture("return"),
                ((Bounded.WIDTH / 2) - (bounded.res.getTexture("return").getWidth() / 2)),
                Bounded.HEIGHT * 3 / 5, 612, 144);
    }
    private void addValuesToList() {
        buttons.add(expandToolbarButton);
        buttons.add(playButton);
        buttons.add(drawButton);
        buttons.add(eraseButton);
        buttons.add(undoButton);
        buttons.add(redoButton);
        buttons.add(clearButton);
        buttons.add(resetButton);
        buttons.add(optionsButton);
        buttons.add(returnToLevelSelectButton);
        buttons.add(pressedDrawButton);
        buttons.add(pressedEraseButton);
        buttons.add(grayPlayButton);
        buttons.add(grayClearButton);
        buttons.add(grayPencilButton);
        buttons.add(grayEraserButton);
        buttons.add(grayRedoButton);
        buttons.add(grayUndoButton);
    }

    // Getters
    public List<SimpleButton> getButtons() { return buttons; }


}