package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildingTest {

    @Test
    public void testConstructorWhenFloorHeightIsNotNegative() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);
        assertEquals(100, building.getFloorHeight());
    }

    @Test
    public void testConstructorWhenFloorHeightIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Building(new ArrayList<>(), new ArrayList<>(), -100));
        String expectedMessage = "Constructor: Floor height can't be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetFloorHeightWhenFloorHeightIsNegative() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                building.setFloorHeight(-100));
        String expectedMessage = "Setter: Floor height can't be negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
