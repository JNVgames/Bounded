package com.jnv.bounded.level.utilities;

import com.badlogic.gdx.math.Vector2;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.utilities.Dimensions;

import static com.jnv.bounded.level.ui.Toolbar.TOOLBAR_BUTTON_PADDING;
import static com.jnv.bounded.level.ui.Toolbar.TOOLBAR_BUTTON_SIZE;

public final class TutorialDialogCoords {

	public static final float DIALOG_WIDTH = 800;
	public static final float DIALOG_HEIGHT = 400;
	public static final float DIALOG_HALF_WIDTH = 400;
	public static final float DIALOG_HALF_HEIGHT = 200;

	public static final Dimensions SKIP_LEVEL_DIM = new Dimensions(Bounded.WIDTH - 377,
			Bounded.HEIGHT - 91, 377, 91);

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

	public static final Vector2[] level2 = {
			new Vector2(1133 - DIALOG_WIDTH, 345 + 30 - DIALOG_HALF_HEIGHT),
			new Vector2(0, Bounded.HEIGHT - 70 - DIALOG_HEIGHT),
			new Vector2((Bounded.WIDTH - DIALOG_WIDTH) / 2, (Bounded.HEIGHT - DIALOG_HEIGHT) / 2)
	};

	public static final Vector2[] level3 = {
			new Vector2(0, Bounded.HEIGHT - 472)
	};

	public static final Vector2[] level4 = {
			new Vector2(465 - DIALOG_HALF_WIDTH, Bounded.HEIGHT - 530),
	};

	public static final Vector2[] level5 = {
			new Vector2(817 - DIALOG_HALF_WIDTH, 0)
	};

	public static final Vector2[] level6 = {
			new Vector2(80, Bounded.HEIGHT / 2 - DIALOG_HALF_HEIGHT)
	};

	public static final Vector2[] level7 = {
			new Vector2(Bounded.WIDTH - DIALOG_WIDTH, Bounded.HEIGHT - 643),
			new Vector2(Bounded.WIDTH / 2 - DIALOG_HALF_WIDTH, 0)
	};

	public static final Vector2[] level8 = {
			new Vector2(Bounded.WIDTH / 2 - DIALOG_HALF_WIDTH,
					Bounded.HEIGHT / 2 - DIALOG_HALF_HEIGHT)
	};

	public static final Vector2[][] allCoords = {
			level1, level2, level3, level4, level5, level6, level7, level8
	};

	public static final Dimensions[] level1Triggers = {
			new Dimensions(level1[0], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[1], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[2], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[3], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level1[4], DIALOG_WIDTH, DIALOG_HEIGHT - 100),
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

	public static final Dimensions[] level2Triggers = {
			new Dimensions(level2[0], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level2[1], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level2[2], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level3Triggers = {
			new Dimensions(level3[0], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level4Triggers = {
			new Dimensions(level4[0], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level5Triggers =  {
			new Dimensions(level5[0], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level6Triggers =  {
			new Dimensions(level6[0], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level7Triggers = {
			new Dimensions(level7[0], DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(level7[1], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[] level8Triggers = {
			new Dimensions(level8[0], DIALOG_WIDTH, DIALOG_HEIGHT)
	};

	public static final Dimensions[][] levelTriggers = {
			level1Triggers, level2Triggers, level3Triggers, level4Triggers, level5Triggers,
			level6Triggers, level7Triggers, level8Triggers
	};
}
