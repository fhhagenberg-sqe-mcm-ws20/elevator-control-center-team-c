package at.fhhagenberg.viewmodel;

import at.fhhagenberg.elevator.RMIElevatorAdapter;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ElevatorViewModelTest {
    private Elevator elevatorMock = mock(Elevator.class);
    private RMIElevatorAdapter simulator = mock(RMIElevatorAdapter.class);

    @BeforeEach
    void initTest() {
        when(elevatorMock.accelerationProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.capacityProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.speedProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.targetProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.weightProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.positionProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.doorStatusProperty()).thenReturn(new SimpleIntegerProperty());
        when(elevatorMock.getFloorButtonStatuses()).thenReturn(List.of(new SimpleBooleanProperty(false), new SimpleBooleanProperty(true), new SimpleBooleanProperty(false)));
        when(elevatorMock.listOfServicedFloorsProperty()).thenReturn(new SimpleObjectProperty<>());
    }

    @Test
    void testConstructor() {
        when(elevatorMock.getAcceleration()).thenReturn(7);
        when(elevatorMock.getCapacity()).thenReturn(12);
        when(elevatorMock.getSpeed()).thenReturn(10);
        when(elevatorMock.getTarget()).thenReturn(2);
        when(elevatorMock.getWeight()).thenReturn(11);
        when(elevatorMock.getPosition()).thenReturn(60);
        when(elevatorMock.getFloorButtonStatuses()).thenReturn(List.of(new SimpleBooleanProperty(false), new SimpleBooleanProperty(true), new SimpleBooleanProperty(false)));
        when(elevatorMock.servicesFloor(0)).thenReturn(true);
        when(elevatorMock.getDoorStatus()).thenReturn(0);

        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        assertEquals("7 ft/s2", elevatorViewModel.getAccelerationString());
        assertEquals("12", elevatorViewModel.getCapacityString());
        assertEquals("10 ft/s", elevatorViewModel.getSpeedString());
        assertEquals("2", elevatorViewModel.getTargetString());
        assertEquals("11 lbs", elevatorViewModel.getWeightString());
        assertEquals(0.8, elevatorViewModel.getPosition());
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorButtonColor(0));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorButtonColor(1));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorButtonColor(2));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorColor(0));
        assertEquals(Color.GRAY, elevatorViewModel.getElevatorFloorColor(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorColor(2));
    }

    @Test
    void testAccelerationStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.accelerationProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getAcceleration()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 ft/s2", elevatorViewModel.getAccelerationString());
    }

    @Test
    void testCapacityStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.capacityProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getCapacity()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5", elevatorViewModel.getCapacityString());
    }

    @Test
    void testSpeedStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.speedProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getSpeed()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 ft/s", elevatorViewModel.getSpeedString());
    }

    @Test
    void testTargetStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.targetProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getTarget()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5", elevatorViewModel.getTargetString());
    }

    @Test
    void testWeightStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.weightProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getWeight()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 lbs", elevatorViewModel.getWeightString());
    }

    @Test
    void testPositionBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.positionProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getPosition()).thenReturn(0);
        testProperty.set(30);
        assertEquals(0.9, elevatorViewModel.getPosition());
    }

    @Test
    void testFloorColorsBindingBindingWhenSettingTarget() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(elevatorMock.targetProperty()).thenReturn(testProperty);
        when(elevatorMock.servicesFloor(1)).thenReturn(true);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.getTarget()).thenReturn(2);
        testProperty.set(0);
        assertEquals(Color.GRAY, elevatorViewModel.getElevatorFloorColor(0));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorColor(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorColor(2));
    }

    @Test
    void testFloorColorsBindingBindingWhenSettingListOfServicedFloors() {
        SimpleObjectProperty<List<Integer>> testProperty = new SimpleObjectProperty<>();
        when(elevatorMock.listOfServicedFloorsProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        when(elevatorMock.servicesFloor(1)).thenReturn(true);
        testProperty.set(List.of(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorColor(0));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorColor(1));
        assertEquals(Color.GRAY, elevatorViewModel.getElevatorFloorColor(2));
    }

    @Test
    void testFloorButtonColorsBindingBindingWhenSettingFloorButtons() {
        SimpleBooleanProperty testProperty1 = new SimpleBooleanProperty(false);
        SimpleBooleanProperty testProperty2 = new SimpleBooleanProperty(true);
        SimpleBooleanProperty testProperty3 = new SimpleBooleanProperty(false);
        when(elevatorMock.getFloorButtonStatuses()).thenReturn(List.of(testProperty1, testProperty2, testProperty3));
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(simulator, elevatorMock, 3, 100);
        testProperty1.set(true);
        testProperty2.set(false);
        testProperty3.set(true);
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorButtonColor(0));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorButtonColor(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorButtonColor(2));
    }

    @Test
    void testSetManualControl(){
        ElevatorViewModel elevatorViewModel=new ElevatorViewModel(simulator, elevatorMock,5,10);
        elevatorViewModel.setManualControl(false);
        verify(elevatorMock, times(1)).setManualControl(anyBoolean());
    }

    @Test
    void testPropertyGetters(){
        when(elevatorMock.getAcceleration()).thenReturn(1);
        when(elevatorMock.getCapacity()).thenReturn(2);
        when(elevatorMock.getSpeed()).thenReturn(3);
        when(elevatorMock.getTarget()).thenReturn(4);
        when(elevatorMock.getWeight()).thenReturn(5);
        when(elevatorMock.getPosition()).thenReturn(0);
        when(elevatorMock.getFloorButtonStatuses()).thenReturn(List.of(new SimpleBooleanProperty(false), new SimpleBooleanProperty(true), new SimpleBooleanProperty(false)));
        when(elevatorMock.listOfServicedFloorsProperty()).thenReturn(new SimpleObjectProperty<>());

        ElevatorViewModel elevatorViewModel=new ElevatorViewModel(simulator, elevatorMock,6,10);

        assertEquals("1 ft/s2",elevatorViewModel.accelerationStringProperty().get());
        assertEquals("2",elevatorViewModel.capacityStringProperty().get());
        assertEquals("3 ft/s",elevatorViewModel.speedStringProperty().get());
        assertEquals("4",elevatorViewModel.targetStringProperty().get());
        assertEquals("5 lbs",elevatorViewModel.weightStringProperty().get());
        assertEquals(1.0,elevatorViewModel.positionProperty().get());
        assertEquals(Color.GRAY,elevatorViewModel.getElevatorFloorColor(0));
        assertEquals(Color.WHITE,elevatorViewModel.getElevatorFloorButtonColor(0));

    }
}
