package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {
    @Test
    public void testGetServicedFloorsWhenThereAreFloorButtons() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Floor floor0 = new Floor(0, false, false);
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(2, false, false);
        floorButtons.put(floor0, true);
        floorButtons.put(floor1, false);
        floorButtons.put(floor2, true);
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        List<Floor> servicedFloors = elevator.getServicedFloors();
        assertEquals(3, servicedFloors.size());
        assertTrue(servicedFloors.contains(floor0));
        assertTrue(servicedFloors.contains(floor1));
        assertTrue(servicedFloors.contains(floor2));
    }

    @Test
    public void testGetServicedFloorsWhenThereAreNoFloorButtons() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        List<Floor> servicedFloors = elevator.getServicedFloors();
        assertEquals(0, servicedFloors.size());
    }

    @Test
    public void testConstructorWhenCommitedDirectionIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Elevator(1, 5, 1, 1, null, 1, 1, 1, 1, null, floorButtons));
        String expectedMessage = "Constructor: Commited direction has a range from 0 to 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testConstructorWhenDoorStatusIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Elevator(1, 1, 1, 5, null, 1, 1, 1, 1, null, floorButtons));
        String expectedMessage = "Constructor: Door status can either be 1 or 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetCommitedDirectionWhenCommitedDirectionIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setCommitedDirection(5));
        String expectedMessage = "Set: Commited direction has a range from 0 to 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testSetDoorStatusWhenDoorStatusIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setDoorStatus(5));
        String expectedMessage = "Set: Door status can either be 1 or 2";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testGetFloorButtonStatusWhenElevatorDoesntServiceTheFloor() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Floor floor0 = new Floor(0, false, false);
        Floor floor1 = new Floor(1, false, false);
        Floor floor2 = new Floor(2, false, false);

        floorButtons.put(floor0, false);
        floorButtons.put(floor1, false);
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        assertThrows(IllegalArgumentException.class, () -> elevator.getFloorButtonStatus(floor2));
    }

    @Test
    public void testGetFloorButtonStatusWhenElevatorServicesNoFloor() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Floor floor0 = new Floor(0, false, false);
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        assertThrows(IllegalArgumentException.class, () -> elevator.getFloorButtonStatus(floor0));
    }
}
