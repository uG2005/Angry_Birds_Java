package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PauseScreen implements Screen {

    private final MainLauncher game; // Reference to the main game class
    private final SpriteBatch batch;
    private final FitViewport viewport;
    private final Stage stage;

    private final Texture backgroundTexture;
    private final ImageButton resumeButton;
    private final ImageButton exitButton;

    public PauseScreen(MainLauncher game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.viewport = new FitViewport(1280, 720); // Adjust this based on your game

        // Initialize stage and input processor
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Load textures for the background and buttons
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));

        Texture resumeTexture = new Texture(Gdx.files.internal("resume_button.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("exit_button.png"));

        // Create buttons
        resumeButton = new ImageButton(new TextureRegionDrawable(resumeTexture));
        exitButton = new ImageButton(new TextureRegionDrawable(exitTexture));

        // Set button sizes to 0.25 of their current size
        float resumeButtonWidth = resumeButton.getWidth() * 0.25f;
        float resumeButtonHeight = resumeButton.getHeight() * 0.25f;
        resumeButton.setSize(resumeButtonWidth, resumeButtonHeight);

        float exitButtonWidth = exitButton.getWidth() * 0.25f;
        float exitButtonHeight = exitButton.getHeight() * 0.25f;
        exitButton.setSize(exitButtonWidth, exitButtonHeight);

        // Set button positions (centered)
        resumeButton.setPosition(((viewport.getWorldWidth() - resumeButton.getWidth())/ 2) -125, (viewport.getWorldHeight() - resumeButton.getHeight() )/2);
        exitButton.setPosition((viewport.getWorldWidth() - exitButton.getWidth()) / 2 + 125 , (viewport.getWorldHeight()  - exitButton.getHeight()) /2 );

        // Add buttons to stage
        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        // Add listeners for buttons
        addListeners();
    }


    private void addListeners() {
        // Listener for Resume button
        resumeButton.addListener(event -> {
            if (resumeButton.isPressed()) {
                // Switch back to the game screen
                game.setScreen(new firstLevel(game)); // Replace firstLevel with your current game screen class
            }
            return true;
        });

        // Listener for Exit button
        exitButton.addListener(event -> {
            if (exitButton.isPressed()) {
                // Switch to the main menu
                game.setScreen(new firstScreen(game)); // Replace firstScreen with your main menu screen class
            }
            return true;
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Draw background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        batch.end();

        // Draw stage (buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        batch.dispose();
        backgroundTexture.dispose();
        stage.dispose();
    }
}

