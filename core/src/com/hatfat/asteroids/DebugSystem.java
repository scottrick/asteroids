package com.hatfat.asteroids;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DebugSystem extends EntitySystem {

    @Override
    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
}
