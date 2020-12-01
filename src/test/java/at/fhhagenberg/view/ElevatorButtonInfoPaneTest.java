package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorButtonInfoPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorButtonInfoPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(anyInt())).thenReturn(new SimpleObjectProperty<>(Color.GREEN));
        when(elevatorViewModelMock.getElevatorFloorButtonColor(anyInt())).thenReturn(Color.GREEN);
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(1)).thenReturn(new SimpleObjectProperty<>(Color.RED));
        when(elevatorViewModelMock.getElevatorFloorButtonColor(1)).thenReturn(Color.RED);


        ElevatorButtonInfoPane elevatorButtonInfoPane = new ElevatorButtonInfoPane(3, elevatorViewModelMock);

        assertEquals(3, elevatorButtonInfoPane.getChildren().size());
        assertEquals(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(0)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(1)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(2)).getBackground());
    }

    @Test
    void testPropertyChange() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        SimpleObjectProperty<Color> testProperty1 = new SimpleObjectProperty<>(Color.GREEN);
        SimpleObjectProperty<Color> testProperty2 = new SimpleObjectProperty<>(Color.RED);

        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(anyInt())).thenReturn(testProperty1);
        when(elevatorViewModelMock.getElevatorFloorButtonColor(anyInt())).thenReturn(Color.GREEN);
        when(elevatorViewModelMock.elevatorFloorButtonColorProperty(1)).thenReturn(testProperty2);
        when(elevatorViewModelMock.getElevatorFloorButtonColor(1)).thenReturn(Color.RED);

        ElevatorButtonInfoPane elevatorButtonInfoPane = new ElevatorButtonInfoPane(3, elevatorViewModelMock);
        when(elevatorViewModelMock.getElevatorFloorButtonColor(anyInt())).thenReturn(Color.RED);
        when(elevatorViewModelMock.getElevatorFloorButtonColor(1)).thenReturn(Color.GREEN);

        testProperty1.set(Color.RED);
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(0)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(1)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(2)).getBackground());

        testProperty2.set(Color.GREEN);
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(0)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(1)).getBackground());
        assertEquals(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)), ((Button) elevatorButtonInfoPane.getChildren().get(2)).getBackground());
    }
}
