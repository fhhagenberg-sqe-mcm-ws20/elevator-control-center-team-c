package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.VBox;

@SuppressWarnings("java:S110")
public class ElevatorPane extends VBox {
    public ElevatorPane(int numberOfFLoors, ElevatorViewModel elevatorViewModel) {
        super();
        this.getChildren().addAll(new ElevatorModeButtonPane(elevatorViewModel),new ElevatorAnimationPane(numberOfFLoors, elevatorViewModel), new ElevatorInfoPane(numberOfFLoors, elevatorViewModel));
    }
}
