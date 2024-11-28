package com.AngryBirds.Screens;

import com.AngryBirds.GameData;
import com.AngryBirds.MainLauncher;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelScreen implements Screen {
    final MainLauncher game = (MainLauncher) Gdx.app.getApplicationListener();

    private Stage stage;
    private FitViewport viewport;
    private Texture backgroundTexture;

    @Override
    public void show() {
        // Set up viewport and stage
        viewport = new FitViewport(1280, 720);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Load background texture
        backgroundTexture = new Texture("background.png");

        // Create and position buttons
        createButtons();
    }

    private void createButtons() {
        float buttonWidth = 200f;
        float buttonHeight = 80f;
        float spacing = 30f;

        int currentLevel = GameData.getCurrentLevel();

        // Back button
        Texture backTexture = new Texture("backy.png");
        ImageButton backButton = new ImageButton(new TextureRegionDrawable(backTexture));
        backButton.setSize(buttonWidth, buttonHeight);
        backButton.setPosition(10, viewport.getWorldHeight() - buttonHeight - 10);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Back button clicked.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstScreen());
            }
        });
        stage.addActor(backButton);

        String[] levelTextures = {"level1.png", "level2.png", "level3.png"};
        for (int i = 0; i < 3; i++) {
            Texture levelTexture = new Texture(levelTextures[i]);
            ImageButton levelButton = new ImageButton(new TextureRegionDrawable(levelTexture));
            levelButton.setSize(buttonWidth, buttonHeight);
            levelButton.setPosition((viewport.getWorldWidth() - 3 * buttonWidth - 2 * spacing) / 2 + i * (buttonWidth + spacing), viewport.getWorldHeight() / 2 - buttonHeight);

            if (i + 1 > currentLevel) {
                levelButton.setDisabled(true);
                levelButton.setColor(0.5f, 0.5f, 0.5f, 1); // Gray out locked levels
            } else {
                final int level = i + 1;
                levelButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        switch (level) {
                            case 1:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstLevel());
                                break;
                            case 2:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new SecondLevel());
                                break;
                            case 3:
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new ThirdLevel());
                                break;
                        }
                    }
                });
            }
            stage.addActor(levelButton);

        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw background
        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        stage.getBatch().end();

        // Render stage
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        stage.dispose();
        backgroundTexture.dispose();
    }
}
