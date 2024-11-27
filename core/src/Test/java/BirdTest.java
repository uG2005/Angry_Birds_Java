import static org.junit.Assert.*;

import com.AngryBirds.Screens.Bird;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.Before;
import org.junit.Test;

public class BirdTest {

    private static final float TOLERANCE = 0.1f; // Tolerance for floating point comparisons

    private Bird bird;

    // Mocking the catapult position to simulate the drag and launch logic
    private Vector2 catapultPosition = new Vector2(275, 150 + 37.5f * 2);

    private World world;

    @Before
    public void setUp() {
        // Initialize a Box2D world with no gravity (2D physics world)
        world = new World(new Vector2(0, 0), true);

        // Initializing Bird with a mock color, position, size, and the actual world
        bird = new Bird("red", 275, 150 + 37.5f * 2, 45, world,true);
        bird.catapult = catapultPosition;  // Mock the catapult position
    }

    @Test
    public void testUpdateDrag_LimitedToMaxDragDistance() {
        // Simulating a drag that goes beyond the maximum drag distance
        Vector2 dragPosition = new Vector2(500, 500);  // A point far away from the catapult

        bird.startDrag();
        bird.updateDrag(dragPosition);

        // Check that the bird's position doesn't exceed the maximum drag distance
        Vector2 newPosition = bird.getBody().getPosition();
        Vector2 dragVector = newPosition.sub(bird.catapult);
        assertTrue(dragVector.len() <= Bird.MAX_DRAG_DISTANCE);
    }

    @Test
    public void testUpdateDrag_NoMovementAfterLaunch() {
        // Simulating a drag after launch
        bird.isLaunched = true;
        Vector2 dragPosition = new Vector2(400, 400);  // A point far away from the catapult

        bird.updateDrag(dragPosition);

        // The position should not change after launch
        assertEquals(catapultPosition.x, bird.getBody().getPosition().x, TOLERANCE);
        assertEquals(catapultPosition.y, bird.getBody().getPosition().y, TOLERANCE);
    }

    @Test
    public void testLaunch_CorrectVelocity() {
        // Launch the bird and check if the calculated velocity is correct
        bird.launch();

        Vector2 launchVelocity = bird.getBody().getLinearVelocity();

        // Checking if the velocity in the x-direction is correct
        // The expected velocity is calculated as (catapult position - bird position) * some scaling factor
        Vector2 expectedVelocity = catapultPosition.cpy().sub(bird.getBody().getPosition()).scl(10f);
        assertEquals(expectedVelocity.x * 100f, launchVelocity.x, TOLERANCE); // Ensure the velocity matches with some tolerance
        assertEquals(0, launchVelocity.y, TOLERANCE); // The y-component should be zero since we apply velocity only in the x direction
    }

    @Test
    public void testLaunch_AppliedForce() {
        // Launch the bird and check if force is applied correctly
        bird.launch();

        // Check that the body has some velocity (force applied)
        assertTrue(bird.getBody().getLinearVelocity().x > 0);
    }
}
