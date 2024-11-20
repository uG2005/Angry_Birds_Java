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

public class ThirdLevel implements Screen {
    private SpriteBatch batch;
    private Sprite background, ground, catapult, green, blue, wood, pig1, pig2, pig3, pig4;
    private Sprite pause;
    private Rectangle pauseButtonBounds;

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    private static Music bgm;

    private void initializeTextures() {
        batch = new SpriteBatch();

        background = new Sprite(new Texture("background.png"));
        ground = new Sprite(new Texture("ground.png"));
        green = new Sprite(new Texture("green.png"));
        blue = new Sprite(new Texture("blue.png"));
        catapult = new Sprite(new Texture("cat.png"));
        wood = new Sprite(new Texture("horiz_wood.png"));
        pig1 = new Sprite(new Texture("pig1.png"));
        pig2 = new Sprite(new Texture("pig1.png"));
        pig3 = new Sprite(new Texture("pig1.png"));
        pig4 = new Sprite(new Texture("pig1.png"));
        pause = new Sprite(new Texture("pause_button.png"));

        bgm = FirstLevel.getBgm();

        pauseButtonBounds = new Rectangle(1170, 670, 0.10f * pause.getWidth(),
            0.10f * pause.getHeight());
    }

    @Override
    public void show() {
        initializeTextures();
    }

    @Override
    public void render(float delta) {
        clearScreen();

        handleInput(pause.getBoundingRectangle());

        if (Gdx.input.isTouched()) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y;

                if (!pause.getBoundingRectangle().contains(clickPosition)) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new VictoryScreen(3));
                }
            }
        }

        batch.begin();
        drawBackground();
        drawGround();
        drawBirdsAndCatapult();
        drawWoodBlocks();
        drawPigs();
        drawPause();
        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void handleInput(Rectangle r1) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            Vector2 clickPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            clickPosition.y = Gdx.graphics.getHeight() - clickPosition.y;

            if (r1.contains(clickPosition)) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new PauseScreen());
            }
        }
    }

    private void drawBackground() {
        background.draw(batch);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    private void drawGround() {
        ground.draw(batch);
        ground.setSize(Gdx.graphics.getWidth(), 50);
    }

    private void drawBirdsAndCatapult() {
        float catapultX = 100;
        float catapultY = 30;
        float birdSpacing = 45;

        catapult.setSize(200, 120);
        catapult.setPosition(catapultX, catapultY);
        catapult.draw(batch);

        green.setSize(40, 40);
        green.setPosition(catapultX - birdSpacing, catapultY + 10);
        green.draw(batch);

        blue.setSize(40, 40);
        blue.setPosition(catapultX - 2 * birdSpacing, catapultY + 5);
        blue.draw(batch);
    }

    private void drawWoodBlocks() {
        // Base blocks
        wood.setSize(120, 10);
        wood.setPosition(730, 150);
        wood.draw(batch);

        wood.setPosition(850, 150);
        wood.draw(batch);

        wood.setPosition(970, 150);
        wood.draw(batch);

        // Vertical blocks
        wood.setSize(120, 10);
        wood.setPosition(650, 110);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(770, 110);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(890, 110);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(1010, 110);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(650+60, 110+115);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(650+180, 110+115);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(650+300, 110+115);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(650+120, 110+230);
        wood.setRotation(90);
        wood.draw(batch);

        wood.setPosition(650+240, 110+230);
        wood.setRotation(90);
        wood.draw(batch);

        // Second layer horizontal blocks

        wood.setSize(120, 10);
        wood.setPosition(790, 260);
        wood.setRotation(0);
        wood.draw(batch);

        wood.setPosition(910, 260);
        wood.setRotation(0);
        wood.draw(batch);

        // Top block
        wood.setSize(120, 10);
        wood.setPosition(850, 380);
        wood.setRotation(0);
        wood.draw(batch);
    }

    private void drawPigs() {
        pig1.setSize(40, 40);
        pig1.setPosition(770, 40);
        pig1.draw(batch);

        pig2.setSize(40, 40);
        pig2.setPosition(890, 40);
        pig2.draw(batch);

        pig2.setSize(40, 40);
        pig2.setPosition(1010, 40);
        pig2.draw(batch);

        pig3.setSize(40, 40);
        pig3.setPosition(840, 155);
        pig3.draw(batch);

        pig3.setSize(40, 40);
        pig3.setPosition(960, 155);
        pig3.draw(batch);

        pig4.setSize(40, 40);
        pig4.setPosition(900, 270);
        pig4.draw(batch);
    }

    private void drawPause() {
        pause.draw(batch);
        pause.setSize(50, 50);
        pause.setPosition(Gdx.graphics.getWidth() - 75, Gdx.graphics.getHeight() - 50);
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
        background.getTexture().dispose();
        ground.getTexture().dispose();
        green.getTexture().dispose();
        blue.getTexture().dispose();
        catapult.getTexture().dispose();
        wood.getTexture().dispose();
        pig1.getTexture().dispose();
        pig2.getTexture().dispose();
        pig3.getTexture().dispose();
        pig4.getTexture().dispose();
        pause.getTexture().dispose();
    }
}

