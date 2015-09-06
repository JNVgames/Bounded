package com.jnv.bounded.level.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.handlers.GameStateManager;
import com.jnv.bounded.level.utilities.ToolbarButton;
import com.jnv.bounded.main.Bounded;
import com.jnv.bounded.resources.BoundedAssetManager;
import com.jnv.bounded.scene2d.InputListener;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {

	public static final float TOOLBAR_BUTTON_SIZE = Bounded.WIDTH / 12;
	public static final float TOOLBAR_BUTTON_PADDING = TOOLBAR_BUTTON_SIZE * (12 - 9) / 9;
	private LevelState levelState;
	private BoundedAssetManager res;
	private Stage stage;
	private Vector2[] buttonCoords;
	private boolean isToolbarShowing = false;
	private List<ToolbarButton> toolbarButtons, grayableButtons;
	private ToolbarButton pencilButton, eraseButton;

	public Toolbar(LevelState levelState) {
		this.levelState = levelState;
		stage = levelState.getGameStateManager().game().getStage();
		res = levelState.getGameStateManager().game().res;
		toolbarButtons = new ArrayList<ToolbarButton>();
		grayableButtons = new ArrayList<ToolbarButton>();
		calcToolbarCoords();
		drawToolbarButton();
		drawPressableGrayableButtons();
		drawPressableButtons();
		hideToolbar();
	}

	private void calcToolbarCoords() {
		buttonCoords = new Vector2[9];
		for (int i = 0; i < buttonCoords.length; i++) {
			buttonCoords[i] = new Vector2(
					i * TOOLBAR_BUTTON_SIZE + (i + 1) * TOOLBAR_BUTTON_PADDING, 0);
		}
	}

	private void drawToolbarButton() {
		Actor toolbarButton = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.draw(res.getTexture("toolbar"), buttonCoords[8].x, buttonCoords[8].y,
						TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
			}
		};
		toolbarButton.setBounds(buttonCoords[8].x, buttonCoords[8].y, TOOLBAR_BUTTON_SIZE,
				TOOLBAR_BUTTON_SIZE);
		toolbarButton.addListener(new InputListener(toolbarButton) {
			@Override
			public void doAction() {
				if (!isToolbarShowing) showToolbar();
				else hideToolbar();
			}
		});
		stage.addActor(toolbarButton);
	}

	public void showToolbar() {
		isToolbarShowing = true;
		for (ToolbarButton toolbarButton : toolbarButtons) {
			toolbarButton.setVisible(true);
			toolbarButton.setTouchable(Touchable.enabled);
		}
	}

	public void hideToolbar() {
		isToolbarShowing = false;
		for (ToolbarButton toolbarButton : toolbarButtons) {
			toolbarButton.setVisible(false);
			toolbarButton.setTouchable(Touchable.disabled);
		}
	}

	private void drawPressableGrayableButtons() {
		stage.addActor(createToolbarButton("play", 1, true, new Runnable() {
			@Override
			public void run() {
				levelState.getLevelEventsHandler().setPlayMode();
			}
		}));
		stage.addActor(createToolbarButton("erase_all", 4, true, new Runnable() {
			@Override
			public void run() {
				if (levelState.getEditState() != LevelState.EditState.PLAY) {
					levelState.getWallsHistoryManager().clearAll();
				}
			}
		}));
		stage.addActor(createToolbarButton("undo", 5, true, new Runnable() {
			@Override
			public void run() {
				if (levelState.getEditState() != LevelState.EditState.PLAY) {
					levelState.getWallsHistoryManager().undo();
				}
			}
		}));
		stage.addActor(createToolbarButton("redo", 6, true, new Runnable() {
			@Override
			public void run() {
				if (levelState.getEditState() != LevelState.EditState.PLAY) {
					levelState.getWallsHistoryManager().redo();
				}
			}
		}));
		drawEditButtons();
	}

	private void drawPressableButtons() {
		stage.addActor(createToolbarButton("reset", 7, false, new Runnable() {
			@Override
			public void run() {
				levelState.resetBall();
				levelState.setEditState(LevelState.EditState.DRAW);
				levelState.setCacheState(LevelState.EditState.DRAW);
				setButtonsNormal();
				setEditButtonsPressed();
			}
		}));
		stage.addActor(createToolbarButton("options", 8, false, new Runnable() {
			@Override
			public void run() {
				levelState.setCacheState(levelState.getEditState());
				levelState.setEditState(LevelState.EditState.OPTIONS);
				drawOptions();
			}
		}));
	}

	private void drawEditButtons() {
		pencilButton = new ToolbarButton(
				res.getTexture("pencil"), res.getTexture("pencil_pressed"),
				res.getTexture("pencil_gray"), (TOOLBAR_BUTTON_SIZE + 2 * TOOLBAR_BUTTON_PADDING), 0,
				TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
		pencilButton.setBounds(
				(TOOLBAR_BUTTON_SIZE + 2 * TOOLBAR_BUTTON_PADDING), 0,
				TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE
		);
		eraseButton = new ToolbarButton(
				res.getTexture("eraser"), res.getTexture("eraser_pressed"),
				res.getTexture("eraser_gray"), (2 * TOOLBAR_BUTTON_SIZE + 3 * TOOLBAR_BUTTON_PADDING), 0,
				TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE
		);
		eraseButton.setBounds(
				(2 * TOOLBAR_BUTTON_SIZE + 3 * TOOLBAR_BUTTON_PADDING), 0,
				TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE
		);

		pencilButton.clearListeners();
		pencilButton.addListener(new InputListener(pencilButton) {
			@Override
			public void doAction() {
				if (levelState.getEditState() != LevelState.EditState.PLAY) {
					levelState.setEditState(LevelState.EditState.DRAW);
					levelState.setCacheState(LevelState.EditState.DRAW);
					pencilButton.setPressed();
					eraseButton.setNormal();
				}
			}
		});
		eraseButton.clearListeners();
		eraseButton.addListener(new InputListener(eraseButton) {
			@Override
			public void doAction() {
				if (levelState.getEditState() != LevelState.EditState.PLAY) {
					levelState.setEditState(LevelState.EditState.ERASE);
					levelState.setCacheState(LevelState.EditState.ERASE);
					eraseButton.setPressed();
					pencilButton.setNormal();
				}
			}
		});
		grayableButtons.add(pencilButton);
		grayableButtons.add(eraseButton);
		pencilButton.setPressed();

		stage.addActor(pencilButton);
		stage.addActor(eraseButton);

		toolbarButtons.add(pencilButton);
		toolbarButtons.add(eraseButton);
	}

	private ToolbarButton createToolbarButton(String buttonName, int slot, boolean isGrayButton,
											  final Runnable action) {
		ToolbarButton tmp;
		if (isGrayButton) {
			tmp = new ToolbarButton(res.getTexture(buttonName), res.getTexture(buttonName + "_pressed"),
					res.getTexture(buttonName + "_gray"),
					((slot - 1) * TOOLBAR_BUTTON_SIZE + slot * TOOLBAR_BUTTON_PADDING), 0,
					TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
			grayableButtons.add(tmp);
		} else {
			tmp = new ToolbarButton(res.getTexture(buttonName), res.getTexture(buttonName + "_pressed"),
					((slot - 1) * TOOLBAR_BUTTON_SIZE + slot * TOOLBAR_BUTTON_PADDING), 0,
					TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
		}
		tmp.setBounds(((slot - 1) * TOOLBAR_BUTTON_SIZE + slot * TOOLBAR_BUTTON_PADDING), 0,
				TOOLBAR_BUTTON_SIZE, TOOLBAR_BUTTON_SIZE);
		tmp.addListener(new InputListener(tmp) {
			@Override
			public void doAction() {
				action.run();
			}
		});
		toolbarButtons.add(tmp);
		return tmp;
	}

	public void setButtonsGray() {
		for (ToolbarButton toolbarButton : grayableButtons) {
			toolbarButton.setGray();
			toolbarButton.setTouchable(Touchable.disabled);
		}
	}

	public void setButtonsNormal() {
		for (ToolbarButton toolbarButton : grayableButtons) {
			toolbarButton.setNormal();
			toolbarButton.setTouchable(Touchable.enabled);
		}
	}

	private void drawOptions() {
		final Actor mask = new Actor() {
			@Override
			public void draw(Batch batch, float parentAlpha) {
				batch.draw(res.getTexture("opacity_mask"), 0, 0, Bounded.WIDTH, Bounded.HEIGHT);
			}
		};
		final Image returnButton = new Image(res.getTexture("return"));

		mask.setBounds(0, 0, Bounded.WIDTH, Bounded.HEIGHT);
		mask.addListener(new InputListener(mask) {
			@Override
			public void doAction() {
				mask.remove();
				returnButton.remove();
				levelState.setEditState(levelState.getCacheState());
			}
		});

		returnButton.layout();
		returnButton.setBounds(((Bounded.WIDTH / 2) - (res.getTexture("return").getWidth() / 2)),
				Bounded.HEIGHT * 3 / 5, 612, 144);
		returnButton.addListener(new InputListener(returnButton) {
			@Override
			public void doAction() {
				levelState.getGameStateManager().setState(GameStateManager.State.LEVELSELECTION);
			}
		});

		stage.addActor(mask);
		stage.addActor(returnButton);
	}

	public void setEditButtonsPressed() {
		if (levelState.getEditState() == LevelState.EditState.DRAW) {
			pencilButton.setPressed();
		} else eraseButton.setPressed();
	}
}
