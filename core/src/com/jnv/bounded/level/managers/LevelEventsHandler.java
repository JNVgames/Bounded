/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.level.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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
import com.jnv.bounded.level.events.LevelComplete;
import com.jnv.bounded.main.Bounded;

import java.util.ArrayList;
import java.util.List;

public class LevelEventsHandler {

	private static Vector2 destroyedPosition;
	private List<Arrow> allArrows;
	private Ball ball;
	private List<BlackHole> allBlackHoles;
	private Boundaries boundaries;
	private List<Fan> allFans;
	private Key key;
	private List<Magnet> allMagnets;
	private List<Teleporter> allTeleporters;
	private List<GumCloud> allGumClouds;
	private List<Wall> allWalls;
	private List<Laser> allLasers;
	private Portal portal;
	private LevelState levelState;
	private com.jnv.bounded.level.events.BoundedContactListener cl;
	private WallsHistoryManager whm;
	private LevelComplete levelComplete;
	private boolean levelCompleted = false;

	public LevelEventsHandler(LevelState levelState) {
		this.levelState = levelState;
		cl = levelState.getBoundedContactListener();
		whm = levelState.getWallsHistoryManager();

		allArrows = new ArrayList<Arrow>();
		allBlackHoles = new ArrayList<BlackHole>();
		allFans = new ArrayList<Fan>();
		allGumClouds = new ArrayList<GumCloud>();
		allMagnets = new ArrayList<Magnet>();
		allTeleporters = new ArrayList<Teleporter>();
		allWalls = new ArrayList<Wall>();
		allLasers = new ArrayList<Laser>();
	}

	// Getters
	public static Vector2 getDestroyedPosition() {
		return destroyedPosition;
	}

	public void update(float dt) {
		ball.update(dt);
		if (key != null) {
			keyUpdate();
		}
		arrowUpdate();
		blackHoleUpdate(dt);
		fanUpdate(dt);
		gumCloudUpdate();
		magnetUpdate(dt);
		portalUpdate(dt);
		teleporterUpdate();
		laserUpdate();
		levelCompleteUpdate();
	}

	public void render(SpriteBatch sb) {
		ballRender(sb);
		arrowRender(sb);
		blackHoleRender(sb);
		gumCloudRender(sb);
		if (key != null) {
			keyRender(sb);
		}
		magnetRender(sb);
		portalRender(sb);
		wallRender(sb);
		teleporterRender(sb);
		fanRender(sb);
		boundariesRender(sb);
		laserRender(sb);
	}

	// Helper Functions
	private void levelCompletionEvents(SpriteBatch sb) {
		levelCompleted = true;
		destroyedPosition = ball.getBody().getPosition();
		ball.setMode(Ball.Mode.DESTROY);
		ball.render(sb);

		portal.setMode(Portal.Mode.DESTROY);
		portal.render(sb);

		if (ball.isDestroyAnimationFinished()) {
			whm.removeBody(ball.getBody());
			ball.setErased();
		}
		unlockLevel();
		if (levelComplete == null) {
			levelComplete = new LevelComplete(levelState);
		}
	}

	private void arrowUpdate() {
		if (cl.isTouchingArrow()) {
			for (Arrow arrow : allArrows) {
				for (Body body : cl.getAllArrows()) {
					if (arrow.getBody() == body) {
						arrow.update(ball.getBody());
					}
				}
			}
		}
	}

	private void blackHoleUpdate(float dt) {
		// For keeping track of time passed for animation
		for (BlackHole blackHole : allBlackHoles) {
			blackHole.update(dt);
		}
		if (cl.isTouchingBlackHole()) {
			// For updating ball
			for (BlackHole blackHole1 : allBlackHoles) {
				for (Body blackHole2 : cl.getAllBlackHoles()) {
					if (blackHole1.getBody() == blackHole2) {
						blackHole1.update(true);
					} else {
						blackHole1.update(false);
					}
				}
			}
		}
		if (cl.isTouchingBlackHoleCenter()) {
			levelState.resetBall();
		}
	}

	private void levelCompleteUpdate() {
		if (levelComplete != null) {
			levelComplete.handleInput();
		}
	}

	private void fanUpdate(float dt) {
		for (Fan fan : allFans) {
			fan.update(dt);
		}
		if (cl.isTouchingFan()) {
			for (Fan fan : allFans) {
				for (Body fan2 : cl.getAllFans()) {
					if (fan.getBody() == fan2) {
						fan.update(true);
					} else {
						fan.update(false);
					}
				}
			}
		}
	}

	private void gumCloudUpdate() {
		if (cl.isTouchingGumCloud()) {
			ball.getBody().setLinearDamping(2f);
		} else {
			ball.getBody().setLinearDamping(0f);
		}
	}

	private void keyUpdate() {
		if (cl.isTouchingKey()) {
			key.update(true);
			whm.removeBody(key.getBody());
		}
	}

