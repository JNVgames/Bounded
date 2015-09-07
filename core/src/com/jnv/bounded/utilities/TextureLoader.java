/*
 * Copyright (c) 2015. JNV Games, All rights reserved.
 */

package com.jnv.bounded.utilities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jnv.bounded.main.Bounded;

public class TextureLoader {

	private Bounded bounded;
	private int ballFrames = 11;
	private int blackHoleFrames = 8;
	private int fanFrames = 6;
	private int magnetFrames = 8;
	private int portalFrames = 11;

	public TextureLoader(Bounded bounded) {
		this.bounded = bounded;
	}

	public void loadLoadingScreenSprites() {
		bounded.res.loadTexture("game_images/JNVGames_splash_screen1280x720.png", "splash");
		bounded.res.loadTexture("game_images/entities/Ball200x200.png", "ball");
		bounded.res.loadTexture("game_images/entities/Blackhole200x200.png", "black hole");
	}

	public void loadAll() {
		loadFirstTimeHelpers();
		loadBackground();
		loadEntities();

		loadBallAnimations();
		loadBlackHoleAnimations();
		loadFanAnimations();
		loadMagnetAnimations();
		loadPortalAnimations();

		loadHUDButtons();
		loadLevelSelectButtons();
		loadMenuStateTextures();

		loadInfoScreens();
	}

	private void loadFirstTimeHelpers() {
		for (int i = 1; i <= 15; i++) {
			bounded.res.loadTexture("helpers/level1/" + i + ".png", "helper-level1-" + i);
			bounded.res.loadTexture("helpers/level1/mask" + i + ".png",
					"helper-level1-mask" + i);
		}
		for (int i = 1; i <= 3; i++) {
			bounded.res.loadTexture("helpers/level2/" + i + ".png", "helper-level2-" + i);
			bounded.res.loadTexture("helpers/level2/mask" + i + ".png",
					"helper-level2-mask" + i);
		}
		bounded.res.loadTexture("helpers/level3/1.png", "helper-level3-1");
		bounded.res.loadTexture("helpers/level3/mask1.png", "helper-level3-mask1");
		bounded.res.loadTexture("helpers/level4/1.png", "helper-level4-1");
		bounded.res.loadTexture("helpers/level4/mask1.png", "helper-level4-mask1");
		bounded.res.loadTexture("helpers/level5/1.png", "helper-level5-1");
		bounded.res.loadTexture("helpers/level5/mask1.png", "helper-level5-mask1");
		bounded.res.loadTexture("helpers/level6/1.png", "helper-level6-1");
		bounded.res.loadTexture("helpers/level6/mask1.png", "helper-level6-mask1");
		for (int i = 1; i <= 2; i++) {
			bounded.res.loadTexture("helpers/level7/" + i + ".png", "helper-level7-" + i);
			bounded.res.loadTexture("helpers/level7/mask" + i + ".png",
					"helper-level7-mask" + i);
		}
		bounded.res.loadTexture("helpers/level8/1.png", "helper-level8-1");
		bounded.res.loadTexture("helpers/level8/mask1.png", "helper-level8-mask1");

		bounded.res.loadTexture("helpers/mask.png", "screen_mask");
		bounded.res.loadTexture("helpers/skip_level_button.png", "skip_level");
	}

	private void loadBackground() {
		bounded.res.loadTexture("backgrounds/blue1920x1080.png", "blue background");
		bounded.res.loadTexture("backgrounds/green1920x1080.png", "green background");
		bounded.res.loadTexture("backgrounds/orange1920x1080.png", "orange background");
		bounded.res.loadTexture("backgrounds/purple1920x1080.png", "purple background");
		bounded.res.loadTexture("backgrounds/red1920x1080.png", "red background");
		bounded.res.loadTexture("game_images/helper1280x720.png", "helper background");
	}

	private void loadEntities() {
		bounded.res.loadTexture("game_images/entities/Arrow200x100.png", "arrow");
		bounded.res.loadTexture("game_images/entities/boundary1280x8.png", "boundaries");
		bounded.res.loadTexture("game_images/entities/Gumcloud200x100.png", "gum cloud");
		bounded.res.loadTexture("game_images/entities/Key100x200.png", "key");
		bounded.res.loadTexture("game_images/entities/Magnet200x200.png", "magnet");
		bounded.res.loadTexture("game_images/entities/Portal200x100.png", "portal");
		bounded.res.loadTexture("game_images/entities/Teleporter100x200.png", "teleporter");
		bounded.res.loadTexture("game_images/entities/Wall200x100.png", "wall");
		bounded.res.loadTexture("game_images/entities/laser200x10.png", "laser");
	}

