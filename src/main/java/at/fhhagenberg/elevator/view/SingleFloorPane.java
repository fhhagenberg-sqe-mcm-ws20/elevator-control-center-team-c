package at.fhhagenberg.elevator.view;

import javafx.scene.layout.HBox;

/**
 * This is the base class for all floor classes
 * This class defines the geometry of a floor view element
 * Done like that so that a SingleFilledFloor and a ElevatorSingleFloor has the same size and align
 */
@SuppressWarnings("java:S110")
public class SingleFloorPane extends HBox {
    public SingleFloorPane() {
        this.setPrefSize(150, 20);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
    }
}
