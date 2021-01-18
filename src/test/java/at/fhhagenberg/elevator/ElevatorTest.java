package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorTest {
    @Test
    void testConstructorWhenCommitedDirectionIsOutOfRange() {
        List<Boolean> floorButtons = new ArrayList<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Elevator(1, 5, 1, 1, 1, 1, 1, 1, 1, 1, null, floorButtons));

        assertEquals("Constructor: Commited direction has a range from 0 to 2", exception.getMessage());//assertEquals

    }

    @Test
    void testConstructorWhenDoorStatusIsOutOfRange() {
        List<Boolean> floorButtons = new ArrayList<>();

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Elevator(1, 1, 1, 5, 1, 1, 1, 1, 1, 1, null, floorButtons));

        assertEquals("Constructor: Door status can either be 1, 2, 3 or 4", exception.getMessage());//assertEquals
    }

    @Test
    void testSetCommitedDirectionWhenCommitedDirectionIsInRange() {
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, floorButtons);

        elevator.setCommitedDirection(0);
        assertEquals(0, elevator.getCommitedDirection());

        elevator.setCommitedDirection(1);
        assertEquals(1, elevator.getCommitedDirection());

        elevator.setCommitedDirection(2);
        assertEquals(2, elevator.getCommitedDirection());
    }

    @Test
    void testSetCommitedDirectionWhenCommitedDirectionIsOutOfRange() {
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, floorButtons);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setCommitedDirection(5));

        assertEquals("Set: Commited direction has a range from 0 to 2", exception.getMessage());//assertEquals

    }

    @Test
    void testSetDoorStatusWhenDoorStatusIsInRange() {
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, floorButtons);

        elevator.setDoorStatus(1);
        assertEquals(1, elevator.getDoorStatus());

        elevator.setDoorStatus(2);
        assertEquals(2, elevator.getDoorStatus());
    }

    @Test
    void testSetDoorStatusWhenDoorStatusIsOutOfRange() {
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator = new Elevator(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, floorButtons);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> elevator.setDoorStatus(5));

        assertEquals("Set: Door status can either be 1 or 2", exception.getMessage());//assertEquals
    }

    @Test
    void testCopyValues() {
        List<Boolean> floorButtons1 = new ArrayList<>();
        floorButtons1.add(false);
        List<Boolean> floorButtons2 = new ArrayList<>();
        floorButtons2.add(true);
        List<Floor> floors = new ArrayList<>();
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, false);
        floors.add(floor1);
        floors.add(floor2);
        Floor floor3 = new Floor(0, true, false);
        Floor floor4 = new Floor(1, true, true);

        List<Integer> listOfServicedFloors1 = new ArrayList<>();
        listOfServicedFloors1.add(floor1.getNumber());
        List<Integer> listOfServicedFloors2 = new ArrayList<>();
        listOfServicedFloors2.add(floor2.getNumber());

        Elevator elevator1 = new Elevator(1, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor2.getNumber(), listOfServicedFloors1, floorButtons1);
        Elevator elevator2 = new Elevator(2, 2, 2, 2, floor4.getNumber(), 2, 2, 2, 2, floor3.getNumber(), listOfServicedFloors2, floorButtons2);

        elevator1.copyValues(elevator2, floors);

        assertNotSame(elevator1, elevator2);
        assertEquals(2, elevator1.getCommitedDirection());
        assertEquals(2, elevator1.getAcceleration());
        assertEquals(2, elevator1.getDoorStatus());
        assertEquals(1, elevator1.getFloor());
        assertEquals(2, elevator1.getPosition());
        assertEquals(2, elevator1.getCommitedDirection());
        assertEquals(2, elevator1.getSpeed());
        assertEquals(2, elevator1.getWeight());
        assertEquals(2, elevator1.getCapacity());
        assertEquals(0, elevator1.getTarget());
        assertTrue(elevator1.servicesFloor(1));
        assertFalse(elevator1.servicesFloor(0));
        assertTrue(elevator1.getFloorButtonStatus(0));
    }
}
