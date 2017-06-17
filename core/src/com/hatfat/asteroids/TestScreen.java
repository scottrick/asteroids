package com.hatfat.asteroids;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TestScreen implements Screen {

    private float BASE_SIZE = 40.0f;

    private float PLAYING_FIELD_WIDTH = BASE_SIZE;
    private float PLAYING_FIELD_HEIGHT = BASE_SIZE;

    private Engine engine;

    private World world;
    private OrthographicCamera camera;

    public TestScreen() {
        engine = new Engine();

        Box2D.init();
        world = new World(new Vector2(0.0f, 0.0f), true);
        camera = new OrthographicCamera();

        engine.addSystem(new PhysicsSystem(world));
        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new DebugSystem());
        engine.addSystem(new RenderSystem(camera));

        setupTest();
    }

    private void setupTest() {
        for (int i = 0; i < 10; i++) {
            Entity entity = new Entity();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(-30f, -BASE_SIZE / 2f + i * BASE_SIZE / 10);
            bodyDef.linearVelocity.set(1.0f + 2.0f * i, i * -.5f);

            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(1f, 1f);
            body.createFixture(shape, 1.0f);
            shape.dispose();

            PhysicsComponent physicsComponent = new PhysicsComponent(body);
            entity.add(physicsComponent);
            engine.addEntity(entity);
        }

        for (int i = 0; i < 10; i++) {
            Entity entity = new Entity();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(30f, -BASE_SIZE / 2f + i * BASE_SIZE / 10);
            bodyDef.linearVelocity.set(-1.0f - 2.0f * i, i * -.2f);

            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(1f, 1f);
            body.createFixture(shape, 1.0f);
            shape.dispose();

            PhysicsComponent physicsComponent = new PhysicsComponent(body);
            entity.add(physicsComponent);
            engine.addEntity(entity);
        }

        {
            Entity otherEntity = new Entity();

            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.DynamicBody;
            def.position.set(3f, PLAYING_FIELD_WIDTH / -2f);
            def.linearVelocity.set(0f, 4f);

            Body otherBody = world.createBody(def);

            PolygonShape shape = new PolygonShape();
            shape.setAsBox(1f, 1f);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f; // Make it bounce a little

            otherBody.createFixture(fixtureDef);
            shape.dispose();

            PhysicsComponent physicsComponent = new PhysicsComponent(otherBody);
            otherEntity.add(physicsComponent);
            engine.addEntity(otherEntity);
        }

        {
            Entity centerBox = new Entity();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(0.0f, 0.0f);

            Body body = world.createBody(bodyDef);
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(1.0f, 1.0f);
            body.createFixture(shape, 20.0f);
            shape.dispose();

            PhysicsComponent physicsComponent = new PhysicsComponent(body);
            centerBox.add(physicsComponent);
            engine.addEntity(centerBox);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        engine.update(delta);
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

        PLAYING_FIELD_WIDTH = xMultiplier * BASE_SIZE;
        PLAYING_FIELD_HEIGHT = yMultiplier * BASE_SIZE;

        camera.setToOrtho(false, PLAYING_FIELD_WIDTH, PLAYING_FIELD_HEIGHT);
        camera.position.set(0.0f, 0.0f, 0.0f);
        camera.update();

        Gdx.app.log("TestScreen", "Playing field dimensions (" + PLAYING_FIELD_WIDTH + ", " + PLAYING_FIELD_HEIGHT + ")");
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

    }
}
