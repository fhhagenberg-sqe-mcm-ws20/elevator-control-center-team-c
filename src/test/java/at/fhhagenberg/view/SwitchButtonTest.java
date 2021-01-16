package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.SwitchButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class SwitchButtonTest {
    @Test
    void testConstructor() {
        SwitchButton switchButton = new SwitchButton();

        assertFalse(switchButton.stateProperty().get());
    }

    @Test
    void testStateTransitions() {
        SwitchButton switchButton = new SwitchButton();
        switchButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));

        assertTrue(switchButton.stateProperty().get());

        switchButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));

        assertFalse(switchButton.stateProperty().get());

        switchButton.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), switchButton.getLayoutX(), MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null));

        assertTrue(switchButton.stateProperty().get());

    }
}
