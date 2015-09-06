package com.jnv.bounded.utilities;

import com.badlogic.gdx.math.Vector2;
import com.jnv.bounded.main.Bounded;

import static com.jnv.bounded.level.ui.Toolbar.TOOLBAR_BUTTON_PADDING;
import static com.jnv.bounded.level.ui.Toolbar.TOOLBAR_BUTTON_SIZE;

public final class TutorialDialogCoords {

	public static final float DIALOG_WIDTH = 800;
	public static final float DIALOG_HEIGHT = 400;
	public static final float DIALOG_HALF_WIDTH = 400;
	public static final float DIALOG_HALF_HEIGHT = 200;

	public static final Vector2[] level1 = {
			new Vector2((Bounded.WIDTH - DIALOG_WIDTH) / 2, (Bounded.HEIGHT - DIALOG_HEIGHT) / 2),
			new Vector2((Bounded.WIDTH - DIALOG_WIDTH) / 2, (Bounded.HEIGHT - DIALOG_HEIGHT) / 2),
			new Vector2(100, Bounded.HEIGHT - DIALOG_HEIGHT - 20),
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH - 70, 150),
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH, Bounded.HEIGHT - DIALOG_HEIGHT),
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH, 120),
			new Vector2((Bounded.WIDTH - DIALOG_WIDTH) / 2, 120),
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH, 120),
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH, 120),
			new Vector2(0, 120),
			new Vector2(0, 120),
			new Vector2(520 - DIALOG_HALF_WIDTH, 120),
			new Vector2(660 - DIALOG_HALF_WIDTH, 120),
			new Vector2(792 - DIALOG_HALF_WIDTH, 120),
			new Vector2(0, 120)
	};

	public static final Dimensions[] level1Triggers = {
			new Dimensions(level1[0], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[1], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[2], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[3], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[4], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(new Vector2(8 * TOOLBAR_BUTTON_SIZE + 9 * TOOLBAR_BUTTON_PADDING, 0),
					TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE),
			new Dimensions(level1[6], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[7], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[8], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[9], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[10], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[11], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[12], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[13], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(new Vector2(TOOLBAR_BUTTON_PADDING, 0), TOOLBAR_BUTTON_SIZE,
					TOOLBAR_BUTTON_SIZE),
	};
}
