package com.hatfat.asteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Asteroids extends Game {

    private BitmapFont fpsFont;

    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));

        setupFont();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        fpsFont.draw(spriteBatch, "fps " + Gdx.graphics.getFramesPerSecond(), 8, 16);
        spriteBatch.end();
    }

    private void setupFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 10;
        fpsFont = generator.generateFont(parameter);
        generator.dispose();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
}
