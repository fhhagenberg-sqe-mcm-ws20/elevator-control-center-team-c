package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.ElevatorAnimationOverlayPane;
import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class ElevatorAnimationOverlayPaneTest {
    @Test
    void testConstructor() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        when(elevatorViewModelMock.positionProperty()).thenReturn(new SimpleDoubleProperty(0.5));

        ElevatorAnimationOverlayPane elevatorAnimationOverlayPane = new ElevatorAnimationOverlayPane(elevatorViewModelMock);

        assertEquals((elevatorAnimationOverlayPane.getHeight()-((ImageView)elevatorAnimationOverlayPane.getChildren().get(0)).getFitHeight())*0.5, elevatorAnimationOverlayPane.getPadding().getTop());
        assertFalse(elevatorAnimationOverlayPane.isPickOnBounds());
    }

    @Test
    void testPropertyChange() {
        ElevatorViewModel elevatorViewModelMock = mock(ElevatorViewModel.class);
        SimpleDoubleProperty testProperty = new SimpleDoubleProperty(0.5);
        when(elevatorViewModelMock.positionProperty()).thenReturn(testProperty);

        ElevatorAnimationOverlayPane elevatorAnimationOverlayPane = new ElevatorAnimationOverlayPane(elevatorViewModelMock);

        testProperty.set(0.8);

        assertEquals(elevatorAnimationOverlayPane.getHeight()*0.8-elevatorAnimationOverlayPane.getImageSize(), elevatorAnimationOverlayPane.getPadding().getTop());
    }
}
