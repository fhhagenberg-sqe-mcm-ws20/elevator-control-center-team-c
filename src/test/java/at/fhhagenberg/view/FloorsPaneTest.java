package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.FloorsPane;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class FloorsPaneTest {
    @Test
    void testConstructor() {
        FloorViewModel floorViewModelMock = mock(FloorViewModel.class);
        when(floorViewModelMock.buttonUpImageProperty()).thenReturn(new SimpleObjectProperty<>("file:up_filled.png"));
        when(floorViewModelMock.buttonDownImageProperty()).thenReturn(new SimpleObjectProperty<>("file:down_filled.png"));
        when(floorViewModelMock.getButtonUpImage()).thenReturn("file:up_filled.png");
        when(floorViewModelMock.getButtonDownImage()).thenReturn("file:down_filled.png");
        List<FloorViewModel> floorViewModelsMocks = new ArrayList<>(List.of(floorViewModelMock, floorViewModelMock, floorViewModelMock));
        FloorsPane floorsPane = new FloorsPane(floorViewModelsMocks);
        assertEquals(4, floorsPane.getChildren().size());
    }
}
