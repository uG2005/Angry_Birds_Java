package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class firstLevel implements Screen {
    private final MainLauncher game;
    private Texture background, ground, catapult, green, blue, wood1, wood2, wood3, pig1;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Music bgm;
    private Texture pause;
    private Rectangle pauseButtonBounds;

    // Virtual world dimensions
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    public firstLevel(MainLauncher game) {
        this.game = game;
        initializeTextures();
        initializeCameraAndViewport();
        initializeMusic();
    }

    private void initializeTextures() {
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        green = new Texture("green.png");
        blue = new Texture("blue.png");
        catapult = new Texture("cat.png");
        wood1 = new Texture("horiz_wood.png");
        wood2 = new Texture("horiz_wood.png");
        wood3 = new Texture("horiz_wood.png");
        pig1 = new Texture("pig1.png");
        pause = new Texture("pause_button.png");

        // Pause button bounds (reduced size)
        pauseButtonBounds = new Rectangle(1170, 670, 0.10f * pause.getWidth(), 0.10f * pause.getHeight());
    }

    private void initializeCameraAndViewport() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
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
    }

    @Override
    public void render(float delta) {
        clearScreen();
        updateCamera();

        handleInput(); // Check for pause button click/touch

        game.batch.begin();
        drawBackground();
        drawGround();
        drawBirds();
        drawWoodBlocks();
        game.batch.draw(catapult, 69, 55, 0.60f * catapult.getWidth(), 0.60f * catapult.getHeight());
        game.batch.draw(pig1, 825, 74);
        game.batch.draw(pause, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height); // Draw pause button
        game.batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void updateCamera() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
    }

    private void handleInput() {
        // Handle mouse click for desktop input
        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();
            mouseY = Gdx.graphics.getHeight() - mouseY; // Invert Y for correct positioning

            if (pauseButtonBounds.contains(mouseX, mouseY)) {
                game.setScreen(new PauseScreen(game));
            }
        }

        // Handle touch input for mobile
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen coordinates to world coordinates

            if (pauseButtonBounds.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new PauseScreen(game));
            }
        }
    }

    private void drawBackground() {
        game.batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }

    private void drawGround() {
        game.batch.draw(ground, 0, 0, WORLD_WIDTH, 100);
    }

    private void drawBirds() {
        game.batch.draw(green, 175, 75);
        game.batch.draw(blue, 120, 74);
    }

    private void drawWoodBlocks() {
        float woodScale = 0.75f;
        float woodRotation = 90;

        game.batch.draw(wood1, 700, 130, wood1.getWidth() / 2, wood1.getHeight() / 2,
            wood1.getWidth(), wood1.getHeight(), woodScale, woodScale,
            woodRotation, 0, 0, wood1.getWidth(), wood1.getHeight(),
            false, false);

        game.batch.draw(wood2, 848, 130, wood2.getWidth() / 2, wood2.getHeight() / 2,
            wood2.getWidth(), wood2.getHeight(), woodScale, woodScale,
            woodRotation, 0, 0, wood2.getWidth(), wood2.getHeight(),
            false, false);

        game.batch.draw(wood3, 774, 2 * wood3.getHeight() + 157, wood3.getWidth() / 2,
            wood3.getHeight() / 2, wood3.getWidth(), wood3.getHeight(),
            1, woodScale, 0, 0, 0, wood3.getWidth(),
            wood3.getHeight(), false, false);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        background.dispose();
        ground.dispose();
        green.dispose();
        blue.dispose();
        catapult.dispose();
        wood1.dispose();
        wood2.dispose();
        wood3.dispose();
        pig1.dispose();
        pause.dispose();
        bgm.dispose();
    }
}
