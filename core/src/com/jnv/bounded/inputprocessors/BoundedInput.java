/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.inputprocessors;

public class BoundedInput {

	public static float x, y, px, py;
	public static boolean down;
	public static boolean pdown;
	public static boolean rightFling, leftFling, upFling, downFling;
	public static boolean pannedRight, pannedLeft, pannedUp, pannedDown;
	public static float deltaX, deltaY;
	public static float zoom = 1;
	public static boolean zoomed;
	public static boolean isPanned;
	public static float deltaX2, deltaY2, initX, initY;
	public static boolean isTapped;

	public static void update() {
		pdown = down;
		px = x;
		py = y;
		rightFling = false;
		leftFling = false;
		upFling = false;
		downFling = false;
		pannedDown = false;
		pannedLeft = false;
		pannedRight = false;
		pannedUp = false;
		deltaX = 0;
		deltaY = 0;
		deltaX2 = 0;
		deltaY2 = 0;
		zoomed = false;
		isPanned = false;
		isTapped = false;
	}

	// Getters
	public static boolean isTapped() {
		return down && !pdown;
	}

	public static boolean isDragged() {
		return down && pdown;
	}

	public static boolean isReleased() {
		return !down && pdown;
	}

}