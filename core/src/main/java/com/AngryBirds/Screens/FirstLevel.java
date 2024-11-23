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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.AngryBirds.Screens.Block;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;

public class FirstLevel implements Screen {


    private final float TIMESTEP = 1 / 60f; // frames
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    boolean touched = false;


    //IMPLEMENTing box 2d
    Array<Body> a1 = new Array<>();

    //array for blocks
    private ArrayList<Block> woodBlocks;


    private SpriteBatch batch;
    private Sprite background,catapult;
    private Bird green,blue;
    private Pig pig1;
    private OrthographicCamera camera;
    private static Music bgm;
    private Sprite pause;
    private Body ground,catapultBody;

    BodyDef bodydef = new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    private World world;

    // Virtual world dimensions
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private void initializeTextures() {

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        camera.update();

        batch = new SpriteBatch();
        world = new World(new Vector2(0, -98f), true);
        background = new Sprite(new Texture("background.png"));
        catapult = new Sprite(new Texture("cat.png"));
        green = new Bird("green", 100, 125, 45f , world);
        blue = new Bird("blue", 175, 125, 45f , world);
        pig1 = new Pig("small",45,825,125,world);
        pause = new Sprite(new Texture("pause_button.png"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds\\game.wav"));
        bgm.setLooping(true);
        bgm.play();



        // Ground setup in initializeTextures
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



        ///creating the wood blocks
        woodBlocks = new ArrayList<>();

        // Creating wood blocks
        Block b2 = new Block("vertical", 750,  150+37.5f, 1f, world);

        Block b1 = new Block("horizontal",
            825,   150+b2.getBlockSprite().getHeight()-27, 1f, world);


        Block b3 = new Block("vertical", 750+b1.getBlockSprite().getWidth()-2,  150+37.5f, 1f, world);


        woodBlocks.add(b1); // wood1
        woodBlocks.add(b2); // wood2
        woodBlocks.add(b3); // wood3

        placeBirdOnCatapult(blue);




    }

    private void initializeMusic() {
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/game.wav"));
        bgm.setLooping(true);
        bgm.setVolume(0.75f);
        bgm.play();
    }
    @Override
    public void show() {
        // No specific actions needed on show
        initializeTextures();
        initializeMusic();
    }
    @Override
    public void render(float delta) {
        clearScreen();

        // Step the Box2D simulation
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        camera.update();
        batch.setProjectionMatrix(camera.combined);





        batch.begin();

        // Draw all elements
        drawBackground();

        drawWoodBlocks();
        drawcatapult();
        drawpause();

        // Sync and draw Box2D bodies with their sprites
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
//            else if(b == ground){
//                Sprite sprite = new Sprite(new Texture("ground.png"));
//                sprite.setPosition(
//                    b.getPosition().x - sprite.getWidth() / 2,
//                    b.getPosition().y - sprite.getHeight() / 2
//                );
//                sprite.draw(batch);
//            }
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
        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        debugRenderer.render(world, camera.combined);

        handleInput(pause.getBoundingRectangle());

        InputHandler(blue);



    }
    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
    private void handleInput(Rectangle r1) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system
            if (r1.contains(clickPosition)) {
                System.out.println("Pause Button Clicked!");
                // Handle pause button click
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen());
            }
        }
    }
    private void drawBackground() {
        background.draw(batch);
        background.setSize(graphics.getWidth(), graphics.getHeight());
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
    private void drawcatapult() {
        Vector2 catapultPosition = catapultBody.getPosition(); // Get the body's position
        catapult.setPosition(
            catapultPosition.x - catapult.getWidth()/2, // Center the sprite on the body
            catapultPosition.y - catapult.getHeight()/2
        );

        // Draw the catapult sprite
        catapult.draw(batch);
    }
    private void drawpause() {
        //handling the pause button
        pause.draw(batch);
        pause.setSize(50, 50);
        pause.setPosition(graphics.getWidth() - 75, graphics.getHeight() - 50);
    }
    private void drawWoodBlocks() {
        for (Block block : woodBlocks) {
            // Sync the block's sprite with its Box2D body
            block.updateSprite();

            // Draw the block's sprite
            block.getBlockSprite().draw(batch);
        }
    }




    @Override
    public void resize(int width, int height) {
        // Handle resizing if needed
    }

    @Override
    public void pause() {
        // Not needed for this example
    }

    @Override
    public void resume() {
        // Not needed for this example
    }

    @Override
    public void hide() {
        // Not needed for this example
    }

    @Override
    public void dispose() {
        background.getTexture().dispose();

        catapult.getTexture().dispose();
        woodBlocks.get(0).getBlockSprite().getTexture().dispose();
        woodBlocks.get(1).getBlockSprite().getTexture().dispose();
        woodBlocks.get(2).getBlockSprite().getTexture().dispose();
        pig1.getPigSprite().getTexture().dispose();
        pause.getTexture().dispose();
        bgm.dispose();

        for (Block block : woodBlocks) {
            block.dispose();
        }
        woodBlocks.clear();
    }

    public static Music getBgm() {
        return bgm;
    }

    private void placeBirdOnCatapult(Bird bird) {
        // Get catapult's position
        Vector2 catapultPosition = catapultBody.getPosition();

        // Place bird slightly above the catapult
        float birdX = catapultPosition.x;
        float birdY = catapultPosition.y + (catapult.getHeight() / 2) + (bird.getSize() / 2);

        // Update bird's position using Box2D's setTransform
        bird.getBody().setTransform(birdX, birdY, 0); // (x, y, angle)
    }


    private void InputHandler(Bird bird) {
        Vector3 mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        if (Gdx.input.isTouched()) {
            touched = true;

            // Get mouse position and convert to world coordinates

            camera.unproject(mousePos);

            // Calculate the angle and update bird's position
            float angle = new Vector2(mousePos.x, mousePos.y).sub(bird.getBody().getPosition()).angleRad();
            bird.getBody().setTransform(mousePos.x, mousePos.y, angle);

            // Stop any motion while dragging
            bird.getBody().setLinearVelocity(0, 0);
            bird.getBody().setActive(true);
        }

        if (touched && !Gdx.input.isTouched()) { // Release logic
            touched = false;

            // Get current bird position and calculate release velocity
            Vector2 birdPosition = bird.getBody().getPosition();
            Vector2 releaseVelocity = new Vector2(birdPosition.x - mousePos.x, birdPosition.y - mousePos.y).scl(-5f);

            // Apply the release velocity
            bird.getBody().setLinearVelocity(releaseVelocity);
        }
    }


}

