package at.fhhagenberg;

import at.fhhagenberg.elevator.AutomaticStateController;
import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static sqelevator.IElevator.ELEVATOR_DOORS_CLOSED;
import static sqelevator.IElevator.ELEVATOR_DOORS_OPEN;

/**
 * This test cases try to model every state of the automatic controller
 * Some test cases are also checked in the bigger state test, but we think it makes sense in this case to keep the small ones to find smaller problems more easily
 * In the whole application, these were the only tests which really showed me error of the controller
 * These test were really useful
 */
class AutomaticStateControllerTest {
    @Test
    void testUpdateStateInitialElevatorButtonsOnly() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        new AutomaticStateController(rmiAdapterMock, building);

        verify(rmiAdapterMock, times(1)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        assertEquals(0, elevatorCaptor.getValue());
        assertEquals(1, floorCaptor.getValue());
    }

    @Test
    void testUpdateStateInitialElevatorButtonsOnlyAlreadyOnTarget() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        new AutomaticStateController(rmiAdapterMock, building);
        verify(rmiAdapterMock, times(2)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(0, allCapturedElevators.get(1));
        assertEquals(0, allCapturedFloors.get(0));
        assertEquals(1, allCapturedFloors.get(1));
    }

    @Test
    void testUpdateStateInitialElevatorButtonsOnlyAlreadyOnTargetDoorsClosed() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, ELEVATOR_DOORS_CLOSED, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        new AutomaticStateController(rmiAdapterMock, building);
        verify(rmiAdapterMock, times(1)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(0, allCapturedFloors.get(0));
    }

    @Test
    void testUpdateStateInitialElevatorButtonsOnlyAlreadyOnTargetButNoAdditionalTarget() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        new AutomaticStateController(rmiAdapterMock, building);
        verify(rmiAdapterMock, times(1)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(0, allCapturedFloors.get(0));
    }

