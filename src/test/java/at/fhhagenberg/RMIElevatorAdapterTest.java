package at.fhhagenberg;

import at.fhhagenberg.elevator.AutomaticStateController;
import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.model.Building;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import sqelevator.IElevator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RMIElevatorAdapterTest {

    IElevator interfaceMock;
    RMIElevatorAdapter simulator;
    Registry rmiRegistry;

    @BeforeAll
    void before() throws AlreadyBoundException, RemoteException {
        interfaceMock = mock(IElevator.class, Mockito.withSettings().serializable());
        rmiRegistry = LocateRegistry.createRegistry(1100);
        rmiRegistry.bind("ElevatorSimTest", interfaceMock);

        simulator = new RMIElevatorAdapter("rmi://localhost:1100/ElevatorSimTest");
    }

    @Test
    void testReconnectAfterRemoteException() throws RemoteException, InterruptedException, AlreadyBoundException {
        await().atMost(300, TimeUnit.MILLISECONDS).until(() -> simulator.isConnected());
        assertTrue(simulator.isConnected());

        try {
            rmiRegistry.unbind("ElevatorSimTest");
        } catch (NotBoundException e) {}
        simulator.reconnect();
        await().atMost(300, TimeUnit.MILLISECONDS).until(() -> !simulator.isConnected());
        assertFalse(simulator.isConnected());

        rmiRegistry.bind("ElevatorSimTest", interfaceMock);
        await().atMost(300, TimeUnit.MILLISECONDS).until(() -> simulator.isConnected());
        assertTrue(simulator.isConnected());
    }

    @Test
    void testSetCommittedDirection() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        RMIElevatorAdapter rmiElevatorAdapter=new RMIElevatorAdapter(interfaceMock);
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> direction = ArgumentCaptor.forClass(Integer.class);

        rmiElevatorAdapter.setCommitedDirection(5,4);

        verify(interfaceMock, times(1)).setCommittedDirection(elevatorCaptor.capture(), direction.capture());
        assertEquals(5, elevatorCaptor.getValue());
        assertEquals(4, direction.getValue());
    }

    @Test
    void testSetTarget() throws RemoteException {
        IElevator interfaceMock = mock(IElevator.class);
        RMIElevatorAdapter rmiElevatorAdapter=new RMIElevatorAdapter(interfaceMock);
        ArgumentCaptor<Integer> elevatorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> target = ArgumentCaptor.forClass(Integer.class);

        rmiElevatorAdapter.setCommitedDirection(6,7);

        verify(interfaceMock, times(1)).setCommittedDirection(elevatorCaptor.capture(), target.capture());
        assertEquals(6, elevatorCaptor.getValue());
        assertEquals(7, target.getValue());
    }

}
