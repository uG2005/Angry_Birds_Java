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

public class VictoryScreen implements Screen {

    //    private MainLauncher game;
    private Stage stage;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private ImageButton replayButton, nextLevelButton;

    private int currentLevel;

    public VictoryScreen(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();

        // Load textures
        backgroundTexture = new Texture(Gdx.files.internal("victory_background.jpg"));
        Texture replayTexture = new Texture(Gdx.files.internal("replay_button.png"));
        Texture nextLevelTexture = new Texture(Gdx.files.internal("next_level_button.png"));

        // Create buttons
        replayButton = new ImageButton(new TextureRegionDrawable(replayTexture));
        nextLevelButton = new ImageButton(new TextureRegionDrawable(nextLevelTexture));

        // Add listeners to buttonszz
        replayButton.addListener(event -> {
            restartCurrentLevel();
            return true;
        });

        nextLevelButton.addListener(event -> {
            loadNextLevel();
            return true;
        });

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Gdx.input.setInputProcessor(stage);

        // Resize buttons
        replayButton.setSize(replayButton.getWidth() * 0.3f, replayButton.getHeight() * 0.3f);
        nextLevelButton.setSize(nextLevelButton.getWidth() * 0.27f, nextLevelButton.getHeight() * 0.27f);

        // Position buttons
        float spacing = 20f;
        float screenWidth = stage.getViewport().getWorldWidth();
        float starsY = 100f;

        float totalWidth = replayButton.getWidth() + nextLevelButton.getWidth() + spacing;
        float startX = (screenWidth - totalWidth) / 2;

        replayButton.setPosition(startX, starsY - replayButton.getHeight() - 10);
        nextLevelButton.setPosition(replayButton.getX() + replayButton.getWidth() + spacing, starsY - nextLevelButton.getHeight() - 10);

        // Add buttons to the stage
        stage.addActor(replayButton);
        stage.addActor(nextLevelButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

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
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    private void loadNextLevel() {
        int nextLevel = (GameData.getCurrentLevel()) + 1;
        GameData.setCurrentLevel(nextLevel);
        switch (nextLevel) {
            case 2:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SecondLevel());
                break;
            case 3:
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ThirdLevel());
                break;
            default:
                System.out.println("No more levels available!");
                break;
        }
    }


    private void restartCurrentLevel() {
        Game game = (Game) Gdx.app.getApplicationListener();
        switch (currentLevel) {
            case 1:
                game.setScreen(new FirstLevel());
                break;
            case 2:
                game.setScreen(new SecondLevel());
                break;
            case 3:
                game.setScreen(new ThirdLevel());
                break;
            default:
                System.out.println("Invalid level: " + currentLevel);
                break;
        }
    }
}
