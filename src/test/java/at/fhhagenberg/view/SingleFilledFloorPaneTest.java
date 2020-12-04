package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.SingleFilledFloorPane;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class SingleFilledFloorPaneTest {
    @Test
    void testConstructor() {
        FloorViewModel floorViewModelMock = mock(FloorViewModel.class);
        when(floorViewModelMock.buttonUpImageProperty()).thenReturn(new SimpleObjectProperty<>("file:up_filled.png"));
        when(floorViewModelMock.buttonDownImageProperty()).thenReturn(new SimpleObjectProperty<>("file:down_filled.png"));
        when(floorViewModelMock.getButtonUpImage()).thenReturn("file:up_filled.png");
        when(floorViewModelMock.getButtonDownImage()).thenReturn("file:down_filled.png");
        SingleFilledFloorPane singleFilledFloorPane = new SingleFilledFloorPane(floorViewModelMock);

        ImageView upLabel = (ImageView) singleFilledFloorPane.getChildren().get(0);
        ImageView downLabel = (ImageView) singleFilledFloorPane.getChildren().get(2);

        assertEquals(new Image("file:up_filled.png").getUrl(), upLabel.getImage().getUrl());
        assertEquals(new Image("file:down_filled.png").getUrl(), downLabel.getImage().getUrl());
    }

    @Test
    void testPropertyChange() {
        FloorViewModel floorViewModelMock = mock(FloorViewModel.class);
        SimpleObjectProperty<String> testUpProperty = new SimpleObjectProperty<>("file:up_filled.png");
        SimpleObjectProperty<String> testDownProperty = new SimpleObjectProperty<>("file:down_filled.png");
        when(floorViewModelMock.buttonUpImageProperty()).thenReturn(testUpProperty);
        when(floorViewModelMock.buttonDownImageProperty()).thenReturn(testDownProperty);
        when(floorViewModelMock.getButtonUpImage()).thenReturn("file:up_filled.png");
        when(floorViewModelMock.getButtonDownImage()).thenReturn("file:down_filled.png");
        SingleFilledFloorPane singleFilledFloorPane = new SingleFilledFloorPane(floorViewModelMock);
        when(floorViewModelMock.getButtonUpImage()).thenReturn("file:up.png");
        when(floorViewModelMock.getButtonDownImage()).thenReturn("file:down.png");

        testUpProperty.set("file:up.png");
        testDownProperty.set("file:down.png");

        ImageView upLabel = (ImageView) singleFilledFloorPane.getChildren().get(0);
        ImageView downLabel = (ImageView) singleFilledFloorPane.getChildren().get(2);
        assertEquals(new Image("file:up.png").getUrl(), upLabel.getImage().getUrl());
        assertEquals(new Image("file:down.png").getUrl(), downLabel.getImage().getUrl());
    }
}
