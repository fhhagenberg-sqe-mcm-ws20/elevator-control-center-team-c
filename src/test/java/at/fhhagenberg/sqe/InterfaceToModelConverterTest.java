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
    public void testConvertIfGetCommitedDirectionsIsCalled() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter imodelc = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(5);
        when(interfaceMock.getFloorNum()).thenReturn(1);
        Building b = imodelc.convert();
        verify(interfaceMock, times(5)).getCommittedDirection(anyInt());
    }

    @Test
    public void testConvertIfAllServicedFloorsIsSavedIntoTheDataModel() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        InterfaceToModelConverter imodelc = new InterfaceToModelConverter(interfaceMock);
        when(interfaceMock.getElevatorNum()).thenReturn(1);
        when(interfaceMock.getFloorNum()).thenReturn(5);
        when(interfaceMock.getServicesFloors(0, 0)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 2)).thenReturn(true);
        when(interfaceMock.getServicesFloors(0, 4)).thenReturn(true);
        Building b = imodelc.convert();
        List<Floor> servicedFloors = b.getElevators().get(0).getServicedFloors();
        assertEquals(3, servicedFloors.size());
        List<Integer> servicedFloorsNumber = new ArrayList<>();
        for (Floor floor : servicedFloors) {
            servicedFloorsNumber.add(floor.getNumber());
        }
        assertTrue(servicedFloorsNumber.contains(0));
        assertTrue(servicedFloorsNumber.contains(2));
        assertTrue(servicedFloorsNumber.contains(4));
        assertFalse(servicedFloorsNumber.contains(1));
    }
}
