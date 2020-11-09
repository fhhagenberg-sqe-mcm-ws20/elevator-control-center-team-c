package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorBackgroundLayoutPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorBackgroundLayoutPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));

        ElevatorBackgroundLayoutPane elevatorBackgroundLayoutPane = new ElevatorBackgroundLayoutPane(5, elevatorViewModelMock);

        assertEquals(5, elevatorBackgroundLayoutPane.getChildren().size());
    }
}
