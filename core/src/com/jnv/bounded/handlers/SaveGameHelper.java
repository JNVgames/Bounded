/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.main.Bounded;

import java.util.ArrayList;
import java.util.List;

public class SaveGameHelper {

    public static class JsonLevels {
        List<Boolean> levelsLocked = new ArrayList<Boolean>(40);
        int level;
    }

    public static void saveLockedLevels() {
        JsonLevels jsonLevels = new JsonLevels();
        jsonLevels.levelsLocked = Bounded.lockedLevels;
        jsonLevels.level = LevelState.getLevel();

        Json json = new Json();
        writeFile("game.sav", json.toJson(jsonLevels));
    }

    public static List<Boolean> loadLockedLevels() {
        String save = readFile("game.sav");
        if (!save.isEmpty()) {
            Json json = new Json();
            JsonLevels jsonLevels = json.fromJson(JsonLevels.class, save);

            Bounded.lockedLevels = jsonLevels.levelsLocked;
            LevelState.setLevel(jsonLevels.level);

            return Bounded.lockedLevels;
        } else {
            List<Boolean> levelsList = new ArrayList<Boolean>();

            for(int i = 0; i < 40; i++) {
                if(i == 0) {
                    levelsList.add(false);
                } else {
                    levelsList.add(true);
                }
            }

            Bounded.lockedLevels = levelsList;
            LevelState.setLevel(1);

            return Bounded.lockedLevels;

        }
    }

    public static void writeFile(String filename, String s) {
        FileHandle file = Gdx.files.local(filename);
        file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s), false);
    }

    public static String readFile(String filename) {
        FileHandle file = Gdx.files.local(filename);
        if(file != null && file.exists()) {
            String s = file.readString();
            if(!s.isEmpty()) {
                return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
            }
        }

        return "";
    }
}
