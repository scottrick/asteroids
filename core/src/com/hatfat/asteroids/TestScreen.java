package com.hatfat.asteroids;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class TestScreen extends GameScreen {

    private World world;

    private Skin skin;
    private BitmapFont buttonFont;

    public TestScreen(Game game) {
        super(game);

        Box2D.init();
        world = new World(new Vector2(0.0f, 0.0f), true);

        engine.addSystem(new PhysicsSystem(world));
//        engine.addSystem(new PhysicsDebugSystem(world, camera));
        engine.addSystem(new RenderSystem(camera));

        setupFonts();
        setupButton();
        setupTest();
    }

    private void setupTest() {
        for (int i = 0; i < 10; i++) {
            Entity entity = new Entity();

            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(-30f, -screenHeight / 2f + i * screenHeight / 10);
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
            bodyDef.position.set(30f, -screenHeight / 2f + i * screenHeight / 10);
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
            def.position.set(3f, screenWidth / -2f);
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

    private void setupFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();
    }

    private void setupButton() {
        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Store the default libgdx font under the name "default".
        skin.add("buttonFont", buttonFont);

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.GOLDENROD);
        textButtonStyle.checked = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("buttonFont");
        skin.add("default", textButtonStyle);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.right().bottom();
        stage.addActor(table);

        // Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        final TextButton againButton = new TextButton("Again", skin);
        table.add(againButton).minWidth(Gdx.graphics.getWidth() * 0.15f).minHeight(Gdx.graphics.getHeight() * 0.05f).pad(Gdx.graphics.getWidth() * 0.01f);
        table.row();

        final TextButton menuButton = new TextButton("Menu", skin);
        table.add(menuButton).minWidth(Gdx.graphics.getWidth() * 0.15f).minHeight(Gdx.graphics.getHeight() * 0.05f).pad(Gdx.graphics.getWidth() * 0.01f);

        againButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                setupTest();
            }
        });

        menuButton.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                changeToScreen(new MainMenuScreen(game));
            }
        });
    }

    @Override
    public void show() {

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

    @Override
    protected float getBaseScreenSize() {
        return 40.0f;
    }
}
