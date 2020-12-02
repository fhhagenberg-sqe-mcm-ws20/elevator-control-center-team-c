package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.VBox;

@SuppressWarnings("java:S110")
public class ElevatorBackgroundLayoutPane extends VBox {
    public ElevatorBackgroundLayoutPane(int numberOfFloors, ElevatorViewModel elevatorViewModel) {
        super();
        for (int i = 0; i < numberOfFloors; i++) {
            this.getChildren().add(new ElevatorSingleFloor(i, elevatorViewModel));
        }
    }
}
