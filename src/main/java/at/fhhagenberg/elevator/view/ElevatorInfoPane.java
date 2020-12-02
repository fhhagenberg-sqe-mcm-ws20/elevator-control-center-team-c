package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.scene.layout.VBox;

@SuppressWarnings("java:S110")
public class ElevatorInfoPane extends VBox {
    public ElevatorInfoPane(int numberOfFloors, ElevatorViewModel elevatorViewModel) {
        super();
        this.getChildren().add(new ElevatorButtonInfoPane(numberOfFloors, elevatorViewModel));
        this.getChildren().add(new ElevatorTextInfoPane(elevatorViewModel));
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
    }
}
