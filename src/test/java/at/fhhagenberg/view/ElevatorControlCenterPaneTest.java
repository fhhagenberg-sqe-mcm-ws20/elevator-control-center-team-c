package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorControlCenterPane;
import at.fhhagenberg.elevator.viewmodel.BuildingViewModel;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorControlCenterPaneTest {
    @Test
    void testInitialize() {
        BuildingViewModel buildingViewModelMock = mock(BuildingViewModel.class);
        Stage stageMock = mock(Stage.class);
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.positionProperty()).thenReturn(new SimpleDoubleProperty(0.5));
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.capacityStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.weightStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.targetStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.speedStringProperty()).thenReturn(new SimpleStringProperty("test"));
        when(elevatorViewModelMock.accelerationStringProperty()).thenReturn(new SimpleStringProperty("test"));
        FloorViewModel floorViewModelMock = mock(FloorViewModel.class);
        when(floorViewModelMock.buttonUpColorProperty()).thenReturn(new SimpleObjectProperty<>("file:up_filled.png"));
        when(floorViewModelMock.buttonDownColorProperty()).thenReturn(new SimpleObjectProperty<>("file:down_filled.png"));
        ElevatorControlCenterPane elevatorControlCenterPane = new ElevatorControlCenterPane(buildingViewModelMock, stageMock);
        when(buildingViewModelMock.getElevatorViewModels()).thenReturn(List.of(elevatorViewModelMock, elevatorViewModelMock, elevatorViewModelMock));
        when(buildingViewModelMock.getFloorViewModels()).thenReturn(List.of(floorViewModelMock, floorViewModelMock, floorViewModelMock, floorViewModelMock));

        elevatorControlCenterPane.initialize();
        assertEquals(5, elevatorControlCenterPane.getChildren().size());
    }
}
