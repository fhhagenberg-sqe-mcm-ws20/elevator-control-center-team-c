package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.FloorViewModel;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("java:S110")
public class FloorsPane extends VBox {

    public FloorsPane(List<FloorViewModel> floorViewModels) {
        this.getChildren().add(new EmptyElevatorModeButtonPane());

        for (int i=floorViewModels.size()-1; i>=0; i--) {
            this.getChildren().add(new SingleFilledFloorPane(floorViewModels.get(i)));
        }
    }
}