	private void loadHUDButtons() {
		bounded.res.loadTexture("game_images/level_complete800x400.png", "level_complete_window");
		bounded.res.loadTexture("game_images/next_level256x144.png", "next_level");
		bounded.res.loadTexture("game_images/level_select_button256x144.png", "level_select_button");
		bounded.res.loadTexture("game_images/black_toolbar150x150.png", "toolbar");
		bounded.res.loadTexture("game_images/play_button150x150.png", "play");
		bounded.res.loadTexture("game_images/play_button_pressed150x150.png", "play_pressed");
		bounded.res.loadTexture("game_images/play_gray150x150.png", "play_gray");
		bounded.res.loadTexture("game_images/pencil_pressed150x150.png", "pencil_pressed");
		bounded.res.loadTexture("game_images/pencil150x150.png", "pencil");
		bounded.res.loadTexture("game_images/pencil_gray150x150.png", "pencil_gray");
		bounded.res.loadTexture("game_images/eraser150x150.png", "eraser");
		bounded.res.loadTexture("game_images/eraser_pressed150x150.png", "eraser_pressed");
		bounded.res.loadTexture("game_images/eraser_gray150x150.png", "eraser_gray");
		bounded.res.loadTexture("game_images/undo150x150.png", "undo");
		bounded.res.loadTexture("game_images/undo_pressed150x150.png", "undo_pressed");
		bounded.res.loadTexture("game_images/undo_gray150x150.png", "undo_gray");
		bounded.res.loadTexture("game_images/redo150x150.png", "redo");
		bounded.res.loadTexture("game_images/redo_pressed150x150.png", "redo_pressed");
		bounded.res.loadTexture("game_images/redo_gray150x150.png", "redo_gray");
		bounded.res.loadTexture("game_images/erase_all150x150.png", "erase_all");
		bounded.res.loadTexture("game_images/erase_all_gray150x150.png", "erase_all_gray");
		bounded.res.loadTexture("game_images/erase_all_pressed150x150.png", "erase_all_pressed");
		bounded.res.loadTexture("game_images/reset150x150.png", "reset");
		bounded.res.loadTexture("game_images/reset_pressed150x150.png", "reset_pressed");
		bounded.res.loadTexture("game_images/settings150x150.png", "options");
		bounded.res.loadTexture("game_images/settings_pressed150x150.png", "options_pressed");
		bounded.res.loadTexture("game_images/level_screen612x144.png", "return");
		bounded.res.loadTexture("game_images/settings_screen854x720.png", "toolbar_box");
		bounded.res.loadTexture("game_images/settings_screen854x720.png", "opacity_mask");
		bounded.res.loadTexture("game_images/arrow_left200x100.png", "left_arrow");
		bounded.res.loadTexture("game_images/arrow_right200x100.png", "right_arrow");
	}

	private void loadBallAnimations() {
		for (int i = 1; i <= ballFrames; i++) {
			bounded.res.loadTexture("game_images/animations/ball/" +
					Integer.toString(i) + ".png", "ball" + Integer.toString(i));
		}
	}

	private void loadBlackHoleAnimations() {
		for (int i = 1; i <= blackHoleFrames; i++) {
			bounded.res.loadTexture("game_images/animations/blackHole/" +
					Integer.toString(i) + ".png", "blackHole" + Integer.toString(i));
		}
	}

	private void loadMagnetAnimations() {
		for (int i = 1; i <= magnetFrames; i++) {
			bounded.res.loadTexture("game_images/animations/magnet/" + Integer.toString(i) +
					".png", "magnet" + Integer.toString(i));
		}
	}

	private void loadFanAnimations() {
		for (int i = 1; i <= fanFrames; i++) {
			bounded.res.loadTexture("game_images/animations/fan/" + Integer.toString(i) +
					".png", "fan" + Integer.toString(i));
		}
	}

	private void loadPortalAnimations() {
		for (int i = 1; i <= portalFrames; i++) {
			bounded.res.loadTexture("game_images/animations/portal/" + Integer.toString(i) +
					".png", "portal" + Integer.toString(i));
		}
	}

	private void loadLevelSelectButtons() {
		bounded.res.loadTexture("game_images/levels/background9.png", "level select background");
		bounded.res.loadTexture("game_images/levels/info_button100x100.png", "info button");
		bounded.res.loadTexture("game_images/circle_x150x150.png", "x");
		bounded.res.loadTexture("game_images/level_screen_text612x144.png", "level select");
		bounded.res.loadTexture("game_images/blue_background800x400.png", "options_background");

		int amountOfLevelButtons = 40;

		for (int i = 1; i <= amountOfLevelButtons; i++) {
			bounded.res.loadTexture("game_images/levels/level" + i +
					"_button256x144.png", "level" + i + "_button");
		}

		for (int i = 1; i <= amountOfLevelButtons; i++) {
			bounded.res.loadTexture("game_images/locked_levels/level" + i +
					"_button_locked256x144.png", "level" + i + "_locked");
		}
	}

	private void loadMenuStateTextures() {
		bounded.res.loadTexture("game_images/bounded_start_screen1280x720.png", "start screen");
	}

	private void loadInfoScreens() {
		bounded.res.loadTexture("game_images/info_first_page1280x2880.png", "infopage1");
		bounded.res.loadTexture("game_images/info_second_page1280x2880.png", "infopage2");
		bounded.res.loadTexture("game_images/developers_page1280x1440.png", "infopage3");
	}

	public Array<TextureRegion> getBallFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = 1; i <= ballFrames; i++) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("ball" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getReverseBallFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = ballFrames; i > 0; i--) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("ball" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getBlackHoleFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = 1; i <= blackHoleFrames; i++) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("blackHole" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getFanFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = 1; i <= fanFrames; i++) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("fan" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getMagnetFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = 1; i <= magnetFrames; i++) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("magnet" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getPortalFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = 1; i <= portalFrames; i++) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("portal" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

	public Array<TextureRegion> getReversePortalFrames() {
		Array<TextureRegion> trList = new Array<TextureRegion>();
		for (int i = portalFrames; i > 0; i--) {
			TextureRegion tmp = new TextureRegion();
			tmp.setRegion(bounded.res.getTexture("portal" + Integer.toString(i)));
			trList.add(tmp);
		}
		return trList;
	}

}
