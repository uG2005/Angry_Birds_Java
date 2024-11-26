package com.AngryBirds.Screens;

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

        // Level 1 button
        Texture level1Texture = new Texture("level1.png");
        ImageButton level1Button = new ImageButton(new TextureRegionDrawable(level1Texture));
        level1Button.setSize(buttonWidth, buttonHeight);
        level1Button.setPosition((viewport.getWorldWidth() - 3 * buttonWidth - 2 * spacing) / 2, viewport.getWorldHeight() / 2 - buttonHeight);
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Level 1 button clicked.");
                FirstScreen.getMusic().stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstLevel());
            }
        });
        stage.addActor(level1Button);

        // Add more buttons similarly (e.g., level 2, level 3)
        Texture level2Texture = new Texture("level2.png");
        ImageButton level2Button = new ImageButton(new TextureRegionDrawable(level2Texture));
        level2Button.setSize(buttonWidth, buttonHeight);
        level2Button.setPosition((viewport.getWorldWidth() -  buttonWidth -  spacing) / 2, viewport.getWorldHeight()  / 2 - buttonHeight);
        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Level 1 button clicked.");
                //FirstScreen.getMusic().stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SecondLevel());
            }
        });
        stage.addActor(level2Button);

        Texture level3Texture = new Texture("level3.png");
        ImageButton level3Button = new ImageButton(new TextureRegionDrawable(level3Texture));
        level3Button.setSize(buttonWidth, buttonHeight);
        level3Button.setPosition((viewport.getWorldWidth() +   buttonWidth +  spacing) / 2, viewport.getWorldHeight() / 2 - buttonHeight);
        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Level 1 button clicked.");
                //FirstScreen.getMusic().stop();
                ((Game) Gdx.app.getApplicationListener()).setScreen(new ThirdLevel());
            }
        });
        stage.addActor(level3Button);

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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        backgroundTexture.dispose();
    }
}
