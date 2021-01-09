package at.fhhagenberg;

import at.fhhagenberg.elevator.App;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextMatchers;
import sqe.IElevator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuiTest {

    IElevator interfaceMock;
    FxRobot robot;

    @BeforeAll
    void before() throws TimeoutException {
        interfaceMock = mock(IElevator.class, Mockito.withSettings().serializable());

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
    void testSetTargetFloor(){
        robot.clickOn("#elevatorModeSwitch");
        robot.clickOn("#targetFloorButton");
        try{
            int target = interfaceMock.getTarget(0);
            assertEquals(target, 0);
        }catch (Exception e){}
    }


    @Test
    void testGUIShowsCorrectTarget(){
        robot.clickOn("#elevatorModeSwitch");
        robot.clickOn("#targetFloorButton");

        FxAssert.verifyThat("#targetValue", TextMatchers.hasText("0"));
    }

    /*@Test
    void testGUIShowsCorrectErrorStateWhenConnected() throws InterruptedException, RemoteException, AlreadyBoundException {
        try {
            rmiRegistry.rebind("ElevatorSim", interfaceMock);
        } catch (RemoteException e) {
            rmiRegistry.bind("ElevatorSim", interfaceMock);
        }

        await().atMost(600, TimeUnit.MILLISECONDS).until(() -> findNodeWithText(SystemStatus.CONNECTED.name().toLowerCase()) != null);
        FxAssert.verifyThat("#statusText", TextMatchers.hasText(SystemStatus.CONNECTED.name().toLowerCase()));
    }*/
}
