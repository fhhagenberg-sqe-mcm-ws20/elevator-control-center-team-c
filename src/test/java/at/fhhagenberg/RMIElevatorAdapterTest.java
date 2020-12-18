package at.fhhagenberg;

import at.fhhagenberg.elevator.App;
import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import javafx.application.Platform;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import sqelevator.IElevator;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RMIElevatorAdapterTest {

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
    void testReconnectAfterRemoteException() throws RemoteException, InterruptedException {
            doThrow(RemoteException.class).when(interfaceMock).setTarget(anyInt(), anyInt());
            rmiRegistry.rebind("ElevatorSimTest", interfaceMock);
            simulator.reconnect();

            Thread.sleep(100);
            assertTrue(simulator.isConnected());

            assertThrows(RemoteException.class, () -> {
                interfaceMock.setTarget(0,0);
            });

            simulator.setTarget(0,0);
            assertFalse(simulator.isConnected());

            Thread.sleep(100);
            assertTrue(simulator.isConnected());
    }

}
