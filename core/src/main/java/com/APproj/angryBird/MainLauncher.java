package com.APproj.angryBird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationAdapter} implementation shared by all platforms. */
public class MainLauncher extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new firstScreen(this));
    }

    @Override
    public void render() {
        super.render(); //run the render method of the first screen
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
