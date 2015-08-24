/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.levelmechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.entities.Arrow;
import com.jnv.bounded.entities.Ball;
import com.jnv.bounded.entities.BlackHole;
import com.jnv.bounded.entities.Boundaries;
import com.jnv.bounded.entities.Fan;
import com.jnv.bounded.entities.GumCloud;
import com.jnv.bounded.entities.Key;
import com.jnv.bounded.entities.Laser;
import com.jnv.bounded.entities.Magnet;
import com.jnv.bounded.entities.Portal;
import com.jnv.bounded.entities.Teleporter;
import com.jnv.bounded.entities.Wall;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.utilities.Constants.ARROW_HEIGHT;
import static com.jnv.bounded.utilities.Constants.ARROW_WIDTH;
import static com.jnv.bounded.utilities.Constants.BALL_RADIUS;
import static com.jnv.bounded.utilities.Constants.FAN_HEIGHT;
import static com.jnv.bounded.utilities.Constants.FAN_WIDTH;
import static com.jnv.bounded.utilities.Constants.KEY_HEIGHT;
import static com.jnv.bounded.utilities.Constants.KEY_WIDTH;
import static com.jnv.bounded.utilities.Constants.LASER_HEIGHT;
import static com.jnv.bounded.utilities.Constants.TELEPORTER_HEIGHT;
import static com.jnv.bounded.utilities.Constants.TELEPORTER_WIDTH;

public class TiledMapLoader {

    // TiledMap Stuff
    private TiledMap tiledMap;
    private int TiledMapWidth;
    private int TiledMapHeight;
    private OrthogonalTiledMapRenderer tmRenderer;

    private Bounded bounded;
    private LevelEventsHandler leh;
    private World world;
    private OrthographicCamera cam;

    public TiledMapLoader(LevelState levelState) {
        // Load Tiled Map
        try {
            tiledMap = new TmxMapLoader().load("levels/level" + LevelState.getLevel() + ".tmx");
        } catch(Exception e) {
            System.out.println("Cannot find file: levels/level" + LevelState.getLevel() + ".tmx");
            Gdx.app.exit();
        }
        TiledMapWidth = tiledMap.getProperties().get("width", Integer.class);
        TiledMapHeight = tiledMap.getProperties().get("height", Integer.class);
        tmRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        bounded = levelState.getGameStateManager().game();
        leh = levelState.getLevelEventsHandler();
        world = levelState.getWorld();
        cam = levelState.getCam();

    }

    public void render() {
        tmRenderer.setView(cam);
        tmRenderer.render();
    }

