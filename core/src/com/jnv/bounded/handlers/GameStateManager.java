/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */
package com.jnv.bounded.handlers;

import com.badlogic.gdx.Gdx;
import com.jnv.bounded.gamestates.GameState;
import com.jnv.bounded.gamestates.InfoScreen;
import com.jnv.bounded.gamestates.LevelSelection;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.gamestates.MenuState;
import com.jnv.bounded.gamestates.SplashScreen;
import com.jnv.bounded.main.Bounded;

import java.util.Stack;

public class GameStateManager {

    private Bounded game;
    private Stack<GameState> gameStates;
    public State currentState;
    public static GameState pauseState;

    public enum State {
        SPLASH, MENU, LEVELSELECTION, LEVELSTATE, FIRSTINFOPAGE
    }

    public GameStateManager(Bounded game) {
        this.game = game;
        gameStates = new Stack<GameState>();
    }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }
    public void render() {
        gameStates.peek().render();
    }
    public void pause() {
        pauseState = gameStates.peek();
    }
    public void resume() {
        gameStates = new Stack<GameState>();
        gameStates.push(pauseState);
    }

    // Helpers
    public void pushState(State state) {
        gameStates.push(getState(state));
    }
    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }

    // Getters
    public GameState state() { return gameStates.peek(); }
    private GameState getState(State state) {
        currentState = state;
        switch (state) {
            case MENU:
                return new MenuState(this);

            case SPLASH:
                return new SplashScreen(this);

            case LEVELSELECTION:
                return new LevelSelection(this);

            case LEVELSTATE:
                return new LevelState(this);

            case FIRSTINFOPAGE:
                return new InfoScreen(this);

            default:
                Gdx.app.log("GameStateManager", "getState() returns null: ERROR");
                return null;
        }
    }
    public Bounded game() { return game; }

    // Setters
    public void setState(State state) {
        if (gameStates.isEmpty()) pushState(state);
        else {
            popState();
            pushState(state);
        }
    }

}