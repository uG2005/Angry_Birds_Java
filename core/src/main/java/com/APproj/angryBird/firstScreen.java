package com.APproj.angryBird;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class firstScreen implements Screen {
    private final MainLauncher game;
    private Texture texture, settings;
    private static Music music;
    private Texture play;
    private Rectangle rect, rect1;
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer shapeRenderer;

    // Define the world size for consistency
    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    public static Music getMusic() {
        return music;
    }

    public firstScreen(MainLauncher game) {
        this.game = game;

        this.texture = new Texture("splash.png");
        this.settings = new Texture("settings.png");
        this.play = new Texture("playBUTTON.png");

        // Initialize the music
        this.music = Gdx.audio.newMusic(Gdx.files.internal("sounds\\song.mp3"));
        this.music.setLooping(true);
        this.music.play();

        // Initialize the camera and viewport for responsive design
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        // Center the camera
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();

        // Initialize the rectangle for the play button
        rect = new Rectangle();
        rect.x = WORLD_WIDTH / 2 - 50;
        rect.y = 20;
        rect.width = 100;
        rect.height = 100;



        //initialize the rect for settings button

        rect1 = new Rectangle();
        rect1.x = 1150;
        rect1.y = 620;
        rect1.width = 69;
        rect1.height = 69;


        // Initialize the ShapeRenderer
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        // No additional setup needed for now
    }

    @Override
    public void render(float delta) {
        // Clear the screen before drawing on it
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Draw the background and play button using the game's batch
        game.batch.begin();
        game.batch.draw(texture, 0, 0, WORLD_WIDTH, WORLD_HEIGHT);
        game.batch.draw(settings, rect1.x,rect1.y,rect1.width,rect1.height);
        game.batch.draw(play, WORLD_WIDTH/2 -100, rect.y, 200, rect.height);
        game.batch.end();

        // Handle touch input for the play button
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen coordinates to world coordinates

            if (rect.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new gamescreen(game));
            }

            if(rect1.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new settingScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Update the viewport to handle window resizing
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Pause logic if needed
    }

    @Override
    public void resume() {
        // Resume logic if needed
    }

    @Override
    public void hide() {
        // Hide logic if needed
    }

    @Override
    public void dispose() {
        // Dispose of resources to free memory
        texture.dispose();
        play.dispose();
        music.dispose();
        shapeRenderer.dispose();
    }
}
