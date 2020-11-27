package at.fhhagenberg.elevator.view;

import at.fhhagenberg.elevator.viewmodel.ElevatorViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;


public class ElevatorButtonInfoPane extends GridPane {
    public ElevatorButtonInfoPane(int numberOfFloors, ElevatorViewModel elevatorViewModel) {
        int column = 0;
        int row = 0;
        for (int i = 0; i < numberOfFloors; i++) {
            Button singleButton = new Button();
            singleButton.setText(""+i);
            singleButton.setDisable(true);
            singleButton.setStyle(
                    "-fx-background-radius: 15em; " +
                            "-fx-min-width: 20px; " +
                            "-fx-min-height: 20px; " +
                            "-fx-max-width: 20px; " +
                            "-fx-max-height: 20px;" +
                            "-fx-font: 8 Arial;"+
                            "-fx-border-style: solid inside;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-color: black;" +
                            "-fx-opacity: 1;"
            );
            ObjectProperty<Background> floorPaneBackground = singleButton.backgroundProperty();
            final int floorNumber = i;
            floorPaneBackground.bind(Bindings.createObjectBinding(() -> {
                BackgroundFill fill = new BackgroundFill(elevatorViewModel.getElevatorFloorButtonColor(floorNumber), CornerRadii.EMPTY, Insets.EMPTY);
                return new Background(fill);
            }, elevatorViewModel.elevatorFloorButtonColorProperty(floorNumber)));
            this.add(singleButton, i%(Math.floorDiv(numberOfFloors,6)+1), i/(Math.floorDiv(numberOfFloors,6)+1));
        }
    }
}
