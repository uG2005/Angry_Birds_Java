package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class gamescreen implements Screen {
    private final MainLauncher game;
    private Texture backgroundTexture;
    private SpriteBatch spriteBatch;
    private FitViewport viewport;
    private Music music;
    private OrthographicCamera camera;
    private button back;
    private button level1;
    private button level2;
    private button level3;
    private button settings;
    private button volumeButton; // Button for volume control
    private boolean isMusicPlaying = true; // Track if music is playing
    private Texture volumeOnTexture; // Texture for volume on
    private Texture volumeOffTexture;

    public gamescreen(MainLauncher game) {
        this.game = game;
        spriteBatch = new SpriteBatch();

        // Initialize the camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera); // Adjusted world size
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();

        backgroundTexture = new Texture("background.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds\\song.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);

        // Load button textures
        Texture backTexture = new Texture(Gdx.files.internal("backy.png"));
        Texture level1Texture = new Texture(Gdx.files.internal("level1.png"));
        Texture level2Texture = new Texture(Gdx.files.internal("level2.png"));
        Texture level3Texture = new Texture(Gdx.files.internal("level3.png"));
        Texture settingsTexture = new Texture(Gdx.files.internal("green.png"));

        // Load volume textures
        volumeOnTexture = new Texture(Gdx.files.internal("pig1.png"));
        volumeOffTexture = new Texture(Gdx.files.internal("pig2.png"));

        // Button dimensions
        float buttonWidth = 100;
        float buttonHeight = 100;

        // Create buttons
        back = new button(backTexture, 0, viewport.getWorldHeight() - buttonHeight, buttonWidth, buttonHeight, viewport); // Back button at top left
        float spacing = 50; // Set the spacing between buttons

        // Spread out level buttons
        level1 = new button(level1Texture, (viewport.getWorldWidth() - buttonWidth * 3 - spacing * 2) / 2, viewport.getWorldHeight() / 2, buttonWidth, buttonHeight, viewport);
        level2 = new button(level2Texture, level1.getX() + buttonWidth + spacing, viewport.getWorldHeight() / 2, buttonWidth, buttonHeight, viewport);
        level3 = new button(level3Texture, level2.getX() + buttonWidth + spacing, viewport.getWorldHeight() / 2, buttonWidth, buttonHeight, viewport);
        settings = new button(settingsTexture, viewport.getWorldWidth() - buttonWidth, 0, buttonWidth, buttonHeight, viewport);

        // Volume button position
        volumeButton = new button(volumeOnTexture, viewport.getWorldWidth() - buttonWidth, settings.getY() + settings.getHeight() + 10, buttonWidth, buttonHeight, viewport); // Above settings



    }

    @Override
    public void show() {
        System.out.println("Game Screen is now active.");
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight()); // Draw background
        back.render(spriteBatch);

        // Render level buttons
        level1.render(spriteBatch);
        level2.render(spriteBatch);
        level3.render(spriteBatch);

        // Render settings button
        settings.render(spriteBatch);

        // Render volume button with appropriate texture based on music state
        Texture currentVolumeTexture = isMusicPlaying ? volumeOnTexture : volumeOffTexture;
        spriteBatch.draw(currentVolumeTexture, volumeButton.getX(), volumeButton.getY(), volumeButton.getWidth(), volumeButton.getHeight());

        spriteBatch.end();
        handleInput();
    }

    private void handleInput() {
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos); // Convert screen coordinates to world coordinates

        if (back.isTouched(touchPos) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            music.stop();
            game.setScreen(new firstScreen(game)); // Switch to first screen
            System.out.println("Switched back to Home Screen.");
        }
        if (level1.isTouched(touchPos) && Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            game.setScreen(new firstLevel(game)); // Switch to first level
            System.out.println("Level 1 button pressed, switching to Level 1.");
        }

        if (level2.isTouched(touchPos)) {
            System.out.println("Level 2 button pressed.");
        }

        if (level3.isTouched(touchPos)) {
            System.out.println("Level 3 button pressed.");
            // Switch to level 3 screen (yet to be defined)
        }
        if (settings.isTouched(touchPos)) {
            System.out.println("Settings button pressed, switching to Settings Screen.");
        }
        if (volumeButton.isTouched(touchPos)) {
            toggleMusic(); // Toggle the music on or off
        }
    }

    private void toggleMusic() {
        isMusicPlaying = !isMusicPlaying; // Toggle music state
        if (isMusicPlaying) {
            music.play(); // Play music
            System.out.println("Music turned ON.");
        } else {
            music.stop(); // Stop music
            System.out.println("Music turned OFF.");
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        music.stop();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        backgroundTexture.dispose();
        music.dispose();
        back.dispose();
        level1.dispose();
        level2.dispose();
        level3.dispose();
        settings.dispose();
        volumeButton.dispose();
        volumeOnTexture.dispose();
        volumeOffTexture.dispose();
    }
}

