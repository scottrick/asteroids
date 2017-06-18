package com.hatfat.asteroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hatfat.asteroids.Asteroids;
import com.hatfat.asteroids.PhysicsSystem;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());

        config.fullscreen = false;

        if (!config.fullscreen) {
            config.width /= 2f;
            config.height /= 2f;
        }

        config.foregroundFPS = PhysicsSystem.UPDATES_PER_SECOND;
		config.backgroundFPS = 10;
        config.resizable = false;
        config.samples = 0;
        config.vSyncEnabled = true;

		new LwjglApplication(new Asteroids(), config);
	}
}
