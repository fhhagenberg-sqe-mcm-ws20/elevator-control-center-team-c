package at.fhhagenberg;

import at.fhhagenberg.elevator.App;
import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.SystemStatus;
import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import javafx.scene.Node;
import javafx.scene.text.Text;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.matcher.control.TextMatchers;
import sqelevator.IElevator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppTest {

    IElevator interfaceMock;
    Registry rmiRegistry;
    FxRobot robot;

    @BeforeAll
    void before() throws TimeoutException, RemoteException, AlreadyBoundException {
        interfaceMock = mock(IElevator.class, Mockito.withSettings().serializable());

        rmiRegistry = LocateRegistry.createRegistry(1099);

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(App.class);
        robot = new FxRobot();

    }

    @Start
    public void start(Stage stage) {
        var app = new App();
        app.start(stage);
    }

    private Node findNodeWithText(String text) {
        return robot.lookup((Text t) -> t.getText().equals(text)).tryQuery().orElse(null);
    }

    @Test
    void testGUIShowsCorrectErrorStateWhenConnecting() throws RemoteException, InterruptedException {
        try {
            rmiRegistry.unbind("ElevatorSim");
        } catch (NotBoundException e) {}
        
        await().atMost(600, TimeUnit.MILLISECONDS).until(() -> findNodeWithText(SystemStatus.CONNECTING.name().toLowerCase()) != null);
        FxAssert.verifyThat("#statusText", TextMatchers.hasText(SystemStatus.CONNECTING.name().toLowerCase()));
    }

    @Test
    void testGUIShowsCorrectErrorStateWhenConnected() throws InterruptedException, RemoteException, AlreadyBoundException {
        try {
            rmiRegistry.rebind("ElevatorSim", interfaceMock);
        } catch (RemoteException e) {
            rmiRegistry.bind("ElevatorSim", interfaceMock);
        }

        await().atMost(600, TimeUnit.MILLISECONDS).until(() -> findNodeWithText(SystemStatus.CONNECTED.name().toLowerCase()) != null);
        FxAssert.verifyThat("#statusText", TextMatchers.hasText(SystemStatus.CONNECTED.name().toLowerCase()));
    }
}