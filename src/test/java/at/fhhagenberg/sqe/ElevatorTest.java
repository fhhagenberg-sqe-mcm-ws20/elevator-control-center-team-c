package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElevatorTest {
    @Test
    public void testGetServicedFloorsWhenThereAreFloorButtons() {
        HashMap<Floor, Boolean> floorButtons = new HashMap();
        Floor floor0 = new Floor(0, false, false, 80);
        Floor floor1 = new Floor(1, false, false, 80);
        Floor floor2 = new Floor(2, false, false, 80);
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
        HashMap<Floor, Boolean> floorButtons = new HashMap();
        Elevator elevator = new Elevator(1, 1, 1, 1, null, 1, 1, 1, 1, null, floorButtons);
        List<Floor> servicedFloors = elevator.getServicedFloors();
        assertEquals(0, servicedFloors.size());
    }
}
