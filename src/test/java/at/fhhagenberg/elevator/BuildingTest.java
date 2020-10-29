package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        assertEquals("Constructor: Floor height can't be negative", exception.getMessage());//assertEquals
    }

    @Test
    public void testSetFloorHeightWhenFloorHeightIsNegative() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                building.setFloorHeight(-100));

        assertEquals("Setter: Floor height can't be negative", exception.getMessage());//assertEquals
    }

    @Test
    public void testIsEmptyIfBuildingThereAreNoElevatorsFloorsAndFloorHeightIsZero() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 0);

        assertTrue(building.isEmpty());
    }

    @Test
    public void testIsEmptyIfBuildingThereAreNoElevatorsFloorsAndFloorHeightIsNotZero() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        assertFalse(building.isEmpty());
    }

    @Test
    public void testIsEmptyIfBuildingThereAreElevatorsButNoFloorsAndFloorHeightIsZero() {
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(new Elevator(0, 1, 1, 1, null, 1, 1, 1, 1, null, null));
        Building building = new Building(elevators, new ArrayList<>(), 100);

        assertFalse(building.isEmpty());
    }

    @Test
    public void testIsEmptyIfBuildingThereAreNoElevatorsButFloorsAndFloorHeightIsZero() {
        List<Floor> floors = new ArrayList<>();
        floors.add(new Floor(0, false, false));
        Building building = new Building(new ArrayList<>(), floors, 100);

        assertFalse(building.isEmpty());
    }

    @Test
    public void testCopyValues() {
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        Floor floor11 = new Floor(0, true, false);
        Floor floor12 = new Floor(1, true, true);
        HashMap<Floor, Boolean> floorButtons1 = new HashMap<>();
        floorButtons1.put(floor01, false);
        floorButtons1.put(floor02, false);
        floorButtons1.put(floor03, false);
        HashMap<Floor, Boolean> floorButtons2 = new HashMap<>();
        floorButtons2.put(floor11, true);
        floorButtons2.put(floor12, true);
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor01, 1, 1, 1, 1, floor02, floorButtons1);
        Elevator elevator2 = new Elevator(1, 2, 2, 2, floor12, 2, 2, 2, 2, floor11, floorButtons2);
        List<Elevator> elevators1 = new ArrayList<>();
        List<Floor> floors1 = new ArrayList<>();
        List<Elevator> elevators2 = new ArrayList<>();
        List<Floor> floors2 = new ArrayList<>();
        elevators1.add(elevator1);
        floors1.add(floor01);
        floors1.add(floor02);
        elevators2.add(elevator2);
        floors2.add(floor11);
        floors2.add(floor12);
        Building building1 = new Building(elevators1, floors1, 50);
        Building building2 = new Building(elevators2, floors2, 100);

        building1.copyValues(building2);

        assertNotSame(building1, building2);
        assertNotSame(building1.getFloor(0), building2.getFloor(0));
        assertEquals(building1.getFloor(0), building2.getFloor(0));
        assertTrue(building1.getFloor(0).isButtonUp());
        assertFalse(building1.getFloor(0).isButtonDown());
        assertTrue(building1.getFloor(1).isButtonDown());
        assertTrue(building1.getFloor(1).isButtonUp());
        assertNotSame(building1.getElevator(0), building2.getElevator(0));
        assertEquals(0, building1.getElevator(0).getNumber());
        assertEquals(2, building1.getElevator(0).getCommitedDirection());
        assertEquals(2, building1.getElevator(0).getAcceleration());
        assertEquals(2, building1.getElevator(0).getDoorStatus());
        assertNotSame(building1.getElevator(0).getFloor(), building2.getElevator(0).getFloor());
        assertEquals(1, building1.getElevator(0).getFloor().getNumber());
        assertEquals(2, building1.getElevator(0).getPosition());
        assertEquals(2, building1.getElevator(0).getCommitedDirection());
        assertEquals(2, building1.getElevator(0).getSpeed());
        assertEquals(2, building1.getElevator(0).getWeight());
        assertEquals(2, building1.getElevator(0).getCapacity());
        assertNotSame(building1.getElevator(0).getTarget(), building2.getElevator(0).getTarget());
        assertEquals(0, building1.getElevator(0).getTarget().getNumber());
        assertTrue(building1.getElevator(0).servicesFloor(new Floor(0, true, true)));
        assertTrue(building1.getElevator(0).servicesFloor(new Floor(1, true, true)));
        assertFalse(building1.getElevator(0).servicesFloor(new Floor(2, true, true)));
        assertTrue(building1.getElevator(0).getFloorButtonStatus(new Floor(0, true, true)));
        assertTrue(building1.getElevator(0).getFloorButtonStatus(new Floor(1, true, true)));
        assertThrows(IllegalArgumentException.class, () -> building1.getElevator(0).getFloorButtonStatus(new Floor(2, true, true)));

        assertEquals(100, building1.getFloorHeight());
    }

    @Test
    public void testGetFloorFromFloorsIfTheFloorIsInTheList() {
        List<Floor> floors = new ArrayList<>();
        Floor floor2 = new Floor(1, false, false);
        floors.add(new Floor(0, false, false));
        floors.add(floor2);
        floors.add(new Floor(2, false, false));

        assertSame(floor2, Building.getFloorFromFloors(new Floor(1, false, false), floors));
    }

    @Test
    public void testGetFloorFromFloorsIfExceptionIsThrownIfTheFloorIsntInTheList() {
        List<Floor> floors = new ArrayList<>();
        floors.add(new Floor(0, false, false));
        floors.add(new Floor(2, false, false));

        assertThrows(IllegalArgumentException.class, () ->
                Building.getFloorFromFloors(new Floor(1, false, false), floors));

    }
}
