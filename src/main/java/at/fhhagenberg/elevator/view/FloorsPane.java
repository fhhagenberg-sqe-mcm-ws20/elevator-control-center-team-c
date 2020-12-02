package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.scene.layout.VBox;

import java.util.List;

@SuppressWarnings("java:S110")
public class FloorsPane extends VBox {

    public FloorsPane(List<FloorViewModel> floorViewModels) {
        this.getChildren().add(new EmptyElevatorModeButtonPane());
        floorViewModels.forEach(floorViewModel -> this.getChildren().add(new SingleFilledFloorPane(floorViewModel)));
    }
}
