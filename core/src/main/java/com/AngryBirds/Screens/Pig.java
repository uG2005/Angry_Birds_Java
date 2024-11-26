package com.AngryBirds.Screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

public class Pig {

    public static final short CATEGORY_BIRD = 0x0001;
    public static final short CATEGORY_CATAPULT = 0x0002;
    public static final short CATEGORY_PIG = 0x0004;
    public static final short CATEGORY_BLOCK = 0x0008;
    public static final short CATEGORY_GROUND = 0x0010;

    private int health =1;
    private Body body;
    private String type;
    private boolean damaged_pig = false;
    private Sprite pigSprite;
    public boolean destroyed = false;
    public boolean initiallyonblock = false;

    public Pig(String type, float size, float x, float y, World world, boolean onblock) {
        BodyDef bd = new BodyDef();
        FixtureDef f = new FixtureDef();

        initiallyonblock = onblock;

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

        f.filter.categoryBits = CATEGORY_PIG;
        f.filter.maskBits = (short)(CATEGORY_PIG | CATEGORY_BLOCK | CATEGORY_GROUND|CATEGORY_BIRD|CATEGORY_CATAPULT); // collide with everything EXCEPT catapult

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
