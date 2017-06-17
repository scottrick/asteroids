package com.hatfat.asteroids;

import com.badlogic.ashley.systems.IntervalSystem;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsSystem extends IntervalSystem {

    private World world;

    public static final int UPDATES_PER_SECOND = 125;

    public PhysicsSystem(World world) {
        super(1.0f / UPDATES_PER_SECOND);

        this.world = world;
    }

    @Override
    protected void updateInterval() {
        world.step(getInterval(), 6, 2);
    }
}
