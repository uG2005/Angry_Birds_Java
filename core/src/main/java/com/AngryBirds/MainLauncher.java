package com.AngryBirds;

import com.AngryBirds.Screens.FirstLevel;
import com.AngryBirds.Screens.FirstScreen;
import com.AngryBirds.Screens.SettingScreen;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainLauncher extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
        super.resize(width, height);
    }

    @Override
    public void render() {
        // Draw your application here.
        super.render();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
        super.pause();
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.

        super.resume();
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        super.dispose();

    }
}
