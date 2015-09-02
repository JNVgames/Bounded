package com.jnv.bounded.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jnv.bounded.gamestates.LevelState;
import com.jnv.bounded.inputprocessors.BoundedInput;
import com.jnv.bounded.utilities.Constants;

public class Panning {
    private boolean panning = false;
    private OrthographicCamera cam;
    private TiledMapLoader tml;

    public Panning(LevelState levelState) {
        cam = levelState.getCam();
        tml = levelState.getTml();
    }

    public void handleInput() {
        if (Gdx.input.isTouched(1) && !panning) {
            panning = true;
            BoundedInput.initX = cam.position.x;
            BoundedInput.initY = cam.position.y;
        }

        if (BoundedInput.isReleased()) {
            panning = false;
        }

        if (BoundedInput.isPanned) {
            handleNormalPanning();
            handleBorderPanning();
            BoundedInput.isPanned = false;
        }
    }

    private void handleNormalPanning() {
        if (((BoundedInput.initX - scale(BoundedInput.deltaX2)) < scale(tml.getLevelWidth())) &&
                (BoundedInput.initX - scale(BoundedInput.deltaX2) > 0) &&
                (BoundedInput.initY + scale(BoundedInput.deltaY2) > 0) &&
                (BoundedInput.initY + scale(BoundedInput.deltaY2)) < scale(tml.getLevelHeight())) {
            cam.position.set(BoundedInput.initX - scale(BoundedInput.deltaX2),
                    BoundedInput.initY + scale(BoundedInput.deltaY2), 0);
        }
    }
    private void handleBorderPanning() {
        if (((BoundedInput.initX - scale(BoundedInput.deltaX2)) < scale(tml.getLevelWidth())) &&
                (BoundedInput.initX - scale(BoundedInput.deltaX2) > 0) &&
                (!(BoundedInput.initY + scale(BoundedInput.deltaY2) > 0) ||
                        !((BoundedInput.initY + scale(BoundedInput.deltaY2)) < scale(tml.getLevelHeight())))) {

            if(!(BoundedInput.initY + scale(BoundedInput.deltaY2) > 0)) {
                cam.position.set(BoundedInput.initX - scale(BoundedInput.deltaX2),
                        0, 0);
            } else {
                cam.position.set(BoundedInput.initX - scale(BoundedInput.deltaX2),
                        scale(tml.getLevelHeight()), 0);
            }
        }
        if ((!((BoundedInput.initX - scale(BoundedInput.deltaX2)) < scale(tml.getLevelWidth())) ||
                !(BoundedInput.initX - scale(BoundedInput.deltaX2) > 0)) &&
                (BoundedInput.initY + scale(BoundedInput.deltaY2) > 0) &&
                (BoundedInput.initY + scale(BoundedInput.deltaY2)) < scale(tml.getLevelHeight())) {

            if (!((BoundedInput.initX - scale(BoundedInput.deltaX2)) < scale(tml.getLevelWidth()))) {
                cam.position.set(scale(tml.getLevelWidth()),
                        BoundedInput.initY + scale(BoundedInput.deltaY2), 0);
            } else {
                cam.position.set(0,
                        BoundedInput.initY + scale(BoundedInput.deltaY2), 0);
            }
        }
    }
    private float scale(float x) {
        return x / Constants.PPM;
    }
}
