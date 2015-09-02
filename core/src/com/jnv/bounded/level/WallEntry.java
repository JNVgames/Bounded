/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.level;

import com.jnv.bounded.entities.UserWall;

import java.util.ArrayList;
import java.util.List;

public class WallEntry {

    private List<UserWall> walls;
    private String action;

    // Size of points and distance arrays
    private int size;

    public WallEntry(String action) {

        walls = new ArrayList<UserWall>();
        this.action = action;
        size = 0;

    }

    // Getters
    public UserWall getWall(int index) { return walls.get(index); }
    public List<UserWall> getWalls() { return walls; }
    public String getAction() { return action; }
    public int size() { return size; }
    public boolean isEmpty() { return walls.isEmpty(); }

    // Setters
    public void addWall(UserWall wall) {
        walls.add(wall);
        size++;
    }

}
