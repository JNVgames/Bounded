/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.jnv.bounded.entities.UserWall;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.jnv.bounded.utilities.Constants.PPM;

public class WallsHistoryManager {

    private LevelState levelState;
    private World world;
    private float maxDistance;

    private List<UserWall> allWalls;
    private Stack<WallEntry> wallEntries;
    private Stack<WallEntry> redoEntries;
    private Stack<Body> bodiesToRemove;
    private QueryCallback drawCallBack, eraseCallback;

    // Temporary variables
    private float x, y;
    private boolean canStore = false, drawable = true;
    private List<Float> tmpDistance;
    private List<Float> tmpPoints;
    private List<List<Float>> tmpArrayOfPoints;
    private WallEntry eraseEntry;
    private Body hitBody = null;

    public WallsHistoryManager(LevelState levelState) {
        this.levelState = levelState;
        this.world = levelState.getWorld();
        this.maxDistance = LevelState.getMaxDistance();

        wallEntries = new Stack<WallEntry>();
        redoEntries = new Stack<WallEntry>();
        bodiesToRemove = new Stack<Body>();

        allWalls = new ArrayList<UserWall>();

        eraseCallback = new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if (fixture.getUserData() != null && fixture.getUserData().equals("userWall")) {
                    hitBody = fixture.getBody();
                    return false;
                } else {
                    // Didn't find the user wall yet, keep searching
                    return true;
                }
            }
        };

        drawCallBack = new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                if (fixture.getUserData() != null && fixture.getUserData().equals("ball")) {
                    drawable = false;
                    return false;
                } else {
                    return true;
                }
            }
        };
    }

    public void update() {
        // Update allWalls
        List<UserWall> tmp = new ArrayList<UserWall>();
        for (UserWall userWall : allWalls) {
            if (!userWall.isDrawn()) { tmp.add(userWall); }
        }
        for (UserWall wall : tmp) { allWalls.remove(wall); }

        // Remove bodies
        if (bodiesToRemove != null) {
            for (Body body : bodiesToRemove) {
                world.destroyBody(body);
            }
            bodiesToRemove = new Stack<Body>();
        }
    }
    public void render(SpriteBatch sb) {
        for (UserWall wall : allWalls) {
            wall.render(sb);
        }
    }

    // Helpers
    private float distTraveled(float x1, float y1, float x2, float y2) {
        return (float) ((Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))) / PPM);
    }
    private float getPartialDistance(List<Float> partialDistance) {
        float totalDist = 0;

        if (!partialDistance.isEmpty()) {
            for (int i = 0; i < partialDistance.size(); i++) {
                totalDist += partialDistance.get(i);
            }
        }
        return totalDist;
    }
    private void setErase(Body body) {
        for (WallEntry wallEntry : wallEntries) {
            if (wallEntry.getAction().equals("draw")) {
                for (UserWall wall : wallEntry.getWalls()) {
                    if (wall.getBody() == body && wall.isDrawn()) {
                        eraseEntry.addWall(wall);
                        wall.setErased();
                    }
                }
            }
        }
    }

    // Getters
    public float getTotalDistance() {
        float totalDistance = 0;

        for (UserWall userWall : allWalls) {
            totalDistance += userWall.getDistance();
        }
        return totalDistance;
    }
    public List<UserWall> getAllWalls() {
        return allWalls;
    }
    public Stack<WallEntry> getWallEntries() {
        return wallEntries;
    }
    public Stack<WallEntry> getRedoEntries() {
        return redoEntries;
    }

    // Setters
    public void draw() {

        if (getTotalDistance() <= maxDistance) {

            if (BoundedInput.isTapped()) {

                canStore = false;

                tmpDistance = new ArrayList<Float>();
                tmpPoints = new ArrayList<Float>();
                tmpArrayOfPoints = new ArrayList<List<Float>>();

                drawable = true;
                world.QueryAABB(drawCallBack, LevelState.scale(BoundedInput.x - 0.001f),
                        LevelState.scale(BoundedInput.y - 0.001f),
                        LevelState.scale(BoundedInput.x + 0.001f),
                        LevelState.scale(BoundedInput.y + 0.001f));

                if (drawable) {
                    x = BoundedInput.x -
                            (Bounded.WIDTH / 2 - levelState.getCam().position.x * PPM);
                    y = BoundedInput.y -
                            (Bounded.HEIGHT / 2 - levelState.getCam().position.y * PPM);
                    tmpPoints.add(x);
                    tmpPoints.add(y);
                }

            }

            if (BoundedInput.isDragged()) {

                drawable = true;
                world.QueryAABB(drawCallBack, LevelState.scale(BoundedInput.x - 0.001f),
                        LevelState.scale(BoundedInput.y - 0.001f),
                        LevelState.scale(BoundedInput.x + 0.001f),
                        LevelState.scale(BoundedInput.y + 0.001f));

                float finalX = BoundedInput.x -
                        (Bounded.WIDTH / 2 - levelState.getCam().position.x * PPM);
                float finalY = BoundedInput.y -
                        (Bounded.HEIGHT / 2 - levelState.getCam().position.y * PPM);

                // If user drawing comes in contact with the ball, stop
                if (!(tmpDistance == null || tmpPoints == null)) {

                    if (drawable) {

                        float drawDistance = distTraveled(x, y, finalX, finalY);

                        if (drawDistance >= Constants.USER_WALL_DIST / PPM
                                &&
                                (getTotalDistance() +
                                        getPartialDistance(tmpDistance) + drawDistance)
                                        <= maxDistance) {

                            tmpDistance.add(drawDistance);

                            tmpPoints.add(finalX);
                            tmpPoints.add(finalY);
                            tmpArrayOfPoints.add(tmpPoints);
                            canStore = true;

                            // Reset tmpPoints
                            tmpPoints = new ArrayList<Float>();
                            tmpPoints.add(finalX);
                            tmpPoints.add(finalY);
                            x = finalX;
                            y = finalY;

                        }
                    } else {
                        tmpPoints.clear();
                        tmpPoints.add(finalX);
                        tmpPoints.add(finalY);
                        x = finalX;
                        y = finalY;
                    }
                }
            }


            if (BoundedInput.isReleased()) {
                if (canStore) {
                    WallEntry wallEntry = new WallEntry("draw");
                    for (int i = 0; i < tmpArrayOfPoints.size(); i++) {
                        UserWall tmp = new UserWall(tmpArrayOfPoints.get(i), world,
                                levelState.getGameStateManager().game());
                        wallEntry.addWall(tmp);
                        allWalls.add(tmp);
                    }
                    wallEntries.push(wallEntry);
                    redoEntries = new Stack<WallEntry>();
                }
            }
        }
    }
    public void erase() {

        if (BoundedInput.isTapped()) {
            canStore = false;
            eraseEntry = new WallEntry("erase");

            x = BoundedInput.x -
                    (Bounded.WIDTH / 2 - levelState.getCam().position.x * PPM);
            y = BoundedInput.y -
                    (Bounded.HEIGHT / 2 - levelState.getCam().position.y * PPM);

            hitBody = null;
            world.QueryAABB(eraseCallback, LevelState.scale(x - 0.5f),
                    LevelState.scale(y - 0.5f), LevelState.scale(x + 0.5f),
                    LevelState.scale(y + 0.5f));

            if (hitBody != null) {
                bodiesToRemove.push(hitBody);
                setErase(hitBody);
                canStore = true;
            }
        }

        if (BoundedInput.isDragged()) {
            x = BoundedInput.x -
                    (Bounded.WIDTH / 2 - levelState.getCam().position.x * PPM);
            y = BoundedInput.y -
                    (Bounded.HEIGHT / 2 - levelState.getCam().position.y * PPM);

            hitBody = null;
            world.QueryAABB(eraseCallback, LevelState.scale(x - 0.5f),
                    LevelState.scale(y - 0.5f), LevelState.scale(x + 0.5f),
                    LevelState.scale(y + 0.5f));

            if (hitBody != null) {
                bodiesToRemove.push(hitBody);
                setErase(hitBody);
                canStore = true;

            }
        }

        if (BoundedInput.isReleased()) {
            if (canStore) {
                if (!eraseEntry.isEmpty()) { wallEntries.push(eraseEntry); }
            }
        }
    }
    public void undo() {

        if (!wallEntries.isEmpty()) {

            // Check if action is draw or erase
            if (wallEntries.peek().getAction().equals("draw")) {
                // If it's draw, remove the walls
                for (int i = 0; i < wallEntries.peek().size(); i++) {
                    wallEntries.peek().getWall(i).setErased();
                    removeBody(wallEntries.peek().getWall(i).getBody());
                }

            } else if (wallEntries.peek().getAction().equals("erase")) {
                // If it's erase, create the walls
                for (int i = 0; i < wallEntries.peek().size(); i++) {
                    allWalls.add(wallEntries.peek().getWall(i));
                    wallEntries.peek().getWall(i).createUserWall();
                }
            } else if (wallEntries.peek().getAction().equals("clear")) {
                // If it's clear, create the walls
                for (int i = 0; i < wallEntries.peek().size(); i++) {
                    allWalls.add(wallEntries.peek().getWall(i));
                    wallEntries.peek().getWall(i).createUserWall();
                }
            }
            redoEntries.push(wallEntries.pop());
        }
    }
    public void redo() {

        if (!redoEntries.isEmpty()) {

            // Check if action is draw or erase
            if (redoEntries.peek().getAction().equals("draw")) {
                // If it's draw, create the walls
                for (int i = 0; i < redoEntries.peek().size(); i++) {
                    allWalls.add(redoEntries.peek().getWall(i));
                    redoEntries.peek().getWall(i).createUserWall();
                }

            } else if (redoEntries.peek().getAction().equals("erase")) {
                // If it's erase, remove the walls
                for (int i = 0; i < redoEntries.peek().size(); i++) {
                    redoEntries.peek().getWall(i).setErased();
                    removeBody(redoEntries.peek().getWall(i).getBody());
                }
            } else if (redoEntries.peek().getAction().equals("clear")) {
                // If it's clear, remove the walls
                for (int i = 0; i < redoEntries.peek().size(); i++) {
                    redoEntries.peek().getWall(i).setErased();
                    removeBody(redoEntries.peek().getWall(i).getBody());
                }
            }
            wallEntries.push(redoEntries.pop());
        }
    }
    public void clearAll() {
        if (!allWalls.isEmpty()) {
            // Store "clear" WallEntry in wallEntries
            WallEntry wallEntry = new WallEntry("clear");

            // Store info into wallEntry and remove all bodies
            for (UserWall userWall : allWalls) {
                wallEntry.addWall(userWall);
                removeBody(userWall.getBody());
                userWall.setErased();
            }

            wallEntries.push(wallEntry);
        }
    }

    public void setRedoEntries(Stack<WallEntry> redoEntries) {
        this.redoEntries = redoEntries;
    }
    public void setWallEntries(Stack<WallEntry> wallEntries) {
        this.wallEntries = wallEntries;
    }
    public void setAllWalls(List<UserWall> allWalls) {
        this.allWalls = allWalls;
    }

    public void removeBody(Body body) { bodiesToRemove.push(body); }
}
