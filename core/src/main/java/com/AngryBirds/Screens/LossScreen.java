package com.AngryBirds.Screens;

import com.AngryBirds.GameData;
import com.badlogic.gdx.Game;
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

    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private ImageButton replayButton;

    public LossScreen(int currentLevel) {
    }


    @Override
    public void show() {

        this.batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture(Gdx.files.internal("loss_background.png"));  // Placeholder
        Texture replayTexture = new Texture(Gdx.files.internal("replay_button.png"));  // Placeholder

        // Create button
        replayButton = new ImageButton(new TextureRegionDrawable(replayTexture));

        // Add listener to replay button
        replayButton.addListener(event -> {
            restartCurrentLevel();
            return true;
        });
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Gdx.input.setInputProcessor(stage);

        replayButton.setSize(replayButton.getWidth() * 0.3f, replayButton.getHeight() * 0.3f);
        float screenWidth = stage.getViewport().getWorldWidth();
        float buttonX = (screenWidth - replayButton.getWidth()) / 2;
        float buttonY = 50f;

        replayButton.setPosition(buttonX, buttonY);

        stage.addActor(replayButton);
    }


    private void restartCurrentLevel() {
        int currentLevel = GameData.getCurrentLevel();
        switch (currentLevel) {
            case 1:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstLevel());
                break;
            case 2:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SecondLevel());
                break;
            case 3:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ThirdLevel());
                break;
            default:
                System.out.println("Invalid level!");
                break;
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the background
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
