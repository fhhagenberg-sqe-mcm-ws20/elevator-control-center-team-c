package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.VBox;

/**
 * THis is the background of the elevator animation. It shows the floors
 */
@SuppressWarnings("java:S110")
public class ElevatorBackgroundLayoutPane extends VBox {
    public ElevatorBackgroundLayoutPane(int numberOfFloors, ElevatorViewModel elevatorViewModel) {
        super();
        //Add all floors
        for (int i = numberOfFloors-1; i >=0; i--) {
            this.getChildren().add(new ElevatorSingleFloor(i, elevatorViewModel));
        }
    }
}
