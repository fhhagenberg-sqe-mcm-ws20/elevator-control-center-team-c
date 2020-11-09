package at.fhhagenberg.elevator.view;

import javafx.scene.layout.HBox;

public class SingleFloorPane extends HBox {
    public SingleFloorPane() {
        this.setPrefSize(150, 20);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 1;" +
                "-fx-border-color: black;");
    }
}
