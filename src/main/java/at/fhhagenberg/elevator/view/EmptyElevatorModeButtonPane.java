package at.fhhagenberg.elevator.view;

import javafx.scene.layout.HBox;

@SuppressWarnings("java:S110")
public class EmptyElevatorModeButtonPane extends HBox {
    public EmptyElevatorModeButtonPane() {
        this.setPrefSize(100, 40);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
    }
}
