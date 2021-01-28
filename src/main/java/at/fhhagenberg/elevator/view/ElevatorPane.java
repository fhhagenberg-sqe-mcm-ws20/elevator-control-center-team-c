package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.VBox;

/**
 * This is the container for all elements of a single elevator view
 * Consists of the ElevatorModeButtonpane, ElevatorAnimationPane and ElevatorInfoPane
 */
@SuppressWarnings("java:S110")
public class ElevatorPane extends VBox {
    public ElevatorPane(int numberOfFLoors, ElevatorViewModel elevatorViewModel) {
        super();
        this.getStyleClass().add("elevator-pane");
        this.setId("elevator-" + elevatorViewModel.getElevatorNumber());
        this.getChildren().addAll(new ElevatorModeButtonPane(elevatorViewModel),new ElevatorAnimationPane(numberOfFLoors, elevatorViewModel), new ElevatorInfoPane(numberOfFLoors, elevatorViewModel));
    }
}
