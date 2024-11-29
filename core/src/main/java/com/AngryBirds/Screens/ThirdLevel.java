package com.AngryBirds.Screens;

import com.AngryBirds.GameData;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.AngryBirds.Screens.Bird;

import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.Gdx.input;

public class ThirdLevel implements Screen {

    private float timeElapsed = 0f;
    private int score;
    private BitmapFont font;

    public static final short CATEGORY_BIRD = 0x0001;
    public static final short CATEGORY_CATAPULT = 0x0002;
    public static final short CATEGORY_PIG = 0x0004;
    public static final short CATEGORY_BLOCK = 0x0008;
    public static final short CATEGORY_GROUND = 0x0010;

    private Vector3 touchPosition = new Vector3();
    private boolean isDragging = false;


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
    private Bird red,blue, green,red2;
    private Pig pig1,pig2 ;
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
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Body c1 = contact.getFixtureA().getBody();
                Body c2 = contact.getFixtureB().getBody();
                if(c2.getUserData() instanceof  Bird){
                    if(c1.getUserData() instanceof Pig ){
                        ((Pig) c1.getUserData()).destroyed = true;
                        ((Bird) c2.getUserData()).destroyed = true;

                    }
                    if(c1.getUserData() instanceof Block ){
                        ((Block) c1.getUserData()).destroyed = true;

                    }


                }


                if(c1.getUserData() instanceof Bird){
                    if(c2.getUserData() instanceof Pig ){
                        ((Pig) c2.getUserData()).destroyed = true;
                        ((Bird) c1.getUserData()).destroyed = true;
                    }
                    if(c2.getUserData() instanceof Block ){
                        ((Block) c2.getUserData()).destroyed = true;

                    }

                }


                if(c1.getUserData() instanceof Pig){
                    if(((Pig) c1.getUserData()).initiallyonblock){
                        if(c2 == ground){
                            ((Pig) c1.getUserData()).destroyed = true;
                        }
                    }
                }

                if(c2.getUserData() instanceof Pig){
                    if(((Pig) c2.getUserData()).initiallyonblock){
                        if(c1 == ground){
                            ((Pig) c2.getUserData()).destroyed = true;
                        }
                    }
                }




            }



            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        background = new Sprite(new Texture("background.png"));
        catapult = new Sprite(new Texture("cat.png"));
        red = new Bird("red", 25, 125, 45f , world,false);
        blue = new Bird("blue", 175, 125, 45f , world,false);
        red2 = new Bird("red", 175, 125, 45f , world,false);
        green = new Bird("green", 100, 125, 45f , world,false);
        pig1 = new Pig("small",45,525,125,world,false);
        pig2 = new Pig("small",45, 600, 350,world,true);
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
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0f;
        fixtureDef.density = 5f;
        fixtureDef.filter.categoryBits = CATEGORY_GROUND;
        fixtureDef.filter.maskBits = (short)(CATEGORY_BIRD | CATEGORY_PIG | CATEGORY_BLOCK);
        // Create the ground body and fixture
        ground = world.createBody(bodydef);
        ground.createFixture(fixtureDef);

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
        catapultFixtureDef.filter.categoryBits = CATEGORY_CATAPULT;
        catapultFixtureDef.filter.maskBits = (short)(CATEGORY_PIG | CATEGORY_BLOCK | CATEGORY_GROUND |CATEGORY_PIG);

        catapultBody = world.createBody(catapultBodyDef);
        catapultBody.createFixture(catapultFixtureDef);

        // Dispose the shape after creating the fixture
        catapultShape.dispose();



        ///creating the wood blocks
        woodBlocks = new ArrayList<>();

        // Creating wood blocks
        Block b2 = new Block("wood","vertical", 455,  150+37.5f, 1f, world);

        Block b1 = new Block("wood","horizontal",
            450+75,   150+b2.getBlockSprite().getHeight()-27, 1f, world);


        Block b3 = new Block("wood","vertical", 597,  150+37.5f, 1f, world);

        Block b4 = new Block("wood","vertical",b3.getX()+b1.getBlockSprite().getWidth(),b3.getY(),1f,world);
        Block b5 = new Block("wood","horizontal",b3.getX()+b1.getBlockSprite().getWidth()/2,b1.getY(),1f,world);

        Block b6 = new Block("glass","vertical", (b2.getX()+ b3.getX())/2,150+50+b2.getBlockSprite().getHeight(),1f,world);
        Block b8 = new Block("glass","horizontal", b6.getX()+75,150+2*b2.getBlockSprite().getHeight()-24,1f,world);
        Block b7 = new Block("glass","vertical", b6.getX()+150,150+50+b2.getBlockSprite().getHeight(),1f,world);




        woodBlocks.add(b1); // wood1
        woodBlocks.add(b2); // wood2
        woodBlocks.add(b3); // wood3
        woodBlocks.add(b4);
        woodBlocks.add(b5);
        woodBlocks.add(b6);
        woodBlocks.add(b7);
        woodBlocks.add(b8);

