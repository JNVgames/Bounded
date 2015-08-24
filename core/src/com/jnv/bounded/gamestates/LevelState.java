/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.handlers.ButtonManager;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.levelmechanics.BoundedContactListener;
import com.jnv.bounded.levelmechanics.LevelEventsHandler;
import com.jnv.bounded.levelmechanics.Panning;
import com.jnv.bounded.levelmechanics.TiledMapLoader;
import com.jnv.bounded.levelmechanics.ToolbarUI;
import com.jnv.bounded.levelmechanics.WallsHistoryManager;
import com.jnv.bounded.inputprocessors.BoundedGestureProcessor;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.inputprocessors.BoundedInputProcessor;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.LevelDistances;

import java.text.DecimalFormat;

import static com.jnv.bounded.utilities.Constants.GRAVITY;
import static com.jnv.bounded.utilities.Constants.PPM;

public class LevelState extends GameState {

    private World world;
    private TextureRegion background;
    private OrthographicCamera HUDCam;
    private BitmapFont distanceFont;
    private DecimalFormat format;
    private BoundedContactListener cl;
    private WallsHistoryManager whm;
    private LevelEventsHandler leh;
    private TiledMapLoader tml;
    private EditState editState = EditState.DRAW, cache = EditState.DRAW;
    public boolean isReset = false;
    private ToolbarUI toolbar;
    private Panning panning;
    private ButtonManager buttonManager;
    private float time = 0;
    private static int level;
    private static float maxDistance;

    public enum EditState {
        DRAW,
        ERASE,
        PLAY,
        OPTIONS
    }

    public LevelState(GameStateManager gsm) {
        super(gsm);
        loadLevelServices();
        loadInputProcessors();
        loadCamsAndHUD();
        setBackground();
        loadFont();
    }

    public void update(float dt) {
        if (canPlay(dt)) {
            handleInput();
            stepWorld(dt);
            whm.update();
        }
        // Obstacle ollision checks
        leh.update(dt);
    }
    public void handleInput() {
        toolbar.handleInput();
        panning.handleInput();
    }
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundRender();
        camRender();
        tml.render();
        HUDCamRender();
    }
    public void dispose() {

    }

    // Initializers
    private void loadLevelServices() {
        world = new World(new Vector2(0, GRAVITY), true);
        cl = new BoundedContactListener();
        world.setContactListener(cl);

        whm = new WallsHistoryManager(this);
        leh = new LevelEventsHandler(this);
        tml = new TiledMapLoader(this);
        tml.createObjects();

        buttonManager = new ButtonManager(game);
        toolbar = new ToolbarUI(this);
        panning = new Panning(this);
    }
    private void loadInputProcessors() {
        BoundedInputProcessor ip = new BoundedInputProcessor();
        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new GestureDetector(new BoundedGestureProcessor()));
        im.addProcessor(ip);
        Gdx.input.setInputProcessor(im);
    }
    private void loadCamsAndHUD() {
        stretchViewport.setScreenPosition(0, 0);

        HUDCam = new OrthographicCamera();
        format = new DecimalFormat("###");

        HUDCam.setToOrtho(false, Bounded.WIDTH, Bounded.HEIGHT);
        cam.setToOrtho(false, scale(Bounded.WIDTH), scale(Bounded.HEIGHT));
        cam.zoom = 1;
        BoundedInput.zoom = 1;
        cam.position.x = scale(Bounded.WIDTH / 2);
        cam.position.y = scale(Bounded.HEIGHT / 2);
    }
    private void loadFont() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("fonts/HURTMOLD.ttf"));
        distanceFont = generator.generateFont(15);
        distanceFont.setColor(Color.WHITE);
        distanceFont.getData().scale(2f);
    }

    // Helpers
    private void stepWorld(float dt) {
        switch (editState) {
            case DRAW:
            case ERASE:
                world.step(0, 0, 0);
                break;
            case PLAY:
                world.step(dt, 6, 2);
                break;
            default:
                break;
        }
    }
    private void camRender() {
        cam.update();
        sb.setProjectionMatrix(cam.combined);
        leh.render(sb);
        whm.render(sb);
    }
    private void HUDCamRender() {
        HUDCam.update();
        sb.setProjectionMatrix(HUDCam.combined);

        sb.begin();
        distanceFont.draw(sb, "Level " + level + ", " + "Distance: " +
                format.format(whm.getTotalDistance()) + " / " +
                format.format(maxDistance), 20, Bounded.HEIGHT - 20);

        // Update the toolbar on bottom
        toolbar.render(sb);
        if (editState == EditState.OPTIONS) {
            drawOptions();
        }

        leh.levelCompleteRender(sb);
        sb.end();
    }
    private void backgroundRender() {
        sb.setProjectionMatrix(HUDCam.combined);
        sb.begin();
        sb.draw(background, stretchViewport.getScreenX(), stretchViewport.getScreenY(),
                Bounded.WIDTH, Bounded.HEIGHT);
        if (level >= 1 && level <= 8) sb.draw(game.res.getTexture("helper" + level),
                stretchViewport.getScreenX(), stretchViewport.getScreenY(), Bounded.WIDTH, Bounded.HEIGHT);
        sb.end();
    }
    public static float scale(float x) {
        return x / PPM;
    }
    private void drawOptions() {
        sb.draw(game.res.getTexture("opacity_mask"),
                0, 0, Bounded.WIDTH, Bounded.HEIGHT);
        buttonManager.getButtons().get(ButtonManager.RETURN_KEY).draw(sb);
    }

    // Getters
    public LevelEventsHandler getLevelEventsHandler() { return leh; }
    public WallsHistoryManager getWallsHistoryManager() { return whm; }
    public GameStateManager getGameStateManager() { return gsm; }
    public BoundedContactListener getBoundedContactListener() { return cl; }
    public TiledMapLoader getTml() { return tml; }
    public ButtonManager getButtonManager() { return buttonManager; }
    public World getWorld() { return world; }
    public boolean canPlay(float dt) {
        if (!leh.isLevelCompleted()) {
            time += dt;
            return time >= 0.3f;
        } else {
            return false;
        }
    }
    public EditState getEditState() { return editState; }
    public EditState getCacheState() { return cache; }
    public static int getLevel() {
        return level;
    }
    public static float getMaxDistance() { return maxDistance; }

    // Setters
    public void resetBall() {
        if (!isReset) {
            leh.resetBall();
            editState = EditState.DRAW;
            isReset = false;
        }
    }
    public void setEditState(EditState state) { editState = state; }
    public void setCacheState(EditState state) { cache = state; }
    private void setBackground() {
        if (level >= 1 && level <= 8) {
            background = new TextureRegion(game.res.getTexture("red background"));
        } else if (level > 8 && level <= 16) {
            background = new TextureRegion(game.res.getTexture("green background"));
        } else if (level > 16 && level <= 24) {
            background = new TextureRegion(game.res.getTexture("blue background"));
        } else if (level > 24 && level <= 32) {
            background = new TextureRegion(game.res.getTexture("orange background"));
        } else if (level > 32 && level <= 40) {
            background = new TextureRegion(game.res.getTexture("purple background"));
        }
    }
    public static void setNextLevel() {
        level++;
    }
    public static void setLevel(int lv) {
        level = lv;
    }
    public static void setMaxDistance() {
        maxDistance = LevelDistances.getDistance(level);
    }
}