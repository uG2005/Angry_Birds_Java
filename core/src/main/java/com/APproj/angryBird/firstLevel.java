package com.APproj.angryBird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class firstLevel implements Screen {
    private final MainLauncher game;
    Texture background;
    Texture bird;
    Texture ground;
    Texture wood;

    public firstLevel(MainLauncher game) {
        this.game = game;
        this.background = new Texture("background.png");
        this.ground = new Texture("ground.png");
        this.bird = new Texture("angry.png");

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //clearing the screen before drawing on it
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
        game.batch.draw(ground,0,0,Gdx.graphics.getWidth(),100);
        game.batch.draw(bird,100,80);
        game.batch.end();



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

    }
}
