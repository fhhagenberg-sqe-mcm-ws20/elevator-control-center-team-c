package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

        assertEquals("Constructor: Commited direction has a range from 0 to 2", exception.getMessage());//assertEquals

    }

    @Test
    public void testConstructorWhenDoorStatusIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Elevator(1, 1, 1, 5, null, 1, 1, 1, 1, null, floorButtons));

        assertEquals("Constructor: Door status can either be 1 or 2", exception.getMessage());//assertEquals
    }

    @Test
    public void testSetCommitedDirectionWhenCommitedDirectionIsInRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);

        elevator.setCommitedDirection(0);
        assertEquals(0, elevator.getCommitedDirection());

        elevator.setCommitedDirection(1);
        assertEquals(1, elevator.getCommitedDirection());

        elevator.setCommitedDirection(2);
        assertEquals(2, elevator.getCommitedDirection());
    }

    @Test
    public void testSetCommitedDirectionWhenCommitedDirectionIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setCommitedDirection(5));

        assertEquals("Set: Commited direction has a range from 0 to 2", exception.getMessage());//assertEquals

    }

    @Test
    public void testSetDoorStatusWhenDoorStatusIsInRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);

        elevator.setDoorStatus(1);
        assertEquals(1, elevator.getDoorStatus());

        elevator.setDoorStatus(2);
        assertEquals(2, elevator.getDoorStatus());
    }

    @Test
    public void testSetDoorStatusWhenDoorStatusIsOutOfRange() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setDoorStatus(5));

        assertEquals("Set: Door status can either be 1 or 2", exception.getMessage());//assertEquals
    }

    @Test
    public void testGetFloorButtonStatusWhenElevatorDoesServiceTheFloor() {
        HashMap<Floor, Boolean> floorButtons = new HashMap<>();
        Floor floor0 = new Floor(0, false, false);
        Floor floor1 = new Floor(1, false, false);
        floorButtons.put(floor0, false);
        floorButtons.put(floor1, true);

        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);

        assertEquals(false, elevator.getFloorButtonStatus(floor0));
        assertEquals(true, elevator.getFloorButtonStatus(floor1));
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

    @Test
    public void testCopyValues() {
        HashMap<Floor, Boolean> floorButtons1 = new HashMap<>();
        floorButtons1.put(new Floor(0, false, false), false);
        HashMap<Floor, Boolean> floorButtons2 = new HashMap<>();
        floorButtons2.put(new Floor(1, true, false), true);
        List<Floor> floors = new ArrayList<>();
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, false);
        floors.add(floor1);
        floors.add(floor2);
        Floor floor3 = new Floor(0, true, false);
        Floor floor4 = new Floor(1, true, true);
        Elevator elevator1 = new Elevator(1, 1, 1, 1, floor1, 1, 1, 1, 1, floor2, floorButtons1);
        Elevator elevator2 = new Elevator(2, 2, 2, 2, floor4, 2, 2, 2, 2, floor3, floorButtons2);

        elevator1.copyValues(elevator2, floors);

        assertNotSame(elevator1, elevator2);
        assertEquals(2, elevator1.getCommitedDirection());
        assertEquals(2, elevator1.getAcceleration());
        assertEquals(2, elevator1.getDoorStatus());
        assertNotSame(elevator1.getFloor(), elevator2.getFloor());
        assertEquals(1, elevator1.getFloor().getNumber());
        assertEquals(2, elevator1.getPosition());
        assertEquals(2, elevator1.getCommitedDirection());
        assertEquals(2, elevator1.getSpeed());
        assertEquals(2, elevator1.getWeight());
        assertEquals(2, elevator1.getCapacity());
        assertNotSame(elevator1.getTarget(), elevator2.getTarget());
        assertEquals(0, elevator1.getTarget().getNumber());
        assertTrue(elevator1.servicesFloor(new Floor(1, true, true)));
        assertFalse(elevator1.servicesFloor(new Floor(0, true, true)));
        assertTrue(elevator1.getFloorButtonStatus(new Floor(1, true, true)));
        assertThrows(IllegalArgumentException.class, () -> elevator1.getFloorButtonStatus(new Floor(0, true, true)));
    }
}
