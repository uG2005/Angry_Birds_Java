package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class firstLevel implements Screen {
    private final MainLauncher game;
    private Texture background, ground, catapult, green,blue, wood1, wood2, wood3, pig1;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Music bgm;

    // Virtual world dimensions
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    public firstLevel(MainLauncher game) {
        this.game = game;
        initializeTextures();
        initializeCameraAndViewport();
    }

    private void initializeTextures() {
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        green = new Texture("green.png");
        catapult = new Texture("cat.png");
        blue = new Texture("blue.png");
        wood1 = new Texture("horiz_wood.png");
        wood2 = new Texture("horiz_wood.png");
        wood3 = new Texture("horiz_wood.png");
        pig1 = new Texture("pig1.png");
        bgm = Gdx.audio.newMusic(Gdx.files.internal("sounds\\game.wav"));
        bgm.setLooping(true);
        bgm.setVolume(0.75f);
        bgm.play();
    }

    private void initializeCameraAndViewport() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
    }

    @Override
    public void show() {
        // No specific actions needed on show
    }

    @Override
    public void render(float delta) {
        clearScreen();
        updateCamera();

        game.batch.begin();
        drawBackground();
        drawGround();
        drawBird(green,175,75);
        drawBird(blue,120,74);
        game.batch.draw(catapult, 69, 55,0.60f*catapult.getWidth(),0.60f*catapult.getHeight());
        drawWoodBlocks();
        game.batch.draw(pig1,790,74);
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

    private void drawBackground() {
        game.batch.draw(background, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }

    private void drawGround() {
        game.batch.draw(ground, 0, 0, WORLD_WIDTH, 100);
    }

    private void drawBird(Texture bird, int xpos, int ypos) {
        game.batch.draw(bird, xpos, ypos);
    }

    private void drawWoodBlocks() {
        // Drawing wood blocks with rotation and scaling
        float woodScale = 0.5f;
        float woodRotation = 90;

        // Draw wood1 rotated vertically
        game.batch.draw(wood1, 700, 110, wood1.getWidth() / 2, wood1.getHeight() / 2,
            wood1.getWidth(), wood1.getHeight(), woodScale, woodScale,
            woodRotation, 0, 0, wood1.getWidth(), wood1.getHeight(),
            false, false);

        // Draw wood2 rotated vertically
        game.batch.draw(wood2, 774, 110, wood2.getWidth() / 2, wood2.getHeight() / 2,
            wood2.getWidth(), wood2.getHeight(), woodScale, woodScale,
            woodRotation, 0, 0, wood2.getWidth(), wood2.getHeight(),
            false, false);

        // Draw wood3 without rotation
        game.batch.draw(wood3, 737, 2 * wood3.getHeight() + 123, wood3.getWidth() / 2,
            wood3.getHeight() / 2, wood3.getWidth(), wood3.getHeight(),
            woodScale, woodScale, 0, 0, 0, wood3.getWidth(),
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
        // Dispose of textures to free up resources
        background.dispose();
        ground.dispose();
        green.dispose();
        wood1.dispose();
        wood2.dispose();
        wood3.dispose();
    }
}
