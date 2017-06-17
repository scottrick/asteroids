package com.hatfat.asteroids;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsDebugSystem extends IteratingSystem implements EntityListener {

    private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);

    private World world;
    private Camera camera;

    private Box2DDebugRenderer debugRenderer;

    public PhysicsDebugSystem(World world, Camera camera) {
        super(Family.all(PhysicsComponent.class).get());

        this.world = world;
        this.camera = camera;

        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);

        /* listen for entity add/remove events! */
        getEngine().addEntityListener(Family.all(PhysicsComponent.class).get(), this);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void entityAdded(Entity entity) {
//        PhysicsComponent physicsComponent = pm.get(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
