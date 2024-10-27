package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LossScreen implements Screen {

    private MainLauncher game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private ImageButton replayButton;

    public LossScreen(MainLauncher game) {
        this.game = game;
        this.batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture(Gdx.files.internal("loss_background.png"));  // Placeholder
        Texture replayTexture = new Texture(Gdx.files.internal("replay_button.png"));  // Placeholder

        // Create button
        replayButton = new ImageButton(new TextureRegionDrawable(replayTexture));

        // Add listener to replay button
        replayButton.addListener(event -> {
            // Logic to replay
            game.setScreen(new firstLevel(game));
            return true;
        });
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(800, 480), batch);
        Gdx.input.setInputProcessor(stage);

        replayButton.setSize(replayButton.getWidth() * 0.3f, replayButton.getHeight() * 0.3f);
        float screenWidth = stage.getViewport().getWorldWidth();
        float buttonX = (screenWidth - replayButton.getWidth()) / 2;
        float buttonY = 50f;

        replayButton.setPosition(buttonX, buttonY);
        
        stage.addActor(replayButton);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, 800, 480);
        batch.end();

        // Update and draw the stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        backgroundTexture.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}
