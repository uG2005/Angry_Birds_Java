package com.AngryBirds.Screens;

import com.AngryBirds.MainLauncher;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SettingScreen implements Screen {
    //private final MainLauncher game;
    private SpriteBatch batch;
    private Sprite background, musicOn, musicOff, soundOn, soundOff, close;
    //private final FitViewport viewport;
    private OrthographicCamera camera;
    private Rectangle rect1, rect2, rect3;
    private boolean isMusicOn = true;
    private boolean isSoundOn = true;

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private BitmapFont font; // Add BitmapFont for rendering text

//    public SettingScreen(MainLauncher game) {
//        this.game = game;
//        camera = new OrthographicCamera();
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//        viewport.apply();
//        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
//        camera.update();
//
//        // Load textures
//
//
//        this.rect1 = new Rectangle();
//        this.rect2 = new Rectangle();
//        this.rect3 = new Rectangle();
//
//
//        rect1.x = (WORLD_WIDTH - background.getWidth()) / 2 + 200;
//        rect1.y = (WORLD_HEIGHT - background.getHeight()) / 2 + 137;
//        rect1.width = 70;
//        rect1.height = 70;
//
//        rect2.x = (WORLD_WIDTH - background.getWidth()) / 2 + 320;
//        rect2.y = (WORLD_HEIGHT - background.getHeight()) / 2 + 137;
//        rect2.width = 70;
//        rect2.height = 70;
//
//        rect3.x = (WORLD_WIDTH - background.getWidth()) / 2 + 75 +425;
//        rect3.y = (WORLD_HEIGHT - background.getHeight()) / 2+275;
//        rect3.width = 50;
//        rect3.height = 50;
//
//
//
//        // Load font from .ttf file
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font\\angrybirds-regular.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 36; // Adjust size as needed
//        parameter.color = Color.BLACK;
//        font = generator.generateFont(parameter); // Generate the font
//        generator.dispose(); // Dispose of the generator after use
//    }

    @Override
    public void show() {
        // Any setup that should happen when the screen is shown can be placed here
        batch = new SpriteBatch();

        background = new Sprite(new Texture("settings_back.jpg"));
        musicOn = new Sprite(new Texture("music_on.png"));
        musicOff = new Sprite(new Texture("music_off.png"));
        soundOn = new Sprite(new Texture("sound_on.png"));
        soundOff = new Sprite(new Texture("sound_off.png"));
        close = new Sprite(new Texture("close.png"));


        //setting the background
        background.setSize(450,300);
        background.setPosition((Gdx.graphics.getWidth()-450)/2,(Gdx.graphics.getHeight()-300)/2);


        //setting the close button
        close.setSize(50,50);
        close.setPosition(background.getX()+background.getWidth()-close.getWidth()/2,background.getY()+background.getHeight()-close.getHeight()/2);

        //setting the music on
        musicOn.setSize(70,70);
        musicOff.setSize(70, 70);
        musicOn.setPosition(background.getX()+background.getWidth()/4,background.getY()+background.getHeight()/2);
        musicOff.setPosition(musicOn.getX(), musicOn.getY());

        //setting the sound on
        soundOn.setSize(70,70);
        soundOff.setSize(70, 70);
        soundOn.setPosition(background.getX()+0.60f*background.getWidth(),background.getY()+background.getHeight()/2);
        soundOff.setPosition(soundOn.getX(), soundOn.getY());


        // Load font from .ttf file
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font\\angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36; // Adjust size as needed
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter); // Generate the font
        generator.dispose(); // Dispose of the generator after use
    }

    @Override
    public void render(float delta) {
        float backgroundx = (WORLD_WIDTH - background.getWidth()) / 2 + 75;
        float backgroundy = (WORLD_HEIGHT - background.getHeight()) / 2;


        batch.begin();
        background.draw(batch);
        close.draw(batch);
        if (isMusicOn) musicOn.draw(batch);
        else musicOff.draw(batch);

        if (isSoundOn) soundOn.draw(batch);
        else soundOff.draw(batch);

        // Render text with the font
        font.draw(batch, "Settings", background.getX()  + background.getWidth()/2 -75, (background.getY()) + 275);
        font.draw(batch, "Credits: ", background.getX() +14 , background.getY() + background.getHeight()/2);
        font.draw(batch, "Aarushi", background.getX() +21, background.getY() +background.getHeight()/2- 25 - 12);
        font.draw(batch, "Ujjwal", background.getX() +21, background.getY()+ background.getHeight()/2 - 65 - 12);

        batch.end();


        if (Gdx.input.isTouched()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
                Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system

                if (close.getBoundingRectangle().contains(clickPosition)) {
                    System.out.println("Sprite clicked!");

                    ((Game) Gdx.app.getApplicationListener()).setScreen(new FirstScreen());
                }

                if (musicOn.getBoundingRectangle().contains(clickPosition) || musicOff.getBoundingRectangle().contains(clickPosition)) {
                    isMusicOn = !isMusicOn;
                    if (isMusicOn) {
                        FirstScreen.getMusic().play(); // Resume music
                    } else {
                        FirstScreen.getMusic().pause(); // Pause music
                    }
                }

            }
        }
    }

    @Override
    public void resize(int width, int height) {
        //viewport.update(width, height);
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
        background.getTexture().dispose();
        musicOn.getTexture().dispose();
        musicOff.getTexture().dispose();
        soundOn.getTexture().dispose();
        soundOff.getTexture().dispose();
        close.getTexture().dispose();
        font.dispose(); // Dispose of the font when done
    }
}
