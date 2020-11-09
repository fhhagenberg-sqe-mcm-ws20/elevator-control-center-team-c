package at.fhhagenberg.viewmodel;

import at.fhhagenberg.elevator.model.Floor;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FloorViewModelTest {
    @Test
    void testConstructorColorBindingWhenButtonsAreFalse() {
        Floor floor = mock(Floor.class);
        when(floor.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(false));
        when(floor.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(false));
        FloorViewModel floorViewModel = new FloorViewModel(floor);
        assertEquals("file:down.png", floorViewModel.getButtonDownColor());
        assertEquals("file:up.png", floorViewModel.getButtonUpColor());
    }

    @Test
    void testConstructorColorBindingWhenButtonsAreTrue() {
        Floor floor = mock(Floor.class);
        when(floor.buttonDownProperty()).thenReturn(new SimpleBooleanProperty(true));
        when(floor.buttonUpProperty()).thenReturn(new SimpleBooleanProperty(true));
        FloorViewModel floorViewModel = new FloorViewModel(floor);
        assertEquals("file:up_filled.png", floorViewModel.getButtonUpColor());
        assertEquals("file:down_filled.png", floorViewModel.getButtonDownColor());
    }

    @Test
    void testColorBindingChanges() {
        SimpleBooleanProperty downTestProperty = new SimpleBooleanProperty(false);
        SimpleBooleanProperty upTestProperty = new SimpleBooleanProperty(false);
        Floor floor = mock(Floor.class);
        when(floor.buttonDownProperty()).thenReturn(downTestProperty);
        when(floor.buttonUpProperty()).thenReturn(upTestProperty);
        FloorViewModel floorViewModel = new FloorViewModel(floor);

        assertEquals("file:down.png", floorViewModel.getButtonDownColor());
        assertEquals("file:up.png", floorViewModel.getButtonUpColor());

        downTestProperty.set(true);
        upTestProperty.set(true);

        assertEquals("file:down_filled.png", floorViewModel.getButtonDownColor());
        assertEquals("file:up_filled.png", floorViewModel.getButtonUpColor());
    }
}