    @Test
    void testUpdateStateInitialElevatorButtonsOnlyAlreadyOnTargetElevatorAndFloorButtonAreTheSame() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, true);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        new AutomaticStateController(rmiAdapterMock, building);
        verify(rmiAdapterMock, times(1)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(0, allCapturedFloors.get(0));
    }

    @Test
    void testUpdateStateElevatorButtonsOnly() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);
        List<Boolean> elevatorFloorButtons2 = new ArrayList<>();
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(true);
        elevatorFloorButtons2.add(true);
        Elevator elevator2 = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons2);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator2);
        Building building2 = new Building(elevators2, floors, 50);

        AutomaticStateController as = new AutomaticStateController(rmiAdapterMock, building);
        building.copyValues(building2);
        as.updateState();
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(2)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(0, allCapturedElevators.get(1));
        assertEquals(2, allCapturedFloors.get(0));
        assertEquals(1, allCapturedFloors.get(1));
    }

    @Test
    void testUpdateStateInitialFloorButtonsOnlyOneElevator() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        new AutomaticStateController(rmiAdapterMock, building);
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(1)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        assertEquals(0, elevatorCaptor.getValue());
        assertEquals(1, floorCaptor.getValue());
    }

    @Test
    void testUpdateStateInitialFloorButtonsOnlyNotServicingFloors() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        new AutomaticStateController(rmiAdapterMock, building);
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(0)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
    }

    @Test
    void testUpdateStateInitialFloorButtonsOnlyTwoElevator() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        elevators.add(elevator2);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        Building building = new Building(elevators, floors, 50);

        new AutomaticStateController(rmiAdapterMock, building);
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(2)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());

        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(1, allCapturedFloors.get(0));
        assertEquals(2, allCapturedFloors.get(1));
    }

    @Test
    void testUpdateStateFloorButtonsOnlyTwoElevator() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        Floor floor04 = new Floor(3, false, true);
        Floor floor05 = new Floor(4, false, true);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        listOfServicedFloors.add(3);
        listOfServicedFloors.add(4);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        elevators.add(elevator2);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        floors.add(floor04);
        floors.add(floor05);
        Floor floor11 = new Floor(0, false, true);
        Floor floor12 = new Floor(1, false, true);
        Floor floor13 = new Floor(2, false, true);
        Floor floor14 = new Floor(3, false, false);
        Floor floor15 = new Floor(4, false, false);
        List<Floor> floors2 = new ArrayList<>();
        floors2.add(floor11);
        floors2.add(floor12);
        floors2.add(floor13);
        floors2.add(floor14);
        floors2.add(floor15);
        Building building = new Building(elevators, floors, 50);
        Building building1 = new Building(elevators, floors2, 50);

        AutomaticStateController as = new AutomaticStateController(rmiAdapterMock, building);
        building.copyValues(building1);
        as.updateState();
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(4)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();


        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(0, allCapturedElevators.get(2));
        assertEquals(1, allCapturedElevators.get(3));
        assertEquals(3, allCapturedFloors.get(0));
        assertEquals(4, allCapturedFloors.get(1));
        assertEquals(1, allCapturedFloors.get(2));
        assertEquals(2, allCapturedFloors.get(3));
    }

    @Test
    void testUpdateStateInitialAllButtonsCombinedElevatorButtonNearest() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, false);
        Floor floor03 = new Floor(2, false, false);
        Floor floor04 = new Floor(3, false, true);
        Floor floor05 = new Floor(4, false, true);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(floor01.getNumber());
        listOfServicedFloors.add(floor02.getNumber());
        listOfServicedFloors.add(floor03.getNumber());
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        elevators.add(elevator2);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        floors.add(floor04);
        floors.add(floor05);
        Building building = new Building(elevators, floors, 50);
        new AutomaticStateController(rmiAdapterMock, building);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(2)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();


        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(1, allCapturedFloors.get(0));
        assertEquals(1, allCapturedFloors.get(1));
    }

    @Test
    void testUpdateStateInitialAllButtonsCombinedFloorButtonNearest() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        Floor floor01 = new Floor(0, false, false);
        Floor floor02 = new Floor(1, false, true);
        Floor floor03 = new Floor(2, false, true);
        Floor floor04 = new Floor(3, false, false);
        Floor floor05 = new Floor(4, false, false);
        List<Boolean> elevatorFloorButtons = new ArrayList<>();
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(false);
        elevatorFloorButtons.add(true);
        elevatorFloorButtons.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        listOfServicedFloors.add(3);
        listOfServicedFloors.add(4);
        Elevator elevator = new Elevator(0, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, floor01.getNumber(), 1, 1, 1, 1, floor02.getNumber(), listOfServicedFloors, elevatorFloorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator);
        elevators.add(elevator2);
        floors.add(floor01);
        floors.add(floor02);
        floors.add(floor03);
        floors.add(floor04);
        floors.add(floor05);
        Building building = new Building(elevators, floors, 50);
        new AutomaticStateController(rmiAdapterMock, building);

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(4)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();


        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(0, allCapturedElevators.get(2));
        assertEquals(1, allCapturedElevators.get(3));
        assertEquals(3, allCapturedFloors.get(0));
        assertEquals(3, allCapturedFloors.get(1));
        assertEquals(1, allCapturedFloors.get(2));
        assertEquals(2, allCapturedFloors.get(3));
    }


    @Test
    void testUpdateStateAllButtonsCombined() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(new Floor(0, false, false));
        floors1.add(new Floor(1, false, false));
        floors1.add(new Floor(2, false, false));
        floors1.add(new Floor(3, false, false));
        floors1.add(new Floor(4, false, false));
        floors1.add(new Floor(5, false, true));
        floors1.add(new Floor(6, false, true));
        floors1.add(new Floor(7, false, true));
        floors1.add(new Floor(8, false, false));
        List<Boolean> elevatorFloorButtons1 = new ArrayList<>();
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        listOfServicedFloors.add(3);
        listOfServicedFloors.add(4);
        listOfServicedFloors.add(5);
        listOfServicedFloors.add(6);
        listOfServicedFloors.add(7);
        listOfServicedFloors.add(8);
        Elevator elevator1 = new Elevator(0, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons1);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons1);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        elevators1.add(elevator2);

        List<Floor> floors2 = new ArrayList<>();
        floors2.add(new Floor(0, false, true));
        floors2.add(new Floor(1, false, true));
        floors2.add(new Floor(2, false, true));
        floors2.add(new Floor(3, false, false));
        floors2.add(new Floor(4, false, false));
        floors2.add(new Floor(5, false, false));
        floors2.add(new Floor(6, false, false));
        floors2.add(new Floor(7, false, false));
        floors2.add(new Floor(8, false, false));
        List<Boolean> elevatorFloorButtons2 = new ArrayList<>();
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(true);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);

        Elevator elevator3 = new Elevator(0, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        Elevator elevator4 = new Elevator(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator3);
        elevators2.add(elevator4);

        Building building1 = new Building(elevators1, floors1, 50);
        Building building2 = new Building(elevators2, floors2, 50);
        AutomaticStateController as = new AutomaticStateController(rmiAdapterMock, building1);
        building1.copyValues(building2);
        as.updateState();

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(8)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();

        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(0, allCapturedElevators.get(2));
        assertEquals(1, allCapturedElevators.get(3));
        assertEquals(0, allCapturedElevators.get(4));
        assertEquals(1, allCapturedElevators.get(5));
        assertEquals(0, allCapturedElevators.get(6));
        assertEquals(1, allCapturedElevators.get(7));
        assertEquals(8, allCapturedFloors.get(0));
        assertEquals(8, allCapturedFloors.get(1));
        assertEquals(5, allCapturedFloors.get(2));
        assertEquals(6, allCapturedFloors.get(3));
        assertEquals(3, allCapturedFloors.get(4));
        assertEquals(3, allCapturedFloors.get(5));
        assertEquals(1, allCapturedFloors.get(6));
        assertEquals(2, allCapturedFloors.get(7));
    }

    @Test
    void testUpdateStateAllButtonsCombinedOneElevatorIsManual() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(new Floor(0, false, false));
        floors1.add(new Floor(1, false, false));
        floors1.add(new Floor(2, false, false));
        floors1.add(new Floor(3, false, false));
        floors1.add(new Floor(4, false, false));
        floors1.add(new Floor(5, false, true));
        floors1.add(new Floor(6, false, true));
        floors1.add(new Floor(7, false, true));
        floors1.add(new Floor(8, false, false));
        List<Boolean> elevatorFloorButtons1 = new ArrayList<>();
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(true);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        listOfServicedFloors.add(3);
        listOfServicedFloors.add(4);
        listOfServicedFloors.add(5);
        listOfServicedFloors.add(6);
        listOfServicedFloors.add(7);
        listOfServicedFloors.add(8);
        Elevator elevator1 = new Elevator(0, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons1);
        elevator1.setManualControl(true);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons1);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        elevators1.add(elevator2);

        List<Floor> floors2 = new ArrayList<>();
        floors2.add(new Floor(0, false, true));
        floors2.add(new Floor(1, false, true));
        floors2.add(new Floor(2, false, true));
        floors2.add(new Floor(3, false, false));
        floors2.add(new Floor(4, false, false));
        floors2.add(new Floor(5, false, false));
        floors2.add(new Floor(6, false, false));
        floors2.add(new Floor(7, false, false));
        floors2.add(new Floor(8, false, false));
        List<Boolean> elevatorFloorButtons2 = new ArrayList<>();
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(true);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);

        Elevator elevator3 = new Elevator(0, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        Elevator elevator4 = new Elevator(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator3);
        elevators2.add(elevator4);

        Building building1 = new Building(elevators1, floors1, 50);
        Building building2 = new Building(elevators2, floors2, 50);
        AutomaticStateController as = new AutomaticStateController(rmiAdapterMock, building1);
        building1.copyValues(building2);
        as.updateState();

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(4)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();

        assertEquals(1, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(1, allCapturedElevators.get(2));
        assertEquals(1, allCapturedElevators.get(3));
        assertEquals(8, allCapturedFloors.get(0));
        assertEquals(5, allCapturedFloors.get(1));
        assertEquals(3, allCapturedFloors.get(2));
        assertEquals(1, allCapturedFloors.get(3));
    }

    @Test
    void testUpdateStateElevatorReachedTargetOfOtherElevatorsFloorTarget() {
        RMIElevatorAdapter rmiAdapterMock = mock(RMIElevatorAdapter.class);
        List<Floor> floors1 = new ArrayList<>();
        floors1.add(new Floor(0, false, false));
        floors1.add(new Floor(1, false, false));
        floors1.add(new Floor(2, false, true));
        floors1.add(new Floor(3, false, true));
        List<Boolean> elevatorFloorButtons1 = new ArrayList<>();
        elevatorFloorButtons1.add(true);
        elevatorFloorButtons1.add(true);
        elevatorFloorButtons1.add(false);
        elevatorFloorButtons1.add(false);
        List<Boolean> elevatorFloorButtons2 = new ArrayList<>();
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        elevatorFloorButtons2.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(0);
        listOfServicedFloors.add(1);
        listOfServicedFloors.add(2);
        listOfServicedFloors.add(3);
        Elevator elevator1 = new Elevator(0, 1, 1, ELEVATOR_DOORS_OPEN, 0, 0, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons1);
        Elevator elevator2 = new Elevator(1, 1, 1, 1, 0, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        List<Elevator> elevators1 = new ArrayList<>();
        elevators1.add(elevator1);
        elevators1.add(elevator2);
        List<Floor> floors2 = new ArrayList<>();
        floors2.add(new Floor(0, false, false));
        floors2.add(new Floor(1, false, false));
        floors2.add(new Floor(2, false, false));
        floors2.add(new Floor(3, false, false));
        List<Boolean> elevatorFloorButtons3 = new ArrayList<>();
        elevatorFloorButtons3.add(false);
        elevatorFloorButtons3.add(false);
        elevatorFloorButtons3.add(true);
        elevatorFloorButtons3.add(false);
        Elevator elevator3 = new Elevator(0, 1, 1, ELEVATOR_DOORS_OPEN, 1, 1, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons3);
        List<Elevator> elevators2 = new ArrayList<>();
        elevators2.add(elevator3);
        elevators2.add(elevator2);
        Elevator elevator4 = new Elevator(0, 1, 1, ELEVATOR_DOORS_OPEN, 2, 3, 1, 1, 1, 1, listOfServicedFloors, elevatorFloorButtons2);
        List<Elevator> elevators3 = new ArrayList<>();
        elevators3.add(elevator4);
        elevators3.add(elevator2);

        Building building1 = new Building(elevators1, floors1, 50);
        Building building2 = new Building(elevators2, floors2, 50);
        Building building3 = new Building(elevators3, floors2, 50);
        AutomaticStateController as = new AutomaticStateController(rmiAdapterMock, building1);
        building1.copyValues(building2);
        as.updateState();
        building1.copyValues(building3);
        as.updateState();

        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> floorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(rmiAdapterMock, times(5)).setTarget(elevatorCaptor.capture(), floorCaptor.capture());
        List<Integer> allCapturedElevators = elevatorCaptor.getAllValues();
        List<Integer> allCapturedFloors = floorCaptor.getAllValues();
        assertEquals(0, allCapturedElevators.get(0));
        assertEquals(1, allCapturedElevators.get(1));
        assertEquals(0, allCapturedElevators.get(2));
        assertEquals(0, allCapturedElevators.get(3));
        assertEquals(1, allCapturedElevators.get(4));
        assertEquals(0, allCapturedFloors.get(0));
        assertEquals(2, allCapturedFloors.get(1));
        assertEquals(1, allCapturedFloors.get(2));
        assertEquals(2, allCapturedFloors.get(3));
        assertEquals(3, allCapturedFloors.get(4));
    }
}
