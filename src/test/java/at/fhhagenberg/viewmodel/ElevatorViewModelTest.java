package at.fhhagenberg.viewmodel;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElevatorViewModelTest {
    private Elevator mockElevator = mock(Elevator.class);

    @BeforeEach
    void initTest() {
        when(mockElevator.accelerationProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.capacityProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.speedProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.targetProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.weightProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.positionProperty()).thenReturn(new SimpleIntegerProperty());
        when(mockElevator.getFloorButtonStatuses()).thenReturn(List.of(new SimpleBooleanProperty(false), new SimpleBooleanProperty(true), new SimpleBooleanProperty(false)));
        when(mockElevator.listOfServicedFloorsProperty()).thenReturn(new SimpleObjectProperty<>());
    }

    @Test
    void testConstructor() {
        when(mockElevator.getAcceleration()).thenReturn(7);
        when(mockElevator.getCapacity()).thenReturn(12);
        when(mockElevator.getSpeed()).thenReturn(10);
        when(mockElevator.getTarget()).thenReturn(2);
        when(mockElevator.getWeight()).thenReturn(11);
        when(mockElevator.getPosition()).thenReturn(60);
        when(mockElevator.getFloorButtonStatuses()).thenReturn(List.of(new SimpleBooleanProperty(false), new SimpleBooleanProperty(true), new SimpleBooleanProperty(false)));
        when(mockElevator.servicesFloor(0)).thenReturn(true);

        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        assertEquals("7 m/s2", elevatorViewModel.getAccelerationString());
        assertEquals("12 kg", elevatorViewModel.getCapacityString());
        assertEquals("10 m/s", elevatorViewModel.getSpeedString());
        assertEquals("2", elevatorViewModel.getTargetString());
        assertEquals("11 kg", elevatorViewModel.getWeightString());
        assertEquals(0.2, elevatorViewModel.getPosition());
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
        when(mockElevator.accelerationProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getAcceleration()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 m/s2", elevatorViewModel.getAccelerationString());
    }

    @Test
    void testCapacityStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.capacityProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getCapacity()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 kg", elevatorViewModel.getCapacityString());
    }

    @Test
    void testSpeedStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.speedProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getSpeed()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 m/s", elevatorViewModel.getSpeedString());
    }

    @Test
    void testTargetStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.targetProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getTarget()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5", elevatorViewModel.getTargetString());
    }

    @Test
    void testWeightStringBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.weightProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getWeight()).thenReturn(5);
        testProperty.set(5);
        assertEquals("5 kg", elevatorViewModel.getWeightString());
    }

    @Test
    void testPositionBinding() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.positionProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getPosition()).thenReturn(5);
        testProperty.set(30);
        assertEquals(0.1, elevatorViewModel.getPosition());
    }

    @Test
    void testFloorColorsBindingBindingWhenSettingTarget() {
        SimpleIntegerProperty testProperty = new SimpleIntegerProperty(1);
        when(mockElevator.targetProperty()).thenReturn(testProperty);
        when(mockElevator.servicesFloor(1)).thenReturn(true);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.getTarget()).thenReturn(2);
        testProperty.set(0);
        assertEquals(Color.GRAY, elevatorViewModel.getElevatorFloorColor(0));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorColor(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorColor(2));
    }

    @Test
    void testFloorColorsBindingBindingWhenSettingListOfServicedFloors() {
        SimpleObjectProperty<List<Integer>> testProperty = new SimpleObjectProperty<>();
        when(mockElevator.listOfServicedFloorsProperty()).thenReturn(testProperty);
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        when(mockElevator.servicesFloor(1)).thenReturn(true);
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
        when(mockElevator.getFloorButtonStatuses()).thenReturn(List.of(testProperty1, testProperty2, testProperty3));
        ElevatorViewModel elevatorViewModel = new ElevatorViewModel(mockElevator, 3, 100);
        testProperty1.set(true);
        testProperty2.set(false);
        testProperty3.set(true);
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorButtonColor(0));
        assertEquals(Color.WHITE, elevatorViewModel.getElevatorFloorButtonColor(1));
        assertEquals(Color.GREEN, elevatorViewModel.getElevatorFloorButtonColor(2));
    }
}
