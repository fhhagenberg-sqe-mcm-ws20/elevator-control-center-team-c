package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.StackPane;

/**
 * Animation Pane which consists of the background pane and overlay pane
 */
@SuppressWarnings("java:S110")
public class ElevatorAnimationPane extends StackPane {
    public ElevatorAnimationPane(int numberOfFLoors, ElevatorViewModel elevatorViewModel) {
        this.getChildren().addAll(new ElevatorBackgroundLayoutPane(numberOfFLoors, elevatorViewModel),new ElevatorAnimationOverlayPane(elevatorViewModel));
    }
}