	private void magnetUpdate(float dt) {
		for (Magnet magnet : allMagnets) {
			magnet.update(dt);
		}
		if (cl.isTouchingMagnet()) {
			for (Magnet magnet1 : allMagnets) {
				for (Body magnet2 : cl.getAllMagnets()) {
					if (magnet1.getBody() == magnet2) {
						magnet1.update(true);
					} else {
						magnet1.update(false);
					}
				}
			}
		}
	}

	private void portalUpdate(float dt) {
		portal.update(dt);
	}

	private void teleporterUpdate() {
		if (cl.isTouchingTeleporter()) {
			for (Teleporter tp : allTeleporters) {
				for (Body tp2 : cl.getAllTeleporters()) {
					if (tp.getBody() == tp2) {
						getTargetTeleporter(tp.getTargetTeleporter(), ball);
						break;
					}
				}
			}
		}
	}

	private void laserUpdate() {
		if (cl.isTouchingLaser()) {
			levelState.resetBall();
		}
	}

	private void arrowRender(SpriteBatch sb) {
		for (Arrow arrow : allArrows) {
			arrow.render(sb);
		}
	}

	private void ballRender(SpriteBatch sb) {
		if (cl.isTouchingPortal() && (key == null || key.isCollected()) || levelCompleted) {
			levelCompletionEvents(sb);
		} else {
			ball.render(sb);
		}
	}

	private void blackHoleRender(SpriteBatch sb) {
		for (BlackHole blackHole : allBlackHoles) {
			blackHole.render(sb);
		}
	}

	private void boundariesRender(SpriteBatch sb) {
		boundaries.render(sb);
	}

	private void fanRender(SpriteBatch sb) {
		for (Fan fan : allFans) {
			fan.render(sb);
		}
	}

	private void gumCloudRender(SpriteBatch sb) {
		for (GumCloud gumCloud : allGumClouds) {
			gumCloud.render(sb);
		}
	}

	private void keyRender(SpriteBatch sb) {
		if (key != null) {
			key.render(sb);
		}
	}

	public void levelCompleteRender(SpriteBatch sb) {
		if (levelComplete != null) {
			levelComplete.render(sb);
		}
	}

	private void magnetRender(SpriteBatch sb) {
		for (Magnet magnet : allMagnets) {
			magnet.render(sb);
		}
	}

	private void portalRender(SpriteBatch sb) {
		portal.render(sb);
	}

	private void teleporterRender(SpriteBatch sb) {
		for (Teleporter teleporters : allTeleporters) {
			teleporters.render(sb);
		}
	}

	private void wallRender(SpriteBatch sb) {
		for (Wall wall : allWalls) {
			wall.render(sb);
		}
	}

	private void laserRender(SpriteBatch sb) {
		for (Laser laser : allLasers) {
			laser.render(sb);
		}
	}

	public Ball getBall() {
		return ball;
	}

	private void getTargetTeleporter(int target, Ball ball) {
		for (Teleporter tp : allTeleporters) {
			if (target == tp.getTPNum()) {
				if (!tp.update(ball, cl.getTmpVelocity()))
					levelState.resetBall();
				break;
			}
		}
	}

	public boolean isLevelCompleted() {
		return levelCompleted;
	}

	// Setters
	public void unlockLevel() {
		Bounded.unlockLevel(LevelState.getLevel());
	}

	public void registerArrow(Arrow arrow) {
		allArrows.add(arrow);
	}

	public void registerBall(Ball ball) {
		this.ball = ball;
	}

	public void registerBlackHole(BlackHole blackHole) {
		allBlackHoles.add(blackHole);
	}

	public void registerBoundaries(Boundaries boundaries) {
		this.boundaries = boundaries;
	}

	public void registerFan(Fan fan) {
		allFans.add(fan);
	}

	public void registerGumCloud(GumCloud gumCloud) {
		allGumClouds.add(gumCloud);
	}

	public void registerKey(Key key) {
		this.key = key;
	}

	public void registerMagnet(Magnet magnet) {
		allMagnets.add(magnet);
	}

	public void registerPortal(Portal portal) {
		this.portal = portal;
	}

	public void registerTeleporters(Teleporter tp) {
		allTeleporters.add(tp);
	}

	public void registerWall(Wall wall) {
		allWalls.add(wall);
	}

	public void registerLaser(Laser laser) {
		allLasers.add(laser);
	}

	public void resetBall() {
		destroyedPosition = new Vector2(ball.getBody().getPosition());
		ball.setMode(Ball.Mode.RESET);
		ball.getBody().setAwake(true);
		ball.getBody().setTransform(ball.getOriginalPosition(), 0);
		ball.getBody().setLinearVelocity(0, 0);
		ball.getBody().setAngularVelocity(0);
		if (key != null) {
			if (!key.isCollected()) {
				whm.removeBody(key.getBody());
			}
			key.createKey();
			key.update(false);
		}
	}


}
