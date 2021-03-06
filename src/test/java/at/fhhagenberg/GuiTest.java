package at.fhhagenberg;

import at.fhhagenberg.elevator.App;
import at.fhhagenberg.elevator.SystemStatus;
import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
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
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;
import sqelevator.IElevator;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import static org.awaitility.Awaitility.await;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GuiTest {

    IElevator interfaceMock;
    FxRobot robot;
    final int elevatorCount = 1;
    final int floorCount = 3;
    final int elevatorWeight = 100;


    @BeforeAll
    void before() throws TimeoutException, RemoteException, AlreadyBoundException {
        //prepare the mock object
        interfaceMock = mock(IElevator.class, Mockito.withSettings().serializable());
        when(interfaceMock.getElevatorNum()).thenReturn(elevatorCount);
        when(interfaceMock.getFloorNum()).thenReturn(floorCount);
        when(interfaceMock.getFloorHeight()).thenReturn(20);
        when(interfaceMock.getFloorButtonDown(0)).thenReturn(false);
        when(interfaceMock.getFloorButtonUp(0)).thenReturn(false);
        for(int i = 0; i < floorCount; i++ ){
            when(interfaceMock.getServicesFloors(0, i)).thenReturn(true);
            when(interfaceMock.getElevatorButton(0, i)).thenReturn(false);
        }
        when(interfaceMock.getCommittedDirection(0)).thenReturn(2);
        when(interfaceMock.getElevatorAccel(0)).thenReturn(7);
        when(interfaceMock.getElevatorDoorStatus(0)).thenReturn(2);
        when(interfaceMock.getElevatorFloor(0)).thenReturn(1);
        when(interfaceMock.getElevatorPosition(0)).thenReturn(20);
        when(interfaceMock.getElevatorSpeed(0)).thenReturn(0);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(0);
        when(interfaceMock.getElevatorCapacity(0)).thenReturn(1600);
        when(interfaceMock.getTarget(0)).thenReturn(0);
        when(interfaceMock.getClockTick()).thenReturn((long) 0);

        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(() -> new App(interfaceMock));

    }

    @Start
    public void start(Stage stage) {
        var app = new App(interfaceMock);
        app.start(stage);
    }

    private Node findNodeWithId(String id) {
        return robot.lookup(id).tryQuery().orElse(null);
    }

    private void waitForGUI(){
        await().atMost(5000, TimeUnit.MILLISECONDS).until(() -> findNodeWithId("#weightLabel") != null);
    }


    @Test
    void testSetTargetFloor() throws InterruptedException, RemoteException, AlreadyBoundException {
        robot = new FxRobot();
        waitForGUI();
        Node elevatorModeSwitch = robot.lookup(".elevator-pane").lookup("#elevator-0").lookup(".elevator-mode-button-pane").query();
        robot.clickOn(elevatorModeSwitch);
        Node floor = robot.lookup(".elevator-single-floor").lookup("#floor-0").query();
        robot.clickOn(floor);
        verify(interfaceMock, times(1)).setTarget(0, 0);
    }


    @Test
    void testGUIShowsCorrectWeight() throws InterruptedException, RemoteException {
        when(interfaceMock.getClockTick()).thenReturn((long) 1);
        when(interfaceMock.getElevatorWeight(0)).thenReturn(elevatorWeight);
        waitForGUI();
        NodeQuery query = robot.lookup(".elevator-text-info-pane").lookup("#elevator-0").lookup("#weightLabel");
        FxAssert.verifyThat(query, LabeledMatchers.hasText(String.valueOf(elevatorWeight) + " lbs"));
    }

}
