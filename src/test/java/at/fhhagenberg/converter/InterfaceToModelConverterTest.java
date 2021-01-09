package at.fhhagenberg.converter;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InterfaceToModelConverterTest {
    InterfaceToModelConverter interfaceToModelConverter;
    IElevator interfaceMock;

    @BeforeEach
    void beforeEach() {
        interfaceMock = mock(IElevator.class);
        interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
    }

    @Test
    void testConvertIfItCreatesRightAmountOfElevators() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(5, building.getNumberOfElevators());
    }

    @Test
    void testConvertIfNumberIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(0, building.getElevator(0).getNumber());
    }

    @Test
    void testConvertIfCommitedDirectionIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getCommitedDirection());
    }

    @Test
    void testConvertIfAccelerationIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorAccel(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getAcceleration());
    }

    @Test
    void testConvertIfDoorStatusIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getDoorStatus());
    }

    @Test
    void testConvertIfFloorIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorFloor(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getFloor());
    }

    @Test
    void testConvertIfPositionIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorPosition(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getPosition());
    }

    @Test
    void testConvertIfSpeedIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorSpeed(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getSpeed());
    }

    @Test
    void testConvertIfWeightIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getWeight());
    }

    @Test
    void testConvertIfCapacityIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorCapacity(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getCapacity());
    }

    @Test
    void testConvertIfTargetFloorIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getTarget(0)).thenReturn(2);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(2, building.getElevator(0).getTarget());
    }

    @Test
    void testConvertIfAllServicedFloorsAreIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        List<Integer> servicedFloors = building.getElevator(0).getListOfServicedFloors();
        List<Integer> servicedFloorsNumber = new ArrayList<>();
        servicedFloorsNumber.add(servicedFloors.get(0));
        servicedFloorsNumber.add(servicedFloors.get(1));
        servicedFloorsNumber.add(servicedFloors.get(2));

        assertEquals(3, servicedFloors.size());
        assertTrue(servicedFloorsNumber.contains(0));
        assertTrue(servicedFloorsNumber.contains(2));
        assertTrue(servicedFloorsNumber.contains(4));
    }

    @Test
    void testConvertIfAllFloorButtonsAreConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 0)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 2)).thenReturn(false);
        when(interfaceMock.getElevatorButton(0, 4)).thenReturn(true);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        List<Integer> servicedFloors = building.getElevator(0).getListOfServicedFloors();
        assertEquals(3, servicedFloors.size());

        for (int floor : servicedFloors) {
            if (floor == 0) {
                assertTrue(building.getElevator(0).getFloorButtonStatus(floor));

            } else if (floor == 2) {
                assertFalse(building.getElevator(0).getFloorButtonStatus(floor));

            } else if (floor == 4) {
                assertTrue(building.getElevator(0).getFloorButtonStatus(floor));
            }
        }
    }

    @Test
    void testConvertIfItCreatesRightAmountOfFloors() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(5);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(5, building.getNumberOfFloors());
    }

    @Test
    void testConvertIfButtonDownIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(2);
        when(interfaceMock.getFloorButtonDown(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonDown(1)).thenReturn(false);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertTrue(building.getFloor(0).isButtonDown());
        assertFalse(building.getFloor(1).isButtonDown());
    }

    @Test
    void testConvertIfButtonUpIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(2);
        when(interfaceMock.getFloorButtonUp(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonUp(1)).thenReturn(false);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertTrue(building.getFloor(0).isButtonUp());
        assertFalse(building.getFloor(1).isButtonUp());
    }

    @Test
    void testConvertIfHeightIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getFloorHeight()).thenReturn(150);

        Building building = new Building();
        interfaceToModelConverter.convert(building);

        assertEquals(150, building.getFloorHeight());
    }

    @Test
    void testConvertIfOverridingValueCorrectly() throws RemoteException {
        Floor floor1 = new Floor(0, false, false);
        Floor floor2 = new Floor(1, false, false);
        Floor floor3 = new Floor(2, false, false);
        Floor floor5 = new Floor(4, false, false);
        Floor floor6 = new Floor(5, false, false);

        List<Boolean> floorButtons = new ArrayList<>();
        floorButtons.add(false);
        floorButtons.add(false);
        floorButtons.add(false);
        floorButtons.add(false);
        floorButtons.add(false);
        List<Integer> listOfServicedFloors = new ArrayList<>();
        listOfServicedFloors.add(floor1.getNumber());
        listOfServicedFloors.add(floor2.getNumber());
        Elevator elevator1 = new Elevator(0, 1, 1, 1, floor1.getNumber(), 1, 1, 1, 1, floor2.getNumber(), listOfServicedFloors, floorButtons);
        List<Elevator> elevators = new ArrayList<>();
        List<Floor> floors = new ArrayList<>();
        elevators.add(elevator1);
        floors.add(floor1);
        floors.add(floor2);

        Building building = new Building(elevators, floors, 50);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getElevatorAccel(0)).thenReturn(2);
        when(interfaceMock.getElevatorDoorStatus(0)).thenReturn(2);
        when(interfaceMock.getElevatorFloor(0)).thenReturn(1);
        when(interfaceMock.getElevatorPosition(0)).thenReturn(2);
        when(interfaceMock.getElevatorSpeed(0)).thenReturn(2);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(2);
        when(interfaceMock.getElevatorCapacity(0)).thenReturn(2);
        when(interfaceMock.getTarget(0)).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(7);
        when(interfaceMock.getFloorButtonDown(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonUp(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonDown(1)).thenReturn(true);
        when(interfaceMock.getFloorButtonUp(1)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 1)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 3)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 6)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 0)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 1)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 3)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 4)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 6)).thenReturn(true);
        when(interfaceMock.getFloorButtonUp(1)).thenReturn(true);
        when(interfaceMock.getFloorHeight()).thenReturn(150);

        interfaceToModelConverter.convert(building);

        assertTrue(building.getFloor(0).isButtonDown());
        assertTrue(building.getFloor(0).isButtonUp());
        assertEquals(0, building.getElevator(0).getNumber());
        assertEquals(2, building.getElevator(0).getCommitedDirection());
        assertEquals(2, building.getElevator(0).getAcceleration());
        assertEquals(2, building.getElevator(0).getDoorStatus());
        assertEquals(1, building.getElevator(0).getFloor());
        assertEquals(2, building.getElevator(0).getPosition());
        assertEquals(2, building.getElevator(0).getCommitedDirection());
        assertEquals(2, building.getElevator(0).getSpeed());
        assertEquals(2, building.getElevator(0).getWeight());
        assertEquals(2, building.getElevator(0).getCapacity());
        assertEquals(0, building.getElevator(0).getTarget());
        assertTrue(building.getElevator(0).servicesFloor(0));
        assertTrue(building.getElevator(0).servicesFloor(1));
        assertFalse(building.getElevator(0).servicesFloor(2));
        assertTrue(building.getElevator(0).servicesFloor(3));
        assertTrue(building.getElevator(0).servicesFloor(4));
        assertFalse(building.getElevator(0).servicesFloor(5));
        assertTrue(building.getElevator(0).servicesFloor(6));
        assertTrue(building.getElevator(0).getFloorButtonStatus(0));
        assertTrue(building.getElevator(0).getFloorButtonStatus(1));
        assertTrue(building.getElevator(0).getFloorButtonStatus(3));
        assertTrue(building.getElevator(0).getFloorButtonStatus(4));
        assertTrue(building.getElevator(0).getFloorButtonStatus(6));
        assertEquals(150, building.getFloorHeight());
    }

    @Test
    void testConvertIfBuildingObjectIsNotThrownAwayWhenClockTimesMatch() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L).thenReturn(1L);

        interfaceToModelConverter.convert(building);

        assertFalse(building.isEmpty());
    }

    @Test
    void testConvertIfBuildingObjectIsThrownAwayWhenClockTimesDontMatch() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L).thenReturn(2L);

        interfaceToModelConverter.convert(building);

        assertTrue(building.isEmpty());
    }


    @Test
    void testConvertIfReturnsTrueWhenClockTimesMatch() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L).thenReturn(1L);

        assertTrue(interfaceToModelConverter.convert(building));
    }

    @Test
    void testConvertIfReturnsFalseWhenClockTimesMatch() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L).thenReturn(2L);

        assertFalse(interfaceToModelConverter.convert(building));
    }

    @Test
    void testConvertIfApiFunctionsAreNotCalledIfItIsTheSameClockTickAsBefore() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L);

        interfaceToModelConverter.convert(building);

        interfaceToModelConverter.convert(building);

        verify(interfaceMock, times(1)).getElevatorNum();
    }

    @Test
    void testConvertIfApiFunctionsAreCalledIfItIsNotTheSameClockTickAsBefore() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L);

        interfaceToModelConverter.convert(building);

        when(interfaceMock.getClockTick()).thenReturn(2L);

        interfaceToModelConverter.convert(building);

        verify(interfaceMock, times(2)).getElevatorNum();
    }

    @Test
    void testConvertIfItReturnsFalseWhenTicksAreTheSame() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L);

        assertTrue(interfaceToModelConverter.convert(building));

        assertFalse(interfaceToModelConverter.convert(building));
    }

    @Test
    void testConvertIfItReturnsTrueWhenTicksAreTheSame() throws RemoteException {
        Building building = new Building();
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getClockTick()).thenReturn(1L);

        assertTrue(interfaceToModelConverter.convert(building));

        when(interfaceMock.getClockTick()).thenReturn(2L);

        assertTrue(interfaceToModelConverter.convert(building));
    }


    @Test
    void testConvertIfRemoteExceptionIsNotCaught() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenThrow(RemoteException.class);

        assertThrows(RemoteException.class, () -> {
            interfaceToModelConverter.convert(null);
        });
    }
}
