package com.jnv.bounded.utilities;

import com.badlogic.gdx.math.Vector2;
import com.jnv.bounded.main.Bounded;

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
			new Dimensions((Bounded.WIDTH - DIALOG_WIDTH) / 2, (Bounded.HEIGHT - DIALOG_HEIGHT) / 2,
					DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions((Bounded.WIDTH - DIALOG_WIDTH) / 2, (Bounded.HEIGHT - DIALOG_HEIGHT) / 2,
					DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(100, Bounded.HEIGHT - DIALOG_HEIGHT - 20, DIALOG_WIDTH, DIALOG_HEIGHT),
			new Dimensions(Bounded.WIDTH - DIALOG_WIDTH - 70, 150, DIALOG_WIDTH, DIALOG_HEIGHT),
			//new Dimensions(, DIALOG_WIDTH, DIALOG_HEIGHT),
			null,

	};
}
