package com.AngryBirds2.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class button {
    private Texture texture;
    private Rectangle bounds;
    private Viewport viewport;

    public button(Texture texture, float x, float y, float width, float height, Viewport viewport) {
        this.texture = texture;
        this.viewport = viewport;
        bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public boolean isTouched(Vector3 touchPos) {
        return bounds.contains(touchPos.x, touchPos.y);
    }



    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return bounds.x;
    }

    public float getY() {
        return bounds.y;
    }

    public float getWidth() {
        return bounds.width;
    }

    public float getHeight() {
        return bounds.height;
    }

}

