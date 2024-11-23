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

public class ThirdLevel implements Screen {

    private final float TIMESTEP = 1 / 60f; // frames
    private final int VELOCITYITERATIONS = 8;
    private final int POSITIONITERATIONS = 3;
    boolean touched = false;


    //IMPLEMENTing box 2d
    Array<Body> a1 = new Array<>();

    //array for blocks
    private ArrayList<Block> woodBlocks;


    private SpriteBatch batch;
    private Sprite background, catapult;
    private Bird green, blue, red;
    private Pig pig1,pig2,pig3,pig4,pig5;
    private OrthographicCamera camera;
    private Sprite pause;
    private Body ground, catapultBody;

    BodyDef bodydef = new BodyDef();
    FixtureDef fixtureDef = new FixtureDef();
    private World world;

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private static Music bgm;


    private void initializeTextures() {

        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0);
        camera.update();


        batch = new SpriteBatch();
        world = new World(new Vector2(0, -98f), true);
        catapult = new Sprite(new Texture("cat.png"));
        background = new Sprite(new Texture("background.png"));
        green = new Bird("green", 200, 125, 45f , world);
        blue = new Bird("blue", 125, 125, 45f , world);
        red =new Bird("red", 50, 125, 45f , world);
        bgm = FirstLevel.getBgm();


        pig1 =  new Pig("small",45,825,125,world);
        pig2 =  new Pig("small",45,975,125,world);
        pig3 =  new Pig("small",45,1125,125,world);


        pause = new Sprite(new Texture("pause_button.png"));


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

        //creating wood blocks

        Block b1 = new Block("vertical",
            750 ,150+ 37.5f,1f,world);

        Block b2 = new Block("horizontal",
            825 ,150+b1.getBlockSprite().getHeight()-27,1f,world);

        Block b3 = new Block("vertical",
            750 +b2.getBlockSprite().getWidth(),   150+37.5f, 1f, world);

        Block b4 = new Block("horizontal",
            b3.getX()+75,   150+b1.getBlockSprite().getHeight()-27, 1f, world);

        Block b5 = new Block("vertical",
            b3.getX()+ b2.getBlockSprite().getWidth() -5,   150+37.5f, 1f, world);

        Block b6 = new Block("horizontal",
            b4.getX()+150,   150+b1.getBlockSprite().getHeight()-27, 1f, world);

        Block b7 = new Block("vertical",
            b5.getX()+b2.getBlockSprite().getWidth(),   150+37.5f, 1f, world);

        Block b8 = new Block("vertical",
          (b1.getX()+ b3.getX())/2,   150+50+b1.getBlockSprite().getHeight(), 1f, world);

        Block b9 = new Block("horizontal",
           b8.getX()+73, 150+2*b1.getBlockSprite().getHeight()-24, 1f, world);

        Block b10 = new Block("vertical",
            b8.getX()+b2.getBlockSprite().getWidth()-5f,   150+50+b1.getBlockSprite().getHeight(), 1f, world);

        Block b11 = new Block("horizontal",
           b9.getX()+150 ,  b9.getY(), 1f, world);

        Block b12 = new Block("vertical",
            b10.getX()+b2.getBlockSprite().getWidth()-5 ,  b10.getY(), 1f, world);




        woodBlocks.add(b1);
        woodBlocks.add(b2);
        woodBlocks.add(b3);
        woodBlocks.add(b4);
        woodBlocks.add(b5);
        woodBlocks.add(b6);
        woodBlocks.add(b7);
        woodBlocks.add(b8);
        woodBlocks.add(b9);
        woodBlocks.add(b10);
        woodBlocks.add(b11);
        woodBlocks.add(b12);


        pig4 =  new Pig("small",45,900,125+37.5f + b1.getBlockSprite().getHeight(),world);
        pig5 =  new Pig("small",45,1050,125+37.5f+b1.getBlockSprite().getHeight(),world);






    }

    @Override
    public void show() {
        initializeTextures();
    }

    @Override
    public void render(float delta) {
        clearScreen();

        // Step the Box2D simulation
        world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        handleInput(pause.getBoundingRectangle());

        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        debugRenderer.render(world, camera.combined);



        batch.begin();
        // Draw all elements
        drawBackground();

        drawWoodBlocks();
        drawCatapult();
        drawPause();

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

        green.getBirdSprite().getTexture().dispose();
        red.getBirdSprite().getTexture().dispose();
        blue.getBirdSprite().getTexture().dispose();
        catapult.getTexture().dispose();

        pig1.getPigSprite().getTexture().dispose();
        pig2.getPigSprite().getTexture().dispose();
        pig3.getPigSprite().getTexture().dispose();
        pig4.getPigSprite().getTexture().dispose();
        pig5.getPigSprite().getTexture().dispose();

        pause.getTexture().dispose();

        for (Block block : woodBlocks) {
            block.dispose();
        }
        woodBlocks.clear();
    }
}

