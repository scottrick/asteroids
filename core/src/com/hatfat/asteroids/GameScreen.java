package com.hatfat.asteroids;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class GameScreen implements Screen {

    protected float screenWidth = getBaseScreenSize();
    protected float screenHeight = getBaseScreenSize();

    protected Game game;

    protected Engine engine;
    protected OrthographicCamera camera;

    protected Stage stage;

    public GameScreen(Game game) {
        this.game = game;

        engine = new Engine();
        camera = new OrthographicCamera();

        engine.addSystem(new DebugSystem());

        setupStage();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        engine.update(delta);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        float ratio = (float) width / (float) height;
        float xMultiplier = 1.0f;
        float yMultiplier = 1.0f;

        if (ratio > 1.0f) {
            xMultiplier = ratio;
        }
        else {
            yMultiplier = 1.0f / ratio;
        }

        screenWidth = xMultiplier * getBaseScreenSize();
        screenHeight = yMultiplier * getBaseScreenSize();

        camera.setToOrtho(false, screenWidth, screenHeight);
        camera.position.set(0.0f, 0.0f, 0.0f);
        camera.update();

        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void setupStage() {
        stage = new Stage();
//        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    protected void changeToScreen(Screen newScreen) {
        Screen previousScreen = game.getScreen();
        game.setScreen(newScreen);
        previousScreen.dispose();
    }

    protected abstract float getBaseScreenSize();
}
