package com.AngryBirds.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class FirstScreen implements Screen {
    private SpriteBatch batch; // sprite is drawn on a sprite batch
    private Sprite Splash;
    private Sprite play;
    private Sprite settings;
    private static Music music;
    @Override
    public void show() {
        batch = new SpriteBatch(); //creating the paper first to draw on it


        Texture t1 = new Texture("splash.png");
        Splash = new Sprite(t1);
        //we want the image to fill the whole screen
        Splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //the play button
        Texture t2 = new Texture("playBUTTON.png");
        play = new Sprite(t2);
        play.setSize(200,100);
        play.setPosition((Gdx.graphics.getWidth()- 200)/ 2, 10);




        //the settings icon
        Texture t3 = new Texture("settings.png");
        settings = new Sprite(t3);
        settings.setSize(50,50);
        settings.setPosition((Gdx.graphics.getWidth()- 75), Gdx.graphics.getHeight()-75);
        //handling the music
        this.music =Gdx.audio.newMusic(Gdx.files.internal("sounds/song.mp3"));
        music.setLooping(true);
        music.setVolume(0.75f);
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //starting to draw on the paper
        batch.begin();
        Splash.draw(batch);
        play.draw(batch);
        settings.draw(batch);
        batch.end();
        handleInput(play.getBoundingRectangle());

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system

            if (settings.getBoundingRectangle().contains(clickPosition)) {
                System.out.println("Sprite clicked!");
                // Add your custom code here, e.g., change sprite color, move it, etc.
                ((Game) Gdx.app.getApplicationListener()).setScreen(new SettingScreen());
            }
        }
        //ending to draw on the paper

    }

    @Override
    public void resize(int width, int height) {

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
        Splash.getTexture().dispose();
        play.getTexture().dispose();
        settings.getTexture().dispose();

    }

    private void handleInput(Rectangle r1){
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) { // Check for left-click
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y; // Convert to y-up coordinate system

            if (r1.contains(clickPosition)) {
                System.out.println("Sprite clicked!");
                // Add your custom code here, e.g., change sprite color, move it, etc.
                ((Game) Gdx.app.getApplicationListener()).setScreen(new LevelScreen());
            }
        }

    }

    public static Music getMusic(){return music;}
}
