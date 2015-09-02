/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.utilities;

import java.util.ArrayList;
import java.util.List;

public class LevelDistances {

	public static List<Float> distances = new ArrayList<Float>();

	public static void loadDistances() {
		distances.add(25f); // Level 1
		distances.add(20f); // Level 2
		distances.add(20f); // Level 3
		distances.add(15f); // Level 4
		distances.add(10f); // Level 5
		distances.add(10f); // Level 6
		distances.add(20f); // Level 7
		distances.add(30f); // Level 8
		distances.add(15f); // Level 9
		distances.add(20f); // Level 10
		distances.add(20f); // Level 11
		distances.add(15f); // Level 12
		distances.add(25f); // Level 13
		distances.add(20f); // Level 14
		distances.add(15f); // Level 15
		distances.add(30f); // Level 16
		distances.add(20f); // Level 17
		distances.add(10f); // Level 18
		distances.add(30f); // Level 19
		distances.add(25f); // Level 20
		distances.add(30f); // Level 21
		distances.add(20f); // Level 22
		distances.add(30f); // Level 23
		distances.add(25f); // Level 24
		distances.add(20f); // Level 25
		distances.add(25f); // Level 26
		distances.add(25f); // Level 27
		distances.add(25f); // Level 28
		distances.add(15f); // Level 29
		distances.add(25f); // Level 30
		distances.add(15f); // Level 31
		distances.add(15f); // Level 32
		distances.add(20f); // Level 33
		distances.add(20f); // Level 34
		distances.add(25f); // Level 35
		distances.add(25f); // Level 36
		distances.add(20f); // Level 37
		distances.add(20f); // Level 38
		distances.add(20f); // Level 39
		distances.add(25f); // Level 40
	}

	public static float getDistance(int level) {
		return distances.get(level - 1);
	}
}
