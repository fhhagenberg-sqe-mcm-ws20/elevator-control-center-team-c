package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorAnimationOverlayPane;
import at.fhhagenberg.elevator.view.ElevatorAnimationPane;
import at.fhhagenberg.elevator.view.ElevatorBackgroundLayoutPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorAnimationPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.positionProperty()).thenReturn(new SimpleDoubleProperty(0.5));
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.capacityStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.weightStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.targetStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.speedStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.accelerationStringProperty()).thenReturn(new SimpleStringProperty("test"));

        ElevatorAnimationPane elevatorAnimationPane = new ElevatorAnimationPane(3, elevatorViewModelMock);

        assertTrue(elevatorAnimationPane.getChildren().get(0) instanceof ElevatorBackgroundLayoutPane);
        assertTrue(elevatorAnimationPane.getChildren().get(1) instanceof ElevatorAnimationOverlayPane);
    }
}
