package com.AngryBirds.Screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class InputManager implements InputProcessor {

    private final Bird bird;

    public InputManager(Bird bird) {
        this.bird = bird;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to world coordinates if needed
        Vector2 touchPos = new Vector2(screenX, screenY); // Add screen-to-world conversion logic if required

        // Check if touch is within the drag region of the bird
        if (bird.getBirdSprite().getBoundingRectangle().contains(touchPos.x, touchPos.y)) {
            bird.startDrag(); // Start dragging the bird
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (bird.isDragging()) {
            Vector2 dragPosition = new Vector2(screenX, screenY); // Add screen-to-world conversion logic if needed
            bird.updateDrag(dragPosition); // Update bird position based on drag
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (bird.isDragging()) {
            bird.isDragging = false; // Stop dragging
            bird.isLaunched = true; // Mark bird as launched

            // Calculate launch vector
            Vector2 launchVector = new Vector2(bird.getSlingshotAnchor()).sub(bird.body.getPosition());

            // Scale the impulse based on the length of the launch vector
            float impulseScale = 1500; // Adjust this multiplier for desired launch strength
            Vector2 impulse = launchVector.scl(impulseScale);

            bird.body.setActive(true); // Enable physics for the bird
            bird.body.applyLinearImpulse(impulse, bird.body.getWorldCenter(), true); // Apply linear impulse at the center of the body
        }
        return true;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
