package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.viewmodel.INotifyModelSizeChangedListener;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuildingTest {

    @Test
    void testConstructorWhenFloorHeightIsNotNegative() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        assertEquals(100, building.getFloorHeight());
    }

    @Test
    void testConstructorWhenFloorHeightIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Building(new ArrayList<>(), new ArrayList<>(), -100));

        assertEquals("Constructor: Floor height can't be negative", exception.getMessage());//assertEquals
    }

    @Test
    void testSetFloorHeightWhenFloorHeightIsNegative() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                building.setFloorHeight(-100));

        assertEquals("Setter: Floor height can't be negative", exception.getMessage());//assertEquals
    }

    @Test
    void testSetFloorHeightWhenFloor() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        building.setFloorHeight(100);

        assertEquals(100, building.getFloorHeight());
    }

    @Test
    void testIsEmptyIfBuildingThereAreNoElevatorsFloorsAndFloorHeightIsZero() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 0);

        assertTrue(building.isEmpty());
    }

    @Test
    void testIsEmptyIfBuildingThereAreNoElevatorsFloorsAndFloorHeightIsNotZero() {
        Building building = new Building(new ArrayList<>(), new ArrayList<>(), 100);

        assertFalse(building.isEmpty());
    }

    @Test
    void testIsEmptyIfBuildingThereAreElevatorsButNoFloorsAndFloorHeightIsZero() {
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(new Elevator(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, null, new ArrayList<>()));
        Building building = new Building(elevators, new ArrayList<>(), 100);

        assertFalse(building.isEmpty());
    }

    @Test
    void testIsEmptyIfBuildingThereAreNoElevatorsButFloorsAndFloorHeightIsZero() {
        List<Floor> floors = new ArrayList<>();
        floors.add(new Floor(0, false, false));
        Building building = new Building(new ArrayList<>(), floors, 100);

        assertFalse(building.isEmpty());
    }

    @Test
    void testCopyValues() {
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        Floor floor11 = new Floor(0, true, false);
        Floor floor12 = new Floor(1, true, true);
        List<Boolean> floorButtons1 = new ArrayList<>();
        floorButtons1.add(true);
        floorButtons1.add(true);
        floorButtons1.add(true);
        List<Boolean> floorButtons2 = new ArrayList<>();
        floorButtons2.add(true);
        floorButtons2.add(true);
        List<Integer> listOfServicedFloors1 = new ArrayList<>();
        listOfServicedFloors1.add(floor01.getNumber());
        listOfServicedFloors1.add(floor02.getNumber());
        listOfServicedFloors1.add(floor03.getNumber());
        List<Integer> listOfServicedFloors2 = new ArrayList<>();
        listOfServicedFloors2.add(floor11.getNumber());
        listOfServicedFloors2.add(floor12.getNumber());
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors1, floorButtons1);
        Elevator elevator2 = new Elevator(1, 2, 2, 2, floor12.getNumber(), 2, 2, 2, 2, floor11.getNumber(), listOfServicedFloors2, floorButtons2);
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
        assertEquals(1, building1.getElevator(0).getFloor());
        assertEquals(2, building1.getElevator(0).getPosition());
        assertEquals(2, building1.getElevator(0).getCommitedDirection());
        assertEquals(2, building1.getElevator(0).getSpeed());
        assertEquals(2, building1.getElevator(0).getWeight());
        assertEquals(2, building1.getElevator(0).getCapacity());
        assertEquals(0, building1.getElevator(0).getTarget());
        assertTrue(building1.getElevator(0).servicesFloor(0));
        assertTrue(building1.getElevator(0).servicesFloor(1));
        assertFalse(building1.getElevator(0).servicesFloor(2));
        assertTrue(building1.getElevator(0).getFloorButtonStatus(0));
        assertTrue(building1.getElevator(0).getFloorButtonStatus(1));
        assertEquals(100, building1.getFloorHeight());
    }

    @Test
    void testCopyValuesIfElevatorsAreAddedIfThereAreNewElevators() {
        Floor floor1 = new Floor(0, false, false);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        Elevator elevator2 = new Elevator(1, 2, 2, 2, floor1.getNumber(), 2, 2, 2, 2, floor1.getNumber(), null, floorButtons);
        List<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator1);
        elevators2.add(elevator2);

        Building building1 = new Building(elevators1, floors, 100);
        Building building2 = new Building(elevators2, floors, 100);

        assertEquals(1, building1.getNumberOfElevators());
        building1.copyValues(building2);
        assertEquals(2, building1.getNumberOfElevators());
    }

    @Test
    void testCopyValuesIfFloorsAreAddedIfThereAreNewFloors() {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, true);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(floor1);
        List<Floor> floors2 = new ArrayList<>();
        floors2.add(floor1);
        floors2.add(floor2);
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);

        Building building1 = new Building(elevators, floors1, 100);
        Building building2 = new Building(elevators, floors2, 100);

        assertEquals(1, building1.getNumberOfFloors());
        building1.copyValues(building2);
        assertEquals(2, building1.getNumberOfFloors());
    }

    @Test
    void testCopyValuesIfElevatorsAreAddedIfChangeListenersAreCalled() {
        Floor floor1 = new Floor(0, false, false);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        Elevator elevator2 = new Elevator(1, 2, 2, 2, floor1.getNumber(), 2, 2, 2, 2, floor1.getNumber(), null, floorButtons);
        List<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator1);
        elevators2.add(elevator2);

        INotifyModelSizeChangedListener changeListenerMock = mock(INotifyModelSizeChangedListener.class);

        Building building1 = new Building(elevators1, floors, 100);
        building1.addChangeListener(changeListenerMock);
        Building building2 = new Building(elevators2, floors, 100);
        building1.copyValues(building2);

        verify(changeListenerMock, times(1)).modelChanged();
    }

    @Test
    void testCopyValuesIfFloorsAreAddedIfChangeListenersAreCalled() {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, true);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(floor1);
        List<Floor> floors2 = new ArrayList<>();
        floors2.add(floor1);
        floors2.add(floor2);
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        INotifyModelSizeChangedListener changeListenerMock = mock(INotifyModelSizeChangedListener.class);
        Building building1 = new Building(elevators, floors1, 100);
        building1.addChangeListener(changeListenerMock);
        Building building2 = new Building(elevators, floors2, 100);

        building1.copyValues(building2);

        verify(changeListenerMock, times(1)).modelChanged();
    }

    @Test
    void testCopyValuesIfFloorsAreAddedIfChangeListenersAreCalledWhenBuildingWasEmpty() {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, true);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        List<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        INotifyModelSizeChangedListener changeListenerMock = mock(INotifyModelSizeChangedListener.class);
        Building building1 = new Building();
        building1.addChangeListener(changeListenerMock);
        Building building2 = new Building(elevators, floors, 100);

        building1.copyValues(building2);

        verify(changeListenerMock, times(1)).modelChanged();
    }

    @Test
    void testCopyValuesIfFloorsAreRemoved() {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, true);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(floor1);
        floors1.add(floor2);
        List<Floor> floors2 = new ArrayList<>();
        floors2.add(floor1);
        List<Elevator> elevators = new ArrayList<>();
        elevators.add(elevator1);
        INotifyModelSizeChangedListener changeListenerMock = mock(INotifyModelSizeChangedListener.class);
        Building building1 = new Building(elevators, floors1, 100);
        building1.addChangeListener(changeListenerMock);
        Building building2 = new Building(elevators, floors2, 100);

        building1.copyValues(building2);

        assertEquals(1, building1.getNumberOfFloors());
    }

    @Test
    void testCopyValuesIfElevatorsAreRemoved() {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, true);
        List<Boolean> floorButtons = new ArrayList<>();
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor1.getNumber(), null, floorButtons);
        List<Floor> floors = new ArrayList<>();
        floors.add(floor1);
        floors.add(floor2);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        elevators1.add(elevator2);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator1);
        INotifyModelSizeChangedListener changeListenerMock = mock(INotifyModelSizeChangedListener.class);
        Building building1 = new Building(elevators1, floors, 100);
        building1.addChangeListener(changeListenerMock);
        Building building2 = new Building(elevators2, floors, 100);

        building1.copyValues(building2);

        assertEquals(1, building1.getNumberOfElevators());
    }
}
