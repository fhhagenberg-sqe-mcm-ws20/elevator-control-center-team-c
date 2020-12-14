package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorSingleFloor;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorSingleFloorTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorColorProperty(0)).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.getElevatorFloorColor(0)).thenReturn(Color.GREEN);

        ElevatorSingleFloor elevatorSingleFloor = new ElevatorSingleFloor(0, elevatorViewModelMock);

        assertEquals(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)), elevatorSingleFloor.getBackground());
        assertEquals("1", ((Label) elevatorSingleFloor.getChildren().get(0)).getText());
    }

    @Test
    void testPropertyChange() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        SimpleObjectProperty<Color> testProperty = new SimpleObjectProperty<>(Color.GREEN);
        when(elevatorViewModelMock.elevatorFloorColorProperty(1)).thenReturn(testProperty);
        when(elevatorViewModelMock.getElevatorFloorColor(1)).thenReturn(Color.GREEN);
        ElevatorSingleFloor elevatorSingleFloor = new ElevatorSingleFloor(1, elevatorViewModelMock);
        when(elevatorViewModelMock.getElevatorFloorColor(1)).thenReturn(Color.RED);

        testProperty.set(Color.RED);

        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), elevatorSingleFloor.getBackground());
    }
}
