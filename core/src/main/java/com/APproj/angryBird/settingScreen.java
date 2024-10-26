package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class settingScreen implements Screen {
    final MainLauncher game;
    private Texture background, musicOn, musicOff, soundOn, soundOff, close;
    private final FitViewport viewport;
    private OrthographicCamera camera;
    private Rectangle rect1, rect2, rect3;

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private BitmapFont font; // Add BitmapFont for rendering text

    public settingScreen(MainLauncher game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();

        // Load textures
        this.background = new Texture("settings_back.jpg");
        this.musicOn = new Texture("music_on.png");
        this.musicOff = new Texture("music_off.png");
        this.soundOn = new Texture("sound_on.png");
        this.soundOff = new Texture("sound_off.png");
        this.close = new Texture("close.png");

        this.rect1 = new Rectangle();
        this.rect2 = new Rectangle();
        this.rect3 = new Rectangle();


        rect1.x = (WORLD_WIDTH - background.getWidth()) / 2 + 200;
        rect1.y = (WORLD_HEIGHT - background.getHeight()) / 2 + 137;
        rect1.width = 70;
        rect1.height = 70;

        rect2.x = (WORLD_WIDTH - background.getWidth()) / 2 + 320;
        rect2.y = (WORLD_HEIGHT - background.getHeight()) / 2 + 137;
        rect2.width = 70;
        rect2.height = 70;

        rect3.x = (WORLD_WIDTH - background.getWidth()) / 2 + 75 +425;
        rect3.y = (WORLD_HEIGHT - background.getHeight()) / 2+275;
        rect3.width = 50;
        rect3.height = 50;



        // Load font from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font\\angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36; // Adjust size as needed
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use
    }

    @Override
    public void show() {
        // Any setup that should happen when the screen is shown can be placed here
    }

    @Override
    public void render(float delta) {
        float backgroundx = (WORLD_WIDTH - background.getWidth()) / 2 + 75;
        float backgroundy = (WORLD_HEIGHT - background.getHeight()) / 2;

        viewport.apply();
        game.batch.setProjectionMatrix(camera.combined);  // Set camera projection

        game.batch.begin();
        game.batch.draw(background, backgroundx, backgroundy, 450, 300);
        game.batch.draw(musicOn, rect1.x, rect1.y, rect1.width, rect1.height);
        game.batch.draw(soundOn, rect2.x, rect2.y, rect2.width, rect2.height);
        game.batch.draw(close, rect3.x, rect3.y, rect3.width, rect3.height);

        // Render text with the font
        font.draw(game.batch, "Settings", backgroundx + 300/2, backgroundy +275);
        font.draw(game.batch, "Credits: ", rect1.x -100, rect1.y +5-12 );
        font.draw(game.batch, "Aarushi", rect1.x - 50, rect1.y-25-12);
        font.draw(game.batch, "Ujjwal", rect1.x - 50, rect1.y-65-12);

        game.batch.end();



        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); // Convert screen coordinates to world coordinates

            if (rect3.contains(touchPos.x, touchPos.y)) {
                game.setScreen(new firstScreen(game));
            }


        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // Handle pause if needed
    }

    @Override
    public void resume() {
        // Handle resume if needed
    }

    @Override
    public void hide() {
        // Handle actions when screen is hidden
    }

    @Override
    public void dispose() {
        background.dispose();
        musicOn.dispose();
        musicOff.dispose();
        soundOn.dispose();
        soundOff.dispose();
        close.dispose();
        font.dispose(); // Dispose of the font when done
    }
}
