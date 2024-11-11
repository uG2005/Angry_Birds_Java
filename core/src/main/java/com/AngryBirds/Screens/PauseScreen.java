package com.AngryBirds.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen implements Screen {

    private final SpriteBatch batch;
    private final Stage stage;

    private final Sprite backgroundSprite;
    private final ImageButton resumeButton;
    private final ImageButton exitButton;

    public PauseScreen() {

        this.batch = new SpriteBatch();

        // Initialize stage and input processor
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Load background sprite
        backgroundSprite = new Sprite(new Texture(Gdx.files.internal("background.png")));
        backgroundSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load textures and create sprites for buttons
        Sprite resumeSprite = new Sprite(new Texture(Gdx.files.internal("resume_button.png")));
        Sprite exitSprite = new Sprite(new Texture(Gdx.files.internal("exit_button.png")));

        // Set button sizes
        resumeSprite.setSize(resumeSprite.getWidth() * 0.25f, resumeSprite.getHeight() * 0.25f);
        exitSprite.setSize(exitSprite.getWidth() * 0.25f, exitSprite.getHeight() * 0.25f);

        // Create ImageButtons with SpriteDrawables
        resumeButton = new ImageButton(new SpriteDrawable(resumeSprite));
        exitButton = new ImageButton(new SpriteDrawable(exitSprite));

        // Set button positions (centered)
        resumeButton.setPosition((Gdx.graphics.getWidth() - resumeButton.getWidth()) / 2 - 125, Gdx.graphics.getHeight() / 2 - resumeButton.getHeight() / 2);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth()) / 2 + 125, Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2);

        // Add buttons to stage
        stage.addActor(resumeButton);
        stage.addActor(exitButton);

        // Add listeners for buttons using ClickListener
        addListeners();
    }

    private void addListeners() {
        // Listener for Resume button
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Switch back to the game screen
                ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstLevel()); // Replace with your game screen class
                //(Game) Gdx.app.getApplicationListener() is equivalent to game
            }
        });

        // Listener for Exit button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(com.badlogic.gdx.scenes.scene2d.InputEvent event, float x, float y) {
                // Switch to the main menu
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen()); // Replace with your main menu screen class
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Draw background
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        // Draw stage (buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // No need to handle resizing, since the stage uses full screen
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
        backgroundSprite.getTexture().dispose();
        stage.dispose();
    }
}
