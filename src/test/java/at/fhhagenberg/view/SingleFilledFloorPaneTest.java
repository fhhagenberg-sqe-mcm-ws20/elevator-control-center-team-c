package at.fhhagenberg.view;

import at.fhhagenberg.elevator.view.SingleFilledFloorPane;
import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
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
        when(floorViewModelMock.buttonUpColorProperty()).thenReturn(new SimpleObjectProperty<>("file:up_filled.png"));
        when(floorViewModelMock.buttonDownColorProperty()).thenReturn(new SimpleObjectProperty<>("file:down_filled.png"));
        when(floorViewModelMock.getButtonUpColor()).thenReturn("file:up_filled.png");
        when(floorViewModelMock.getButtonDownColor()).thenReturn("file:down_filled.png");
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
        when(floorViewModelMock.buttonUpColorProperty()).thenReturn(testUpProperty);
        when(floorViewModelMock.buttonDownColorProperty()).thenReturn(testDownProperty);
        when(floorViewModelMock.getButtonUpColor()).thenReturn("file:up_filled.png");
        when(floorViewModelMock.getButtonDownColor()).thenReturn("file:down_filled.png");
        SingleFilledFloorPane singleFilledFloorPane = new SingleFilledFloorPane(floorViewModelMock);
        when(floorViewModelMock.getButtonUpColor()).thenReturn("file:up.png");
        when(floorViewModelMock.getButtonDownColor()).thenReturn("file:down.png");

        testUpProperty.set("file:up.png");
        testDownProperty.set("file:down.png");

        ImageView upLabel = (ImageView) singleFilledFloorPane.getChildren().get(0);
        ImageView downLabel = (ImageView) singleFilledFloorPane.getChildren().get(2);
        assertEquals(new Image("file:up.png").getUrl(), upLabel.getImage().getUrl());
        assertEquals(new Image("file:down.png").getUrl(), downLabel.getImage().getUrl());
    }
}
