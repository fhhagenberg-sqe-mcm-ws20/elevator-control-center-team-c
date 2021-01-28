package at.fhhagenberg.elevator.view;

import javafx.scene.layout.HBox;

/**
 * This class is the base class for the ElevatorModeButtonPane
 * Defines the shape of the pane and used in the FloorsPane to have aligning floors
 */
@SuppressWarnings("java:S110")
public class EmptyElevatorModeButtonPane extends HBox {
    public EmptyElevatorModeButtonPane() {
        this.setMinSize(100,40);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
    }
}
