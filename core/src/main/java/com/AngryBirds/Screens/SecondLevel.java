package com.AngryBirds.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class SecondLevel implements Screen {

    //setting up box2d
    private final float TIMESTEP = 1 / 60f; // frames
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;


    //arrays for bodies
    Array<Body> a1 = new Array<>();
    private ArrayList<Block> woodBlocks;

    BodyDef bodydef = new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    private World world;






    private SpriteBatch batch;
    private Sprite background, catapult, wood;
    private Bird red,blue;
    private Pig pig1, pig2, pig3;
    private OrthographicCamera camera;
    private Sprite pause;
    private Body ground, catapultBody;


    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private static Music bgm;

    private void initializeTextures() {

        //setting up the camera
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        camera.update();

        //setting up the world
        world = new World(new Vector2(0,-98f), true);


        batch = new SpriteBatch();
        background = new Sprite(new Texture("background.png"));
        catapult = new Sprite(new Texture("cat.png"));
        red = new Bird("red", 100,125,45f,world);
        blue = new Bird("blue", 175, 125,45f, world);
        pause = new Sprite(new Texture("pause_button.png"));
        bgm = FirstLevel.getBgm();

        wood = new Sprite(new Texture("wood_horizontal.png"));
        pig1 = new Pig("small",45,825,125,world);
        pig2 = new Pig("small",45,975,125,world);


        //ground setup
        bodydef.type = BodyDef.BodyType.StaticBody;

        // Position the ground at the center horizontally and at the bottom of the screen
        bodydef.position.set(WORLD_WIDTH / 2, 75); // Set y to half the ground height
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(WORLD_WIDTH / 2, 37.5f); // Width is full screen, height is 75px (37.5f * 2)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 5f;
        // Create the ground body and fixture
        Body groundBody = world.createBody(bodydef);
        Fixture groundFixture = groundBody.createFixture(fixtureDef);
        // Save reference to the body for rendering or other logic
        ground = groundBody;
        // Dispose the shape after use
        groundShape.dispose();


        //setting up the catapult
        // Create the catapult physics body
        catapult.setSize(catapult.getWidth()/2, catapult.getHeight()/2);
        BodyDef catapultBodyDef = new BodyDef();
        catapultBodyDef.type = BodyDef.BodyType.StaticBody; // Static body
        catapultBodyDef.position.set(275, 150+37.5f); // Centered at the rectangle (adjust to match your setup)

        PolygonShape catapultShape = new PolygonShape();
        catapultShape.setAsBox(100/2, 100/2); // Half-width and half-height of the catapult (100X125 total)

        FixtureDef catapultFixtureDef = new FixtureDef();
        catapultFixtureDef.shape = catapultShape;
        catapultFixtureDef.density = 0f;
        catapultFixtureDef.friction = 0.8f;
        catapultFixtureDef.restitution = 0f;

        catapultBody = world.createBody(catapultBodyDef);
        catapultBody.createFixture(catapultFixtureDef);

        // Dispose the shape after creating the fixture
        catapultShape.dispose();



        //setting up the wood
        woodBlocks = new ArrayList<>();

        //horizontal x = +125

        Block wood1,wood2,wood3,wood4,wood5,wood6,wood7,wood8;
        wood1 = new Block("vertical",
            750 ,150+ 37.5f,1f,world);
        wood2 = new Block("horizontal",
            825 ,150+wood1.getBlockSprite().getHeight()-27,1f,world);
        wood3 = new Block("vertical",
            750+wood2.getBlockSprite().getWidth() ,150+ 37.5f,1f,world);
        wood4 = new Block("horizontal",
            975,150+wood1.getBlockSprite().getHeight()-27,1f,world);
        wood5 = new Block("vertical",
            wood3.getX()+ wood2.getBlockSprite().getWidth() -10,150+ 37.5f,1f,world);
        wood6 = new Block("vertical",
            (wood1.getX()+ wood3.getX())/2 ,150+50+wood1.getBlockSprite().getHeight(),1f,world);
        wood7 = new Block("horizontal",
            wood6.getX()+75 ,150+wood1.getBlockSprite().getHeight()+wood6.getBlockSprite().getHeight()-24,1f,world);
        wood8 = new Block("vertical",
            wood6.getX()+ wood2.getBlockSprite().getWidth() -10 ,150+50+wood1.getBlockSprite().getHeight(),1f,world);
        woodBlocks.add(wood1);
        woodBlocks.add(wood2);
        woodBlocks.add(wood3);
        woodBlocks.add(wood4);
        woodBlocks.add(wood5);
        woodBlocks.add(wood6);
        woodBlocks.add(wood7);
        woodBlocks.add(wood8);

        pig3 = new Pig("small",45,900,125+37.5f+wood1.getBlockSprite().getHeight(),world);




    }

    @Override
    public void show() {
        initializeTextures();
    }

    @Override
    public void render(float delta) {
        clearScreen();

        //create the Box2D simulation
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        debugRenderer.render(world, camera.combined);

        if (Gdx.input.isTouched()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y;
            }
        }

        batch.begin();
        drawBackground();
        drawWoodBlocks();
        drawCatapult();
        drawPause();
        world.getBodies(a1);
        for (Body b : a1) {
            if (b.getUserData() instanceof Bird) {
                Bird bird = (Bird) b.getUserData();
                Sprite sprite = bird.getBirdSprite();
                sprite.setPosition(
                    b.getPosition().x - sprite.getWidth() / 2,
                    b.getPosition().y - sprite.getHeight() / 2
                );
                sprite.setRotation(b.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }

            else if(b.getUserData() instanceof Pig){
                Pig pig = (Pig) b.getUserData();
                Sprite sprite = pig.getPigSprite();
                sprite.setPosition(
                    b.getPosition().x - sprite.getWidth() / 2,
                    b.getPosition().y - sprite.getHeight() / 2
                );
                sprite.setRotation(b.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        a1.clear();

        drawGround();
        batch.end();



        handleInput(pause.getBoundingRectangle());
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void handleInput(Rectangle r1) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y;

            if (r1.contains(clickPosition)) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen());
            }
        }
    }

    private void drawBackground() {
        background.draw(batch);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void drawGround() {
        Sprite groundSprite = new Sprite(new Texture("ground.png"));

        // Set the size of the ground sprite to match the virtual world width
        groundSprite.setSize(WORLD_WIDTH, 150); // Full screen width, height matching ground box

        // Position the ground sprite at the bottom
        groundSprite.setPosition(0, 0);

        // Draw the ground sprite
        groundSprite.draw(batch);
    }

    private void drawCatapult() {
        Vector2 catapultPosition = catapultBody.getPosition(); // Get the body's position
        catapult.setPosition(
            catapultPosition.x - catapult.getWidth()/2, // Center the sprite on the body
            catapultPosition.y - catapult.getHeight()/2
        );

        // Draw the catapult sprite
        catapult.draw(batch);
    }


    private void drawWoodBlocks() {
        for (Block block : woodBlocks) {
            // Sync the block's sprite with its Box2D body
            block.updateSprite();

            // Draw the block's sprite
            block.getBlockSprite().draw(batch);
        }
    }




    private void drawPause() {
        pause.draw(batch);
        pause.setSize(50, 50);
        pause.setPosition(Gdx.graphics.getWidth() - 75, Gdx.graphics.getHeight() - 50);
    }

    @Override
    public void resize(int width, int height) {
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
        background.getTexture().dispose();

        blue.getBirdSprite().getTexture().dispose();
        catapult.getTexture().dispose();
        wood.getTexture().dispose();
        pig1.getPigSprite().getTexture().dispose();
        pig2.getPigSprite().getTexture().dispose();
        pig3.getPigSprite().getTexture().dispose();
        pause.getTexture().dispose();
        for (Block block : woodBlocks) {
            block.dispose();
        }
        woodBlocks.clear();
    }
}
