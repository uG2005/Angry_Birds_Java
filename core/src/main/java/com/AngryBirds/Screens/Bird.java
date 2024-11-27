package com.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.badlogic.gdx.Gdx.graphics;

public class Bird {


    public static final float MAX_DRAG_DISTANCE = 150f;  // Maximum distance bird can be pulled back
    public Vector2 catapult;  // Position of the slingshot/catapult
    public boolean isDragging = false;
    public boolean isLaunched = false;
    public boolean oncatapult = false;
    public boolean destroyed = false;
    public boolean testing =false;

    public static final short CATEGORY_BIRD = 0x0001;
    public static final short CATEGORY_CATAPULT = 0x0002;
    public static final short CATEGORY_PIG = 0x0004;
    public static final short CATEGORY_BLOCK = 0x0008;
    public static final short CATEGORY_GROUND = 0x0010;

    // Setting up Box2D
    public Body body;
    public boolean damaged_bird = false;
    public String color;
    public Sprite birdSprite;
    public float size;
    float worldX;
    float worldY;


    // Constructor
    public Bird(String color, float x, float y, float size, World world, boolean test) {

        BodyDef bd = new BodyDef();
        FixtureDef f = new FixtureDef();


        worldX = x ;
        worldY = y;

        if(!test){
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
        }

        else{this.birdSprite = null;}




        // Body definition and positioning
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(worldX, worldY);

        // Shape definition (circle) for the body
        CircleShape circle = new CircleShape();
        circle.setRadius(22.5f);  // Adjust radius for Box2D scaling

        // Fixture properties
        f.shape = circle;
        f.density = 10f;
        f.friction = 0f;
        f.restitution = 0f;

        f.filter.categoryBits = CATEGORY_BIRD;
        f.filter.maskBits = (short)(CATEGORY_PIG | CATEGORY_BLOCK | CATEGORY_GROUND); // collide with everything EXCEPT catapult

        // Create the body and attach the fixture
        this.body = world.createBody(bd);
        this.body.createFixture(f);

        this.body.setUserData(this); // Store a reference to this Bird instance

        // Dispose of the shape after creating the fixture
        circle.dispose();

        this.catapult = new Vector2(275, 150+37.5f*2);
        this.isLaunched = false;


    }


    // Restores the bird to its initial state
    public void rest() {
        body.setLinearVelocity(0, 0);
        body.setAngularVelocity(0);
        body.setTransform(body.getPosition(), 0); // Reset the position and rotation
        damaged_bird = false;  // Reset damaged state
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


    public void startDrag() {
        isDragging = true;
        body.setActive(false); // Disable physics while dragging
    }


    public void updateDrag(Vector2 newPosition) {
        if (!isDragging || isLaunched) return;

        // Calculate vector from anchor to touch position
        Vector2 dragVector = new Vector2(newPosition).sub(catapult);


        // Limit drag distance
        float dragDistance = dragVector.len();
        if (dragDistance > MAX_DRAG_DISTANCE) {
            dragVector.nor().scl(MAX_DRAG_DISTANCE);
            newPosition = new Vector2(catapult).add(dragVector);
        }

        // Update bird position
        body.setTransform(newPosition, 0);
    }


    public void launch() {
        isDragging = false;
        isLaunched = true;
        body.setActive(true);


        // Calculate launch velocity (from current position to slingshot anchor)
        Vector2 launchVector = new Vector2(catapult).sub(body.getPosition()).scl(10);

        body.setLinearVelocity(launchVector.x*100f,0);
        body.applyLinearImpulse(new Vector2(10,0),body.getWorldCenter(),true);
        body.applyForceToCenter(1000f,0,true);





    }

    // Add getters
    public boolean isDragging() { return isDragging; }
    public boolean isLaunched() { return isLaunched; }
    public Vector2 getSlingshotAnchor() { return catapult; }






}
