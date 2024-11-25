package com.AngryBirds.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Objects;

public class Block {

    public static final short CATEGORY_BIRD = 0x0001;
    public static final short CATEGORY_CATAPULT = 0x0002;
    public static final short CATEGORY_PIG = 0x0004;
    public static final short CATEGORY_BLOCK = 0x0008;
    public static final short CATEGORY_GROUND = 0x0010;

    private Body body;
    private boolean damaged_block = false;
    private String type;
    private Sprite BlockSprite;
    private float x,y;

    public Block(String type, float x, float y, float size, World world){
        BodyDef bd = new BodyDef();
        FixtureDef f = new FixtureDef();

        if(type.equalsIgnoreCase("horizontal")){
            BlockSprite = new Sprite(new Texture("wood_horizontal.png"));
            BlockSprite.setSize(BlockSprite.getWidth()/2, BlockSprite.getHeight()/2);
        }
        else if(type.equalsIgnoreCase("vertical")){
            BlockSprite = new Sprite(new Texture("wood_vertical.png"));
            BlockSprite.setSize(BlockSprite.getWidth()/2, BlockSprite.getHeight()/2);
        }

        this.BlockSprite.setOrigin(BlockSprite.getWidth() / 2.0f, BlockSprite.getHeight() / 2.0f);
        //setting the origin at the centre

        bd.type = BodyDef.BodyType.DynamicBody;
        this.x =x;
        this.y =y;
        bd.position.set(x, y);


        PolygonShape rect = new PolygonShape();
        rect.setAsBox(BlockSprite.getWidth()/2, BlockSprite.getHeight()/2);


        f.shape = rect;
        f.density = 2f;
        f.friction = 1f;
        f.restitution = 0.3f;

        f.filter.categoryBits = CATEGORY_BLOCK;
        f.filter.maskBits = (short)(CATEGORY_PIG | CATEGORY_BLOCK | CATEGORY_GROUND|CATEGORY_BIRD); // collide with everything EXCEPT catapult



        this.body = world.createBody(bd);
        this.body.createFixture(f);

        this.body.setUserData(this); // Store a reference to this Bird instance

        // Dispose of the shape after creating the fixture
        rect.dispose();


    }


    public Sprite getBlockSprite(){
        return BlockSprite;
    }

    public void dispose(){
        dispose();
    }

    public void updateSprite(){
        Vector2 catapultPosition = body.getPosition(); // Get the body's position
        BlockSprite.setPosition(
            catapultPosition.x - BlockSprite.getWidth()/2, // Center the sprite on the body
            catapultPosition.y - BlockSprite.getHeight()/2
        );

            BlockSprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees); // Convert radians to degrees
    }

    public float getX(){return x;}
    public float getY(){return y;}

}
