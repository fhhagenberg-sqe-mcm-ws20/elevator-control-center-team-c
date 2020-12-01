package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorModeButtonPane;
import at.fhhagenberg.elevator.view.SwitchButton;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(ApplicationExtension.class)
class ElevatorModeButtonPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        ElevatorModeButtonPane elevatorModeButtonPane = new ElevatorModeButtonPane(elevatorViewModelMock);

        assertTrue(elevatorModeButtonPane.getChildren().get(0) instanceof Label);
        assertTrue(elevatorModeButtonPane.getChildren().get(1) instanceof SwitchButton);
    }
}
