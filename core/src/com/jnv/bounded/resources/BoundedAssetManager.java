/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.resources;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ObjectMap;

public class BoundedAssetManager extends AssetManager {

    private ObjectMap<String, String> resourceNames;

    public BoundedAssetManager() {
        super();
        resourceNames = new ObjectMap<String, String>();
    }

    public synchronized void clear() {
        super.clear();
        resourceNames.clear();
    }

    public synchronized void unloadTexture(String name) {
        super.unload(resourceNames.get(name));
    }

    public synchronized Texture getTexture(String name) {
        return get(resourceNames.get(name), Texture.class);
    }

    public synchronized void loadTexture(String fileName, String name) {
        resourceNames.put(name, fileName);
        load(fileName, Texture.class);
    }
}
