package com.AngryBirds.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {

    private Body body;
    private String type;
    private boolean damaged_pig = false;
    private Sprite pigSprite;

    public Pig(String type, float size, float x, float y, World world) {
        BodyDef bd = new BodyDef();
        FixtureDef f = new FixtureDef();

        // Initialize the pig's sprite based on the type
        if (type.equalsIgnoreCase("small")) {
            this.pigSprite = new Sprite(new Texture("pig1.png"));
            float spriteSize = 22.5f * 2 ; // Adjust for Box2D world scaling
            this.pigSprite.setSize(spriteSize, spriteSize);
            this.pigSprite.setOrigin(spriteSize / 2, spriteSize / 2);
        } else if (type.equalsIgnoreCase("mid")) {
            this.pigSprite = new Sprite(new Texture("pig2.png"));
            float spriteSize = 22.5f * 2 ; // Adjust for Box2D world scaling
            this.pigSprite.setSize(spriteSize, spriteSize);
            this.pigSprite.setOrigin(spriteSize / 2, spriteSize / 2);
        } else if (type.equalsIgnoreCase("big")) {
            this.pigSprite = new Sprite(new Texture("pig3.png"));
            float spriteSize = 22.5f * 2; // Adjust for Box2D world scaling
            this.pigSprite.setSize(spriteSize, spriteSize);
            this.pigSprite.setOrigin(spriteSize / 2, spriteSize / 2);
        }

        // Body definition and positioning
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);

        // Shape definition (circle) for the body
        CircleShape circle = new CircleShape();
        circle.setRadius(22.5f); // Match the sprite size in Box2D units

        // Fixture properties
        f.shape = circle;
        f.density = 2f;
        f.friction = 0.5f;
        f.restitution = 0f;

        // Create the body and attach the fixture
        this.body = world.createBody(bd);
        this.body.createFixture(f);

        this.body.setUserData(this); // Store a reference to this Pig instance

        // Dispose of the shape after creating the fixture
        circle.dispose();
    }

    public void setDamagedpig(boolean b) {
        damaged_pig = b;
    }

    public Sprite getPigSprite() {
        return pigSprite;
    }

    public void updateSprite() {
        pigSprite.setPosition(
            body.getPosition().x - pigSprite.getWidth() / 2,
            body.getPosition().y - pigSprite.getHeight() / 2
        );
        pigSprite.setRotation(body.getAngle() * (180f / (float) Math.PI)); // Convert radians to degrees
    }
}
