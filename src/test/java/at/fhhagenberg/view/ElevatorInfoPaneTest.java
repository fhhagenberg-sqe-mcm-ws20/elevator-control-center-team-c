package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorButtonInfoPane;
import at.fhhagenberg.elevator.view.ElevatorInfoPane;
import at.fhhagenberg.elevator.view.ElevatorTextInfoPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
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
class ElevatorInfoPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.RED));
        when(elevatorViewModelMock.capacityStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.weightStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.targetStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.speedStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.accelerationStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.doorStatusProperty()).thenReturn(new SimpleStringProperty("test"));
        ElevatorInfoPane elevatorInfoPane = new ElevatorInfoPane(3, elevatorViewModelMock);

        assertTrue(elevatorInfoPane.getChildren().get(0) instanceof ElevatorButtonInfoPane);
        assertTrue(elevatorInfoPane.getChildren().get(1) instanceof ElevatorTextInfoPane);
    }
}
