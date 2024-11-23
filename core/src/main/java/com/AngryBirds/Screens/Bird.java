package com.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.badlogic.gdx.Gdx.graphics;

public class Bird {

    // Setting up Box2D
    private Body body;
    private boolean damaged_bird = false;
    private String color;
    private Sprite birdSprite;
    private float size;

    private boolean isDragging = false;
    private Vector2 initialPosition;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();

    // Constructor
    public Bird(String color, float x, float y, float size, World world) {

        BodyDef bd = new BodyDef();
        FixtureDef f = new FixtureDef();


        float worldX = x ;
        float worldY = y;


        this.color = color;
        this.size = size;

        // Initialize the bird's sprite based on the color
        if (color.equalsIgnoreCase("red")) {
            this.birdSprite = new Sprite(new Texture("angry.png"));
        } else if (color.equalsIgnoreCase("blue")) {
            this.birdSprite = new Sprite(new Texture("blue.png"));
        } else if (color.equalsIgnoreCase("green")) {
            this.birdSprite = new Sprite(new Texture("green.png"));
        }

        this.birdSprite.setSize(size, size);
        this.birdSprite.setOrigin(size / 2.0f, size / 2.0f);

        // Body definition and positioning
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(worldX, worldY);

        // Shape definition (circle) for the body
        CircleShape circle = new CircleShape();
        circle.setRadius(22.5f);  // Adjust radius for Box2D scaling

        // Fixture properties
        f.shape = circle;
        f.density = 2f;
        f.friction = 0.5f;
        f.restitution = 0.2f;

        // Create the body and attach the fixture
        this.body = world.createBody(bd);
        this.body.createFixture(f);

        this.body.setUserData(this); // Store a reference to this Bird instance

        // Dispose of the shape after creating the fixture
        circle.dispose();
    }


    // Restores the bird to its initial state
    public void rest() {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setTransform(body.getPosition(), 0); // Reset the position and rotation
        damaged_bird = false;  // Reset damaged state
    }

    // Collide with a Block
    public void collideWithBlock(Block block) {
        if (!damaged_bird) {
//            block.setDamaged(true);  // Mark the block as damaged
            damaged_bird = true;     // Mark the bird as damaged
        }
    }

    // Collide with a Pig
    public void collideWithPig(Pig pig) {
        if (!damaged_bird) {
            pig.setDamagedpig(true);  // Mark the pig as damaged
            damaged_bird = true;   // Mark the bird as damaged
        }
    }

    // Getter for the bird's body
    public Body getBody() {
        return body;
    }

    public float getSize(){
        return size;
    }

    // Getter for the bird's sprite (for rendering)
    public Sprite getBirdSprite() {
        return birdSprite;
    }

    // Getter for the bird's current damage state
    public boolean isDamaged() {
        return damaged_bird;
    }




}
