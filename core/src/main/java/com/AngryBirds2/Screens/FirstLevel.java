package com.AngryBirds2.Screens;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FirstLevel implements Screen {
    private SpriteBatch batch;
    private Sprite background, ground, catapult, green, blue, wood1, wood2, wood3, pig1;
    private OrthographicCamera camera;
    private Viewport viewport;
    private static Music bgm;
    private Sprite pause;
    private Rectangle pauseButtonBounds;

    // Virtual world dimensions
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;


    private void initializeTextures() {
        batch = new SpriteBatch();

        background = new Sprite(new Texture("background.png"));
        ground = new Sprite(new Texture("ground.png"));
        green = new Sprite(new Texture("green.png"));
        blue = new Sprite(new Texture("blue.png"));
        catapult = new Sprite(new Texture("cat.png"));
        wood1 = new Sprite(new Texture("horiz_wood.png"));
        wood2 = new Sprite(new Texture("horiz_wood.png"));
        wood3 = new Sprite(new Texture("horiz_wood.png"));
        pig1 = new Sprite(new Texture("pig1.png"));
        pause = new Sprite(new Texture("pause_button.png"));
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds\\game.wav"));
        bgm.setLooping(true);
        bgm.play();
        // Pause button bounds (reduced size)
        pauseButtonBounds = new Rectangle(1170, 670, 0.10f * pause.getWidth(),
            0.10f * pause.getHeight());


    }

//    private void initializeCameraAndViewport() {
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//        viewport.apply();
//        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
//    }

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
        //initializeCameraAndViewport();
    }

    @Override
    public void render(float delta) {
        clearScreen();
//        updateCamera();

        handleInput(pause.getBoundingRectangle()); // Check for pause button click/touch

        if (Gdx.input.isTouched()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
//                Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
//                clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system
//
//                if (close.getBoundingRectangle().contains(clickPosition)) {
//                    System.out.println("Sprite clicked!");

                ((Game) Gdx.app.getApplicationListener()).setScreen(new VictoryScreen());
            }
        }
//        }
        batch.begin();
        drawBackground();
        drawGround();
        drawBirds();
        drawWoodBlocks();
        drawpigs();
        drawcatapult();
        drawpause();
        batch.end();
    }


    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void updateCamera() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    private void handleInput(Rectangle r1) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system

            if (r1.contains(clickPosition)) {
                System.out.println("Sprite clicked!");
                // Add your custom code here, e.g., change sprite color, move it, etc.
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen());
            }
        }
    }


    private void drawBackground() {
        background.draw(batch);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void drawGround() {
        ground.draw(batch);
        ground.setSize(Gdx.graphics.getWidth(), 80);
    }

    private void drawBirds() {
        //handling the green bird
        green.draw(batch);
        green.setSize(55, 55);
        green.setPosition(50,60);


        //handling the blue bird
        blue.draw(batch);
        blue.setSize(55, 55);
        blue.setPosition(125,60);
    }

    private void drawWoodBlocks() {
        float woodRotation = 90;

        //wood1 settings
        wood1.draw(batch);
        wood1.setSize(150,15);
        wood1.setRotation(woodRotation);
        wood1.setPosition(650,134);

        //wood2 settings
        wood2.draw(batch);
        wood2.setSize(150,15);
        wood2.setRotation(woodRotation);
        wood2.setPosition(650+150-10,134);

        //wood 3 settings
        wood3.draw(batch);
        wood3.setSize(150,15);
        wood3.setPosition(650+75,134+75);

    }

    private void drawpigs() {
        pig1.draw(batch);
        pig1.setSize(62,62);
        pig1.setPosition(765, 57);
    }

    private void drawcatapult(){
        //handling the catapult
        catapult.draw(batch);
        catapult.setSize(350,200);
        catapult.setPosition(100,43);

    }

    private void drawpause(){
        //handling the pause button
        pause.draw(batch);
        pause.setSize(50,50);
        pause.setPosition(Gdx.graphics.getWidth()-75,Gdx.graphics.getHeight()-50);
    }
    @Override
    public void resize(int width, int height){
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
        ground.getTexture().dispose();
        green.getTexture().dispose();
        blue.getTexture().dispose();
        catapult.getTexture().dispose();
        wood1.getTexture().dispose();
        wood2.getTexture().dispose();
        wood3.getTexture().dispose();
        pig1.getTexture().dispose();
        pause.getTexture().dispose();
        bgm.dispose();
    }

    public static Music getBgm(){return bgm;}
}