    public void createObjects() {
        createBoundaries();
        createBall();
        createGumClouds();
        createArrows();
        createFans();
        createWalls();
        createMagnets();
        createBlackHoles();
        createTeleporters();
        createPortals();
        createKey();
        createLaser();
    }
    private void createBall() {
        MapLayer layer = tiledMap.getLayers().get("ball");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float radius = BALL_RADIUS;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - radius * 2;

            x += radius;
            y += radius;
            y = TiledMapHeight - y;

            leh.registerBall(new Ball(x, y, world, bounded));
        }
    }
    private void createBoundaries() {
        leh.registerBoundaries(new Boundaries(TiledMapWidth, TiledMapHeight, world, bounded));
    }
    private void createArrows() {
        MapLayer layer = tiledMap.getLayers().get("arrow");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = ARROW_WIDTH;
            float height = ARROW_HEIGHT;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerArrow(new Arrow(x, y, -angle, world, bounded));
        }
    }
    private void createLaser() {
        MapLayer layer = tiledMap.getLayers().get("laser");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = objects.getProperties().get("width", Float.class);
            float height = LASER_HEIGHT;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerLaser(new Laser(x, y, width, -angle, world, bounded));
        }
    }
    private void createPortals() {
        MapLayer layer = tiledMap.getLayers().get("portal");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = objects.getProperties().get("width", Float.class);
            float height = objects.getProperties().get("height", Float.class);
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerPortal(new Portal(x, y, angle, world, bounded));
        }
    }
    private void createKey() {
        MapLayer layer = tiledMap.getLayers().get("key");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = KEY_WIDTH;
            float height = KEY_HEIGHT;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;

            x += width / 2;
            y = TiledMapHeight - (y + height / 2);

            leh.registerKey(new Key(x, y, world, bounded));
        }
    }
    private void createWalls() {
        MapLayer layer = tiledMap.getLayers().get("wall");
        if (layer == null) return;


        for (MapObject objects : layer.getObjects()) {
            float width = objects.getProperties().get("width", Float.class);
            float height = objects.getProperties().get("height", Float.class);
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerWall(new Wall(x, y, width, height, -1 * angle, world, bounded));
        }
    }
    private void createGumClouds() {
        MapLayer layer = tiledMap.getLayers().get("gumcloud");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = objects.getProperties().get("width", Float.class);
            float height = objects.getProperties().get("height", Float.class);
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;

            x += width / 2;
            y = TiledMapHeight - (y + height / 2);

            leh.registerGumCloud(new GumCloud(x, y, width, height, world, bounded));
        }
    }
    private void createBlackHoles() {
        MapLayer layer = tiledMap.getLayers().get("blackhole");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            leh.registerBlackHole(new BlackHole(
                    objects.getProperties().get("x", Float.class)
                            + objects.getProperties().get("width", Float.class) / 2,
                    objects.getProperties().get("y", Float.class)
                            + objects.getProperties().get("width", Float.class) / 2,
                    objects.getProperties().get("width", Float.class) / 2,
                    leh.getBall(), world, bounded
            ));
        }
    }
    private void createMagnets() {
        MapLayer layer = tiledMap.getLayers().get("magnet");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            leh.registerMagnet(new Magnet(
                    objects.getProperties().get("x", Float.class)
                            + objects.getProperties().get("width", Float.class) / 2,
                    objects.getProperties().get("y", Float.class)
                            + objects.getProperties().get("width", Float.class) / 2,
                    objects.getProperties().get("width", Float.class) / 2,
                    leh.getBall(), world, bounded
            ));
        }
    }
    private void createTeleporters() {
        MapLayer layer = tiledMap.getLayers().get("teleporter");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = TELEPORTER_WIDTH;
            float height = TELEPORTER_HEIGHT;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);
            int tpNum = Integer.parseInt(objects.getProperties().get("tpNum", String.class));
            int target = Integer.parseInt(objects.getProperties().get("target", String.class));

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerTeleporters(new Teleporter(x, y, -angle, tpNum, target, world, bounded));
        }
    }
    private void createFans() {
        MapLayer layer = tiledMap.getLayers().get("fan");
        if (layer == null) return;

        for (MapObject objects : layer.getObjects()) {
            float width = FAN_WIDTH;
            float height = FAN_HEIGHT;
            float x = objects.getProperties().get("x", Float.class);
            float y = TiledMapHeight - objects.getProperties().get("y", Float.class) - height;
            float angle = getAngle(objects);

            x = (float) (x + width / 2 * Math.cos(Math.toRadians(angle)) +
                    height / 2 * Math.cos(Math.toRadians(angle + 90)));
            y = (float) (y + width / 2 * Math.sin(Math.toRadians(angle)) +
                    height / 2 * Math.sin(Math.toRadians(angle + 90)));
            y = TiledMapHeight - y;

            leh.registerFan(new Fan(x, y, -angle, leh.getBall(), world, bounded));
        }
    }

    private float getAngle(MapObject object) {
        float angle = 0;
        if (object.getProperties().get("rotation") != null) {
            angle = object.getProperties().get("rotation", Float.class);
        }
        while(angle < 0) angle += 360;
        while(angle >= 360) angle -= 360;
        return angle;
    }

    public float getLevelWidth() { return TiledMapWidth; }
    public float getLevelHeight() { return TiledMapHeight; }
}
