package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.model.IElevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterfaceToModelConverterTest {
    InterfaceToModelConverter interfaceToModelConverter;
    IElevator interfaceMock;

    @BeforeEach
    public void beforeEach() {
        interfaceMock = mock(IElevator.class);
        interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
    }

    @Test
    public void testConvertIfItCreatesRightAmountOfElevators() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);

        Building building = interfaceToModelConverter.convert();

        assertEquals(5, building.getNumberOfElevators());
    }

    @Test
    public void testConvertIfNumberIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);

        Building building = interfaceToModelConverter.convert();

        assertEquals(0, building.getElevator(0).getNumber());
    }

    @Test
    public void testConvertIfCommitedDirectionIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getCommitedDirection());
    }

    @Test
    public void testConvertIfAccelerationIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorAccel(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getAcceleration());
    }

    @Test
    public void testConvertIfDoorStatusIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getDoorStatus());
    }

    @Test
    public void testConvertIfFloorIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorFloor(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getFloor().getNumber());
    }

    @Test
    public void testConvertIfPositionIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorPosition(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getPosition());
    }

    @Test
    public void testConvertIfSpeedIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorSpeed(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getSpeed());
    }

    @Test
    public void testConvertIfWeightIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getWeight());
    }

    @Test
    public void testConvertIfCapacityIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorCapacity(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getCapacity());
    }

    @Test
    public void testConvertIfTargetFloorIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getTarget(0)).thenReturn(2);

        Building building = interfaceToModelConverter.convert();

        assertEquals(2, building.getElevator(0).getTarget().getNumber());
    }

    @Test
    public void testConvertIfAllServicedFloorsAreIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);

        Building building = interfaceToModelConverter.convert();
        List<Floor> servicedFloors = building.getElevator(0).getServicedFloors();
        List<Integer> servicedFloorsNumber = new ArrayList<>();
        servicedFloorsNumber.add(servicedFloors.get(0).getNumber());
        servicedFloorsNumber.add(servicedFloors.get(1).getNumber());
        servicedFloorsNumber.add(servicedFloors.get(2).getNumber());

        assertEquals(3, servicedFloors.size());
        assertTrue(servicedFloorsNumber.contains(0));
        assertTrue(servicedFloorsNumber.contains(2));
        assertTrue(servicedFloorsNumber.contains(4));
    }

    @Test
    public void testConvertIfAllFloorButtonsAreConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 0)).thenReturn(true);
        when(interfaceMock.getElevatorButton(0, 2)).thenReturn(false);
        when(interfaceMock.getElevatorButton(0, 4)).thenReturn(true);

        Building building = interfaceToModelConverter.convert();
        List<Floor> servicedFloors = building.getElevator(0).getServicedFloors();
        assertEquals(3, servicedFloors.size());

        for (Floor floor : servicedFloors) {
            if (floor.getNumber() == 0) {
                assertTrue(building.getElevator(0).getFloorButtonStatus(floor));

            } else if (floor.getNumber() == 2) {
                assertFalse(building.getElevator(0).getFloorButtonStatus(floor));

            } else if (floor.getNumber() == 4) {
                assertTrue(building.getElevator(0).getFloorButtonStatus(floor));

            }
        }
    }

    @Test
    public void testConvertIfItCreatesRightAmountOfFloors() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(5);

        Building building = interfaceToModelConverter.convert();

        assertEquals(5, building.getNumberOfFloors());
    }

    @Test
    public void testConvertIfButtonDownIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(2);
        when(interfaceMock.getFloorButtonDown(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonDown(1)).thenReturn(false);

        Building building = interfaceToModelConverter.convert();

        assertTrue(building.getFloor(0).isButtonDown());
        assertFalse(building.getFloor(1).isButtonDown());
    }

    @Test
    public void testConvertIfButtonUpIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(2);
        when(interfaceMock.getFloorButtonUp(0)).thenReturn(true);
        when(interfaceMock.getFloorButtonUp(1)).thenReturn(false);

        Building building = interfaceToModelConverter.convert();

        assertTrue(building.getFloor(0).isButtonUp());
        assertFalse(building.getFloor(1).isButtonUp());
    }

    @Test
    public void testConvertIfHeightIsConvertedCorrectly() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenReturn(0);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getFloorHeight()).thenReturn(150);

        Building building = interfaceToModelConverter.convert();

        assertEquals(150, building.getFloorHeight());
    }

    @Test
    public void testConvertIfRemoteExceptionIsNotCaught() throws RemoteException {
        when(interfaceMock.getElevatorNum()).thenThrow(RemoteException.class);

        assertThrows(RemoteException.class, interfaceToModelConverter::convert);
    }
}
