package at.fhhagenberg.sqe;

import at.fhhagenberg.sqe.converter.InterfaceToModelConverter;
import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterfaceToModelConverterTest {
    @Test
    public void testConvertIfGetCommitedDirectionsIsCalledAccordingToTheNumberOfElevators() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        interfaceToModelConverter.convert();
        verify(interfaceMock, times(5)).getCommittedDirection(anyInt());
    }

    @Test
    public void testConvertIfNumberIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        Building building = interfaceToModelConverter.convert();
        assertEquals(0, building.getElevator(0).getNumber());
    }

    @Test
    public void testConvertIfCommitedDirectionIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getCommitedDirection());
    }

    @Test
    public void testConvertIfAccelerationIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorAccel(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getAcceleration());
    }

    @Test
    public void testConvertIfDoorStatusIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getDoorStatus());
    }

    @Test
    public void testConvertIfFloorIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorFloor(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getFloor().getNumber());
    }

    @Test
    public void testConvertIfPositionIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorPosition(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getPosition());
    }

    @Test
    public void testConvertIfSpeedIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorSpeed(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getSpeed());
    }

    @Test
    public void testConvertIfWeightIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getWeight());
    }

    @Test
    public void testConvertIfCapacityIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getElevatorCapacity(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getCapacity());
    }

    @Test
    public void testConvertIfTargetFloorIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(3);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getTarget(0)).thenReturn(2);
        Building building = interfaceToModelConverter.convert();
        assertEquals(2, building.getElevator(0).getTarget().getNumber());
    }

    @Test
    public void testConvertIfAllServicedFloorsAreIsConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getElevatorDoorStatus(anyInt())).thenReturn(1);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);
        Building building = interfaceToModelConverter.convert();
        List<Floor> servicedFloors = building.getElevator(0).getServicedFloors();
        assertEquals(3, servicedFloors.size());
        List<Integer> servicedFloorsNumber = new ArrayList<>();
        for (Floor floor : servicedFloors) {
            servicedFloorsNumber.add(floor.getNumber());
        }
        assertTrue(servicedFloorsNumber.contains(0));
        assertTrue(servicedFloorsNumber.contains(2));
        assertTrue(servicedFloorsNumber.contains(4));
    }

    @Test
    public void testConvertIfAllFloorButtonsAreConvertedCorrectly() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
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
    public void testConvertIfRemoteExceptionIsNotCaught() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter interfaceToModelConverter = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenThrow(RemoteException.class);
        assertThrows(RemoteException.class, interfaceToModelConverter::convert);
    }
}