////       placeBirdOnCatapult(blue);



    }

    private void initializeMusic() {
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds/game.wav"));
        bgm.setLooping(true);
        bgm.setVolume(0.75f);
        bgm.play();
    }
    @Override
    public void show() {
        score = GameData.getScore();
        font = new BitmapFont();
        font.getData().setScale(2);
        // No specific actions needed on show
        initializeTextures();
        initializeMusic();
    }
    @Override
    public void render(float delta) {
        clearScreen();

        // Step the Box2D simulation
        world.step(TIMESTEP, VELOCITYITERATIONS*10, POSITIONITERATIONS*10);

        camera.update();
        batch.setProjectionMatrix(camera.combined);


        Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
        debugRenderer.render(world, camera.combined);


        batch.begin();

        // Draw all elements
        drawBackground();
        drawGround();


        drawcatapult();
        drawpause();
        font.draw(batch, "Score: " + score, 50, graphics.getHeight() - 50);

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
            else if(b.getUserData() instanceof Block){
                Block block = (Block) b.getUserData();
                Sprite sprite = block.getBlockSprite();
                sprite.setPosition(
                    b.getPosition().x - sprite.getWidth() / 2,
                    b.getPosition().y - sprite.getHeight() / 2
                );
                sprite.setRotation(b.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        a1.clear();



        batch.end();




        handleInput(pause.getBoundingRectangle());

        if(!blue.oncatapult){
            placeBirdOnCatapult(blue);
        }
        InputHandler(blue);

        // Handle input for red bird
        if(blue.isLaunched && !red2.oncatapult){
            placeBirdOnCatapult(red2);
        }

        if(red2.oncatapult){
            InputHandler(red2);
        }

        if(red2.isLaunched && !green.oncatapult){
            placeBirdOnCatapult(green);
        }

        if(green.oncatapult){
            InputHandler(green);
        }

        if(green.isLaunched && !red.oncatapult){
            placeBirdOnCatapult(red);
        }

        if(red.oncatapult){
            InputHandler(red);
        }


        destroy();
        timeElapsed += delta;


        if (timeElapsed > 1f && pig1.isdestroyed() && pig2.isdestroyed()) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new VictoryScreen(GameData.getCurrentLevel()));
        } else if (blue.isLaunched() && green.isLaunched() && red.isLaunched() && red2.isLaunched() && (!pig1.isdestroyed() || !pig2.isdestroyed())) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LossScreen(GameData.getCurrentLevel()));
        }

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
        bird.oncatapult = true;
        bird.isLaunched = false;
        bird.isDragging = false;

        Vector2 anchorPos = bird.getSlingshotAnchor();
        bird.getBody().setTransform(anchorPos.x, anchorPos.y, 0);
        bird.getBody().setActive(false);
        bird.rest();
    }
    private void InputHandler(Bird bird) {
        // Remove the oncatapult check that was preventing launch
        if (bird.isLaunched) return;  // Only prevent handling if the bird is already launched

        // Convert touch position to world coordinates
        touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPosition);
        Vector2 worldTouch = new Vector2(touchPosition.x, touchPosition.y);

        if (Gdx.input.justTouched()) {
            // Check if touch is near the bird
            Vector2 birdPos = bird.getBody().getPosition();
            float touchRadius = 50f; // Adjust this value as needed

            if (worldTouch.dst(birdPos) <= touchRadius && !bird.isLaunched()) {
                bird.startDrag();
            }
        }

        if (bird.isDragging() && Gdx.input.isTouched()) {
            bird.updateDrag(worldTouch);
        }

        if (bird.isDragging() && !Gdx.input.isTouched()) {
            bird.launch();
            //    bird.oncatapult = false;  // Mark the bird as no longer on the catapult
        }
    }
    public void destroy(){
        world.getBodies(a1);

        for(Body body: a1){

            if(body.getUserData() instanceof  Bird){
                if(((Bird) body.getUserData()).destroyed){
                    world.destroyBody(body);
                }
            }
            if(body.getUserData() instanceof  Pig){
                if(((Pig) body.getUserData()).destroyed){
                    world.destroyBody(body);
                }
            }
            if(body.getUserData() instanceof Block){
                if(((Block) body.getUserData()).destroyed){
                    world.destroyBody(body);
                }
            }
        }

        a1.clear();
    }


}
