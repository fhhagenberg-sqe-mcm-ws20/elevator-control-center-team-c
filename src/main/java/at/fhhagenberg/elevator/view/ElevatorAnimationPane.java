package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.StackPane;

@SuppressWarnings("java:S110")
public class ElevatorAnimationPane extends StackPane {
    public ElevatorAnimationPane(int numberOfFLoors, ElevatorViewModel elevatorViewModel) {
        this.getChildren().addAll(new ElevatorBackgroundLayoutPane(numberOfFLoors, elevatorViewModel),new ElevatorAnimationOverlayPane(elevatorViewModel));
    }
}
