package com.hatfat.asteroids;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;

public class RenderSystem extends IteratingSystem {

    private ComponentMapper<PhysicsComponent> pm = ComponentMapper.getFor(PhysicsComponent.class);

    private ShapeRenderer shapeRenderer;
    private Camera camera;

    public RenderSystem(Camera camera) {
        super(Family.all(PhysicsComponent.class).get());

        this.shapeRenderer = new ShapeRenderer();
        this.camera = camera;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PhysicsComponent physicsComponent = pm.get(entity);
        Body body = physicsComponent.body;

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(0.0f, 0.8f, 0.0f, 1.0f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();

        shapeRenderer.translate(body.getPosition().x, body.getPosition().y, 0.0f);
        shapeRenderer.rotate(0.0f, 0.0f, 1.0f, body.getAngle() * 180.0f / (float) Math.PI);
        shapeRenderer.box(-1.0f, -1.0f, 0.0f, 2.0f, 2.0f, 0.0f);

        shapeRenderer.end();
    }
}
