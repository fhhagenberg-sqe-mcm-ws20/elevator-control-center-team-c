package at.fhhagenberg.viewmodel;

import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FloorViewModelTest {
    @Test
    void testConstructorColorBindingWhenButtonsAreFalse() {
        Floor floorMock = mock(Floor.class);
        when(floorMock.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(false));
        when(floorMock.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(false));
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);
        assertEquals("file:down.png", floorViewModel.getButtonDownImage());
        assertEquals("file:up.png", floorViewModel.getButtonUpImage());
    }

    @Test
    void testConstructorColorBindingWhenButtonsAreTrue() {
        Floor floorMock = mock(Floor.class);
        when(floorMock.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(true));
        when(floorMock.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(true));
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);
        assertEquals("file:up_filled.png", floorViewModel.getButtonUpImage());
        assertEquals("file:down_filled.png", floorViewModel.getButtonDownImage());
    }

    @Test
    void testColorBindingChanges() {
        SimpleBooleanProperty downTestProperty = new SimpleBooleanProperty(false);
        SimpleBooleanProperty upTestProperty = new SimpleBooleanProperty(false);
        Floor floorMock = mock(Floor.class);
        when(floorMock.buttonDownProperty()).thenReturn(downTestProperty);
        when(floorMock.buttonUpProperty()).thenReturn(upTestProperty);
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);

        assertEquals("file:down.png", floorViewModel.getButtonDownImage());
        assertEquals("file:up.png", floorViewModel.getButtonUpImage());

        downTestProperty.set(true);
        upTestProperty.set(true);

        assertEquals("file:down_filled.png", floorViewModel.getButtonDownImage());
        assertEquals("file:up_filled.png", floorViewModel.getButtonUpImage());
    }

    @Test
    void testGetFloorNumber() {
        Floor floorMock = mock(Floor.class);
        when(floorMock.getNumber()).thenReturn(5);
        when(floorMock.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(true));
        when(floorMock.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(true));
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);
        assertEquals(5, floorViewModel.getFloorNumber());
    }

    @Test
    void testButtonDownProperty() {
        Floor floorMock = mock(Floor.class);
        when(floorMock.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(true));
        when(floorMock.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(true));
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);
        assertEquals("file:down_filled.png", floorViewModel.buttonDownImageProperty().get());
    }

    @Test
    void testButtonUpProperty() {
        Floor floorMock = mock(Floor.class);
        when(floorMock.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(true));
        when(floorMock.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(true));
        FloorViewModel floorViewModel = new FloorViewModel(floorMock);
        assertEquals("file:up_filled.png", floorViewModel.buttonUpImageProperty().get());
    }
}
